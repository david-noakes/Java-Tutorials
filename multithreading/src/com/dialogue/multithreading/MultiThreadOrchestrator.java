package com.dialogue.multithreading;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MultiThreadOrchestrator {

	// Logger
	static Logger logger = Logger.getLogger("dialog.multithreading.logger");
	/*
	 * Parameters from Config File
	 */
	// DB Connection Data (wiht Default Values)
	private String postgresDriver = "org.postgresql.Driver";
	private String postgresUrl = "jdbc:postgresql://localhost:5432/geocode_db";
	private String postgresUser = "geouser";
	private String postgresPassword = "geopassword";

	private String serviceStopFile = "/opt/local/geocode/stop";
	private File stopFile = null;
	private boolean simulateDB = true;
	private File simulFile;
	private String userDir;
	private String simulFileName;
	BufferedReader dbOutput = null;
	private boolean workersStarted = false;
	List<MultiThreadWorker> workers;
	private boolean tooManyWorkers = false;
	private boolean needMoreWorkers = false;
	
	///////////////////////////////////////////////
	// QUEUES - Thread safe
	private ArrayBlockingQueue<InputDTO> workerQueue = null;
    private ArrayBlockingQueue<ServiceResult> resultQueue = null;
	
	
	
	// ////////////////////////////////////////////
	private static int maxThreads = 11;
	private static int minThreads = 3;
	private static int nbrThreadsInit = 3;
	private boolean stopping = false;
	long updateTimestamp; 
	private static int requestsPerSecond = 22;
    private static int minNbrToRead = minThreads;
	// ////////////////////////////////////////////
    private static int processingTooFastTime = 700; //if we clear the Q in less than this time, kill off a worker
    private static int processingTooSlowTime = 900; //if we take longer than this, we need another worker
	
	// Queue loading pause time 
	private static int queueLoadingPauseTimeValue = 2;
	// housekeeping loop sleep timer - for processing our Q and waiting for 1 second to elapse
	private static int housekeepingSleepTimeValue = 13;
    // pseudo processsing loop sleep timer - for processing 1 Q item 
    private static int pseudoProcessingSleepTimeValue = 7;
    //pause after loading queue for workers to do work
    private static int waitForWorkersTime = 350-(queueLoadingPauseTimeValue * nbrThreadsInit);
    
    
	// Long 'Sleep' timer when there are no records to process
	private static int maxShutdownTimeValue = 5000;
	// Timer increase value
	private static int increaseTimeValue = 100;
	private static int almost1Second =  1000 - housekeepingSleepTimeValue +1;
	
	public static final String statusProcessing = "PROCESSING";
	public static final String statusFailed = "FAILED";
	public static final String statusRetry = "RETRY";
	public static final String statusSuccess = "SUCCESS"; 

	// DB Connection
	private Connection postgresConnection = null;


	private final String queryRead = "SELECT * FROM address_read WHERE status <> '" + statusProcessing
			+ "' AND status <> '" + statusFailed 
			+ "' ORDER BY priority DESC, status ASC, record_timestamp ASC LIMIT " + requestsPerSecond + ";";
	
	
	
	public MultiThreadOrchestrator()
	{
		super();
	}
	
	public void run() {
		stopFile = performInit();
		openDBConnection();
		int counter = 0;
		int burst = 0;
		int wqLen = 0;

		// ////////////////////////////////////////////
	    workerQueue = new ArrayBlockingQueue<InputDTO>(1024);
	    resultQueue = new ArrayBlockingQueue<ServiceResult>(1024);
		workers = new ArrayList<MultiThreadWorker>(maxThreads);
		for (int i = 0; i < nbrThreadsInit; i++) {
			workers.add(new MultiThreadWorker("Worker "+ i, workerQueue, resultQueue));
		}

		// ////////////////////////////////////////////
		
		long currentTime  = System.currentTimeMillis();
        long startTime  = currentTime;
		int timeDelta = 0;
		int saveTimeDelta = 0;

		while (!stopping) {
            // we should come through here once per second
		    // almost1Second is exactly that - if we construct the loop for exactly 1 second, 
		    // we go past that value by as much as 35 ms
		    // recalculate it here, because we might adjust the housekeepingSleepTimeValue
		    almost1Second = 1000 - housekeepingSleepTimeValue +1;

		    // read dbOutput requestsPerSecond rows at a time, and apportion to workers
		    // if the workerQueue contains rows, real less rows to ensure the queue total 
		    // will be the configured batch size
			try {
				List rows = performQuery(wqLen);  // could set stopping = true
				
				System.out.println("read "+rows.size()+ " qLen = " + wqLen);
				burst = rows.size() + getWorkerQLength(); // how many we are trying to process this second
				if (stopFile.exists()) {
					stopping = true;   // only set flag to allow orderly shutdown
				} 
				if (rows.size() > 0) {
				    for (int i = 0; i<rows.size();i++) {
				        counter++;
					    InputDTO dto = new InputDTO((String) rows.get(i), counter);
					    putToWorkerQ(dto);
					    if (i>0 && i % workers.size() == 0) {
					        Thread.sleep(queueLoadingPauseTimeValue);
					    }
					}
				}
                currentTime = System.currentTimeMillis(); 
                saveTimeDelta = 0;
				if (!workersStarted) {
				    // set startTime again for closer approx of actual time to process batch
				    startTime = System.currentTimeMillis();
					for (int i = 0; i < workers.size(); i++) {
						workers.get(i).start();
					}
					workersStarted = true;
				} else {
                    for (int i = 0; i < workers.size(); i++) {
                        // start any new ones
                        if (!workers.get(i).isRunning()) {
                            workers.get(i).start();
                        } else {
                            // reset currentTime for workers
                            // they can then sleep for the balance of the second if queue goes empty
                            workers.get(i).currentTime = currentTime; 
                        }
                    }
				}
				
				// allow the workers to do some work
				Thread.sleep(waitForWorkersTime); // this should not be made too small
				
				timeDelta = (int) (System.currentTimeMillis() - currentTime);
				
				//************************************//
				while (timeDelta < almost1Second) {
	                // this is our housekeeping loop
	                // process the output from the workers, update the database
	                if (allWorkersIdle()) {
	                    if (saveTimeDelta == 0) {
	                        saveTimeDelta = timeDelta; // used to calculate burst rate
	                    }
	                    if (!stopping && !tooManyWorkers  && timeDelta < processingTooFastTime) {
    	                    tooManyWorkers = true;
    	                    System.out.println("too many workers @ "+timeDelta);
	                    }
	                }
				    if (getResultQLength() > 0) {
				        // process 1 entry in our input q
				        processResultQItem();
				    } else {
				       Thread.sleep(housekeepingSleepTimeValue); 
				    }
				    timeDelta = (int) (System.currentTimeMillis() - currentTime);
				}
                //************************************//

				// almost 1 second has elapsed.
				
				if (stopping) {  // not a panic abort, but orderly shutdown
					while (!allWorkersIdle() || getResultQLength() > 0) {
	                    if (getResultQLength() > 0) {
	                        // process 1 entry in our input q
	                        processResultQItem();
	                    } else {
	                       Thread.sleep(housekeepingSleepTimeValue); 
	                    }
					}
					// we get here, all Qs are empty
					// we can now shut down the workers
                    updateTimestamp = System.currentTimeMillis();
                    long shutdownAbort = updateTimestamp + maxShutdownTimeValue;
					for (int i = 0; i < workers.size(); i++) {
						workers.get(i).stop();
					}
					Thread.sleep(300);
					boolean threadsStillRunning = true;
					while (threadsStillRunning) {
					    if (System.currentTimeMillis() > shutdownAbort) {
        					for (int i = 0; i < workers.size(); i++) {
        					    if (workers.get(i).isRunning()) {
        					        workers.get(i).stopNow();
        					    }
        					}
					    } else {
					        threadsStillRunning = false;
                            for (int i = 0; i < workers.size(); i++) {
                                if (workers.get(i).isRunning()) {
                                    threadsStillRunning = true;
                                }
                            }
					    }
                        if (threadsStillRunning) {
                            Thread.sleep(100);
                        }
					}
				} else { 
				    // not stopping, almost 1 second elapsed.
				    // do we need more or less workers?
				    wqLen = getWorkerQLength();
    				burst -= wqLen;
    				if (saveTimeDelta !=0) {
    				    System.out.println("processed " + burst + " items in " + saveTimeDelta + " ms, loop time="+timeDelta);
    				} else {
    				    System.out.println("processed " + burst + " items in " + timeDelta + " ms");
    				    saveTimeDelta = timeDelta;
    				}
                    if (wqLen > 0 || saveTimeDelta > processingTooSlowTime ) {
                        needMoreWorkers = true;
                        System.out.println("more workers needed Q = " + wqLen + ", tDelta="+saveTimeDelta);
                    }
    				if (needMoreWorkers) {
    				    int nbrNeeded = wqLen / workers.size();
    				    if (nbrNeeded == 0) {
    				        nbrNeeded = 1;
    				    }
    				    for (int i = 0; i < nbrNeeded; i++) {
        				    if (workers.size() < maxThreads) {
        				        // do not start them until we give them work
    				            int n = workers.size();
    				            MultiThreadWorker mtw = new MultiThreadWorker("Worker "+ n, workerQueue, resultQueue); 
                                workers.add(mtw);
        				    }
    				    }
    				    needMoreWorkers = false;
    				    tooManyWorkers = false;
    				}
    				if (tooManyWorkers) {
    				    if (workers.size() > minThreads  && saveTimeDelta > 0 && wqLen == 0) {
    				        // remove 1 worker
    				        int n=workers.size() - 1;
        				    MultiThreadWorker mtw = workers.get(n);
    				        workers.remove(n);
        				    mtw.stop(); // this allows it to shut down in an orderly fashion
        				                // and return its result if it is processing
    				    }
    				    tooManyWorkers = false;
    				}
				}
			} catch (InterruptedException ex) {
				logger.warning("Thread was interrupted : " + ex.getMessage());
			}
		}
		closeDBConnection(stopFile.exists());
		if (stopFile.exists()) {
		    stopFile.delete();
		}

	    long deltaT = updateTimestamp - startTime;
	    double decSecs = ((double) deltaT) / 1000;
	    int seconds = (int) (deltaT / 1000);
	    double average = Math.floor(((double) counter * 1000) / ((double) deltaT)*100)/100;		// per second 2 decimal places
	    logger.info(" #### - Stopping - processed " + counter + " requests in "+ decSecs + " seconds" +
	                " ==> average = " + average + " per second");
	    System.out.println(" #### - Stopping - processed " + counter + " requests in "+ decSecs + " seconds" +
                " ==> average = " + average + " per second");
	}

	private File performInit() {
		logger.info("##### Starting Geocoding Application");

		//readConfigFile(configFilePath);

		// Update 'urlSuffix' value to include 'clientID'
		//urlSuffix = urlSuffix + "&client=" + clientID;

		// Create Service 'StopFile'
		File stopFile = new File(serviceStopFile);

		return stopFile;
	}

	private void openDBConnection() {
		if (simulateDB) {
			try {
				userDir = System.getProperty("user.dir");
				simulFileName = userDir+"/scripts/address_data.csv";
				simulFile = new File(simulFileName);
				dbOutput = new BufferedReader(new FileReader(simulFileName));
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (postgresConnection == null) {
			try {
				Class.forName(postgresDriver);
				postgresConnection = DriverManager.getConnection(postgresUrl, postgresUser, postgresPassword);
				logger.info("DB Connection was created for " + postgresConnection);
			} catch (ClassNotFoundException ex) {
				logger.severe("DB Connection was NOT Created (" + postgresDriver + " : " + postgresConnection + ") - " + ex.getMessage());
				logger.severe("Finishing Geocoding Application - No DB Connection...");
				System.exit(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void closeDBConnection(boolean stopFile) {
		if (stopFile) {
			logger.info("Finishing Geocoding Application - 'Stop' File was found...");
		} else {
			logger.info("Finishing Geocoding Application - Something called 'exit()'...");

		}
		if (simulateDB) {
			try {
				dbOutput.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (postgresConnection != null) {
			try {
				postgresConnection.close();
			} catch (SQLException ex) {
				logger.severe("DB Connection was NOT Closed " + ex.getMessage());
			}
		}
	}

	private void readConfigFile(String path) {
		try {
			JSONParser parser = new JSONParser();
			Object obj;
			obj = parser.parse(new FileReader(path));
			JSONObject jsonObject = (JSONObject) obj;
			// TODO
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
            System.out.println("position: " + e.getPosition());
            System.out.println(e);
		}
	}		

	private List performQuery(int qLen) {
		List zx = new ArrayList();
		String s;
		int nbrToRead = requestsPerSecond - qLen;
		if (nbrToRead < minNbrToRead) {
		    nbrToRead = minNbrToRead; // keep ticking over
		}
		for (int i=0;i<nbrToRead;i++) {
			try {
				s = dbOutput.readLine();
				if (s == null) {
					stopping = true;
					return zx;
				}
				zx.add(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		if (zx.size() < nbrToRead) {
			stopping = true;
		}
		return zx;
		
	}
	
	// individual methods of the queue are synchronized, but different methods use different locks
	// thus we need to synchronize access so that put and take done simultaneously will leave the queue 
	// in a consistent state
	private boolean allWorkersIdle() {
        synchronized (workerQueue) {
            if (workerQueue.size() != 0) {
                return false;
            }
            for (int i=0;i<workers.size();i++) {
                if (workers.get(i).isProcessing) {
                    return false;
                }
            }
            return true;
        }    
	}

    public int getWorkerQLength() {
        synchronized (workerQueue) {
            return workerQueue.size();
        }
    }
    public int getResultQLength() {
        synchronized (resultQueue) {
            return resultQueue.size();
        }
    }
    
    public ServiceResult getFromResultQ() throws InterruptedException{
        synchronized (resultQueue) {
            return resultQueue.take();
        }
    }

    public void putToWorkerQ(InputDTO dto) throws InterruptedException {
        synchronized (workerQueue) {
            workerQueue.put(dto);
        }
    }
    
    private void processResultQItem() throws InterruptedException{
        // process 1 entry in our input q
        System.out.println("processing ResultQ len=" + getResultQLength());
        ServiceResult sr = getFromResultQ();
        Thread.sleep(pseudoProcessingSleepTimeValue);
    }
}
