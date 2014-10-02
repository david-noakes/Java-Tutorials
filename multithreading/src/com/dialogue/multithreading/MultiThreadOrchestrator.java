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


import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MultiThreadOrchestrator {

	// Logger
	static Logger logger = Logger.getLogger("dialog.multithreading.logger");
	private static String configFilePath = "/opt/local/geocode/conf/geocode_config.json";
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
	private int inputQLength = 0; 
	List<MultiThreadWorker> workers;
	private boolean tooManyWorkers = false;
	private boolean needMoreWorkers = false;
	
	
	
	// ////////////////////////////////////////////
	private static int maxThreads = 11;
	private static int minThreads = 3;
	private static int nbrThreadsInit = 11;
	private boolean stopping = false;
	long updateTimestamp; 
	// ////////////////////////////////////////////

	
	// Default 'Sleep' timer - no more than 22 queries per second
	private static int defaultSleepTimeValue = 10;
	// housekeeping loop sleep timer - for processing our Q and waiting for 1 second to elapse
	private static int housekeepingSleepTimeValue = 11;
	// Long 'Sleep' timer when there are no records to process
	private static int longSleepTimeValue = 5000;
	// Timer increase value
	private static int increaseTimeValue = 100;
	private static int almost1Second =  1000 - housekeepingSleepTimeValue +1;
	private static int minNbrToRead = minThreads;
	
	private final String statusProcessing = "PROCESSING";
	private final String statusFailed = "FAILED";
	private final String statusRetry = "RETRY";

	// DB Connection
	private Connection postgresConnection = null;


	private final String queryRead = "SELECT * FROM address_read WHERE status <> '" + statusProcessing
			+ "' AND status <> '" + statusFailed 
			+ "' ORDER BY priority DESC, status ASC, record_timestamp ASC LIMIT 22;";
	
	
	
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
		workers = new ArrayList<MultiThreadWorker>(maxThreads);
		for (int i = 0; i < nbrThreadsInit; i++) {
			workers.add(new MultiThreadWorker("Worker "+ i));
		}

		// ////////////////////////////////////////////
		
		long currentTime  = System.currentTimeMillis();
        long startTime  = currentTime;
		int timeDelta = 0;
		int saveTimeDelta = 0;

		while (!stopping) {
		    almost1Second = 1000 - housekeepingSleepTimeValue +1;
			// TODO - read dbOutput 22 rows at a time, and apportion to workers
			try {
				List rows = performQuery(wqLen);  // could set stopping = true
				counter += rows.size();
				System.out.println("read "+rows.size()+ " qLen = " + wqLen);
				burst = rows.size() + workerQLen(); // how many we are trying to process this second
				if (stopFile.exists()) {
					stopping = true;
				} 
				if (rows.size() > 0) {
				    // load levelling - not needed first time round, but it doesn't matter
					for (int i = 0; i<rows.size();i++) {
					    MultiThreadWorker mtw = workerWithShortestQ();
					    mtw.qLength += 1;
					}
				}
                currentTime = System.currentTimeMillis();
                saveTimeDelta = 0;
				if (!workersStarted) {
				    // set this again
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
                        }
                    }
				}
				
				// allow the workers to do some work
				Thread.sleep(300);
				timeDelta = (int) (System.currentTimeMillis() - currentTime);
				// this is our houskeeping loop
				// process the output from the workers, update the database
				// there output is our inputQ - threadsafe
				while (timeDelta < almost1Second) {
	                if (allWorkerQsEmpty()) {
	                    if (saveTimeDelta == 0) {
	                        saveTimeDelta = timeDelta;
	                    }
	                    if (!stopping && !tooManyWorkers  && timeDelta < 800) {
    	                    tooManyWorkers = true;
    	                    System.out.println("too many workers @ "+timeDelta);
	                    }
	                }
				    if (inputQLength > 0) {
				        // process 1 entry in our input q
				    } else {
				       Thread.sleep(housekeepingSleepTimeValue); 
				    }
				    timeDelta = (int) (System.currentTimeMillis() - currentTime);
				}
				
				// almost 1 second has elapsed.
				
				if (stopping) {  // not a panic abort, but orderly shutdown
					while (!allWorkerQsEmpty() || inputQLength > 0) {
	                    if (inputQLength > 0) {
	                        // process 1 entry in our input q
	                    } else {
	                       Thread.sleep(housekeepingSleepTimeValue); 
	                    }
					}
					// we get here, all Qs are empty
					// we can now shut down the workers
                    updateTimestamp = System.currentTimeMillis();
					for (int i = 0; i < workers.size(); i++) {
						workers.get(i).stop();
					}
					Thread.sleep(500); 
					for (int i = 0; i < workers.size(); i++) {
					    if (workers.get(i).isRunning()) {
					        workers.get(i).stopNow();
					    }
					}
					Thread.sleep(300);
				} else { 
				    // not stopping, almost 1 second elapsed.
				    // do we need more or less workers?
				    wqLen = workerQLen();
    				if (wqLen > 0) {
    				    needMoreWorkers = true;
    				    System.out.println("more workers needed Q = " + wqLen);
    				}
    				burst -= wqLen;
    				if (saveTimeDelta !=0) {
    				    System.out.println("processed " + burst + " items in " + saveTimeDelta + " ms");
    				} else {
    				    System.out.println("processed " + burst + " items in " + timeDelta + " ms");
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
    				            MultiThreadWorker mtw = new MultiThreadWorker("Worker "+ n); 
                                workers.add(mtw);
        				    }
    				    }
    				    needMoreWorkers = false;
    				    tooManyWorkers = false;
    				}
    				if (tooManyWorkers) {
    				    if (workers.size() > minThreads  && saveTimeDelta > 0) {
    				        // we need a fraction of the workers removed.
    				        //int nbrToRemove = (int) (((double)(1000-saveTimeDelta)) / ((double) 1000) * ((double) workers.size()));
    				        //System.out.println("removing " + nbrToRemove + " threads");
                            int nbrToRemove = 1;
    				        int n=workers.size() - 1;
    				        while (n > minThreads && nbrToRemove > 0) {
        				        MultiThreadWorker mtw = workers.get(n);
        				        if (mtw.qLength == 0) {
        				            workers.remove(n);
        				            mtw.stop();
        				            nbrToRemove--;
                                }
        				        n--;
    				        }
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
		int nbrToRead = 22 - qLen;
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
	
	private boolean allWorkerQsEmpty() {

	    for (int i = 0; i < workers.size(); i++) {
            if (workers.get(i).qLength > 0 ) {
                return false;
            }
        }

	    return true;
	}

	private int workerQLen(){
	    int len = 0;
        for (int i = 0; i < workers.size(); i++) {
            len += workers.get(i).qLength;
        }
	    return len;
	}
    public int getInputQLength() {
        return inputQLength;
    }

    public void setInputQLength(int inputQLength) {
        this.inputQLength = inputQLength;
    }

    private MultiThreadWorker workerWithShortestQ(){
        int n = workers.size() - 1;
        int mtwQlen = 999; 
        MultiThreadWorker mtw = workers.get(n);
        mtwQlen = mtw.qLength;
        for (int i = n - 1; i >=0; i--) {
            if (workers.get(i).qLength < mtwQlen) {
                mtw = workers.get(i);
                mtwQlen = mtw.qLength;
            }
        }
        return mtw;
    }
	
}
