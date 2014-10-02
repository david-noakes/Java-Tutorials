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
    List<MultiThreadWorker> spareWorkers;
	
	
	
	// ////////////////////////////////////////////
	private Date currentTime;
	private static int maxThreads = 11;
	private static int minThreads = 3;
	private long sleepTime = 1;
	private long counter = 0;
	private boolean stopping = false;
	long updateTimestamp; 
	// ////////////////////////////////////////////

	
	// Current 'Sleep' timer - can be changed by this Application
	private static int currentSleepTimeValue = 1;
	// Default 'Sleep' timer - no more than 22 queries per second
	private static int defaultSleepTimeValue = 10;
	// housekeeping loop sleep timer - for processing our Q and waiting for 1 second to elapse
	private static int housekeepingSleepTimeValue = 17;
	// Long 'Sleep' timer when there are no records to process
	private static int longSleepTimeValue = 5000;
	// Timer increase value
	private static int increaseTimeValue = 100;
	private static final int almost1Second = 980;
	
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

		// ////////////////////////////////////////////
		workers = new ArrayList<MultiThreadWorker>(maxThreads);
		spareWorkers = new ArrayList<MultiThreadWorker>(maxThreads);
		for (int i = 0; i < minThreads; i++) {
			workers.add(new MultiThreadWorker("Worker "+ i));
		}

		// ////////////////////////////////////////////
		
		long currentTime  = System.currentTimeMillis();
        long startTime  = currentTime;
		int timeDelta = 0;

		while (!stopping) {
			
			// TODO - read dbOutput 22 rows at a time, and apportion to workers
			try {
				List rows = performQuery();  // could set stopping = true
				counter += rows.size();
				if (stopFile.exists()) {
					stopping = true;
				} 
				if (rows.size() > 0) {
					int n = 0;
					for (int i = 0; i<rows.size();i++) {
						workers.get(n).qLength = workers.get(n).qLength + 1;
						n++;
						if (n<workers.size()) {
						} else {	
							n=0;
						}
					}
				}
                currentTime = System.currentTimeMillis();
				if (!workersStarted) {
				    // set this again
				    startTime = System.currentTimeMillis();
					for (int i = 0; i < workers.size(); i++) {
						workers.get(i).start();
					}
					workersStarted = true;
				}
				
				// allow the workers to do some work
				Thread.sleep(300);
				timeDelta = (int) (System.currentTimeMillis() - currentTime);
				// this is our houskeeping loop
				// process the output from the workers, update the database
				// there output is our inputQ - threadsafe
				while (timeDelta < almost1Second) {
	                if (allWorkerQsEmpty() && timeDelta < 800) {
	                    tooManyWorkers = true;
	                    logger.info("too many workers");
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
					Thread.sleep(500);
				} else { 
				    // not stopping, almost 1 second elapsed.
				    // do we need more or less workers?
    				if (!allWorkerQsEmpty()) {
    				    needMoreWorkers = true;
    				    logger.info("more workers needed Q = " + workerQLen());
    				}
    				if (needMoreWorkers) {
    				    if (workers.size() < maxThreads) {
    				        if (spareWorkers.size()>0) {
    	                        int n = 0;
    	                        MultiThreadWorker mtw = spareWorkers.get(n);
    	                        spareWorkers.remove(n);
    	                        mtw.start();
    	                        workers.add(mtw);
    				        } else {
    				            int n = workers.size();
    				            MultiThreadWorker mtw = new MultiThreadWorker("Worker "+ n); 
                                mtw.start();
                                workers.add(mtw);
    				        }
    				    }
    				    needMoreWorkers = false;
    				    tooManyWorkers = false;
    				}
    				if (tooManyWorkers) {
    				    if (workers.size() > minThreads) {
    				       int n = workers.size() - 1;
    				       MultiThreadWorker mtw = workers.get(n);
    				       workers.remove(n);
    				       mtw.stop();
    				       spareWorkers.add(mtw);
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
	    long average = (counter * 1000) / deltaT;		// per second
	    logger.info(" #### - Stopping - processed " + counter + " requests in "+ decSecs + " seconds" +
	                " ==> average = " + average + " per second");
	    System.out.println(" #### - Stopping - processed " + counter + " requests in "+ decSecs + " seconds" +
                " ==> average = " + average + " per second");
	}

	private File performInit() {
		logger.info("##### Starting Geocoding Application");

		//readConfigFile(configFilePath);

		// Set 'Sleep' timer to 'Default' (Working) status
		currentSleepTimeValue = defaultSleepTimeValue;

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

	private List performQuery() {
		List zx = new ArrayList();
		String s;
		for (int i=0;i<22;i++) {
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
		if (zx.size() < 22) {
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

	
}
