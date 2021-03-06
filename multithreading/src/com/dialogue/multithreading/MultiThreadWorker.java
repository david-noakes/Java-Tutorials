package com.dialogue.multithreading;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MultiThreadWorker  implements Runnable {
    private Thread t;
    private String threadName;
    private boolean stopping = false;
    private boolean running = false;
    private static int smallSleepTime = 11; 
    private static int pollWaitTime = 3;  // not currently used. 
    // poll takes an average of 10 microseconds to fail
    // polling can cause a blocking loop for the workers than can last for up to 500ms
    private int sleepTime = 20;
    private static int sleepAdj = 17;
    private static int almost1Second = 987;
    public  boolean isProcessing = false;
    public long currentTime = System.currentTimeMillis();
    
    ///////////////////////////////////////////////
    // QUEUES - Thread safe - shared between all workers and controller
    private ArrayBlockingQueue<InputDTO> workerQueue = null;
    private ArrayBlockingQueue<ServiceResult> resultQueue = null;
    
    
    
	public MultiThreadWorker(String name, ArrayBlockingQueue<InputDTO> wQ, ArrayBlockingQueue<ServiceResult> rQ ) {
		threadName = name;
		System.out.println("Creating " +  threadName);		
		workerQueue = wQ;
		resultQueue = rQ;
	}

	@Override
	public void run() {
		Random rand = new Random();
		InputDTO dto = null;
		ServiceResult res = null;
		OutputDTO outDTO = null;
		running = true;
        System.out.println("Running " +  threadName );
	    try {
	        currentTime = System.currentTimeMillis();
	    	while (!stopping) {
                int pauseTime = rand.nextInt(51)+180; // random web request time 180..230
	            if (getQLength() > 0) {
	                // prevent blocking loop by checking Q length first
                    dto = getFromWorkerQ();   // may return null
                    if (dto != null) {
	                // simulate processing = Let the thread sleep for a while.
    	                System.out.println("Thread: " + threadName + ", row " + dto.getRowNbr() + ", Qlen = " + getQLength() +
    	                                   ", working for " + pauseTime);
        	            Thread.sleep(pauseTime);
        	            // send back the results
        	            outDTO = new OutputDTO(dto);
        	            res = new ServiceResult(MultiThreadOrchestrator.statusSuccess, outDTO);
        	            putToResultQ(res);
                    }
	            }
	            if (getQLength() == 0) {  
	            	long endTime = System.currentTimeMillis();
	            	int deltaT = (int) (endTime - currentTime);
                    if (deltaT < 100) { // less than a tenth second has elapsed
                        System.out.println("Thread: " + threadName + ", Qlen = " + getQLength() + ", sleeping for " + sleepAdj);
                        Thread.sleep(sleepAdj);
                    } else if (deltaT < almost1Second) { // less than a second has elapsed
                        sleepTime = 1000 - deltaT - sleepAdj;
                        if (sleepTime <= 0) {
                            sleepTime = smallSleepTime;
                        }
	            		System.out.println("Thread: " + threadName + ", Qlen = " + getQLength() + ", sleeping for " + sleepTime);
            		    Thread.sleep(sleepTime);
	            	} else {
	            		System.out.println("Thread: " + threadName + ", Qlen = " + getQLength() + ", elapsed " + deltaT + ", sleeping for " + smallSleepTime);
	            		Thread.sleep(smallSleepTime);
	            	}
            		//currentTime = System.currentTimeMillis();
	            }
	    	}  
	     } catch (InterruptedException e) {
	         System.out.println("Thread " +  threadName + " interrupted.");
	     }
	     System.out.println("Thread " +  threadName + " exiting.");
	     running = false;
	}
    public void start ()  {
	    System.out.println("Starting " +  threadName);
	    if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
	    }
   }

   public void stop() {
	   stopping = true;
   }
   public void stopNow() {
	   stopping = true;
	   t.interrupt(); 
   }

    public boolean isRunning() {
        return running;
    }
    
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    private int getQLength() {
        synchronized (workerQueue) {
            return workerQueue.size();
        }
    }
    
    private InputDTO getFromWorkerQ() throws InterruptedException {
        synchronized (workerQueue) {
        	if (workerQueue.size() > 0) {
	            // take() and poll(timeout) can block
	            // the timeout is erratic - often at least 5 to 10 times the value
	            // polling can cause a blocking loop that can run for up to 500ms for all workers
	            // by checking the Q length before trying take(), we generally 
	            // have sufficient rows to satisfy all workers.
	            long t1 = System.nanoTime();
	            InputDTO dto = workerQueue.take(); //poll(); //(pollWaitTime, TimeUnit.MICROSECONDS);
	            long t2 = System.nanoTime() - t1;
	            if (t2 > 100000) { // 10th of millisecond
	                System.out.println(threadName + " poll timeout="+t2+" nanos");
	            }
	            if (dto != null) {
	                isProcessing = true;
	            }
	            return dto;
        	} else {
        		return null;
        	}
        }
    }
    
    private void putToResultQ(ServiceResult sr) throws InterruptedException {
        synchronized (resultQueue) {
            resultQueue.put(sr);
            isProcessing = false;
        }
    }
    
}
