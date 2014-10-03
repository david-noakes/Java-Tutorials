package com.dialogue.multithreading;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class MultiThreadWorker  implements Runnable {
    private Thread t;
    private String threadName;
    private boolean stopping = false;
    private boolean running = false;
    private static int smallSleepTime = 7; 
    private int sleepTime = 20;
    private static int sleepAdj = 17;
    private static int almost1Second = 987;
    public  boolean isProcessing = false;
    public long currentTime = System.currentTimeMillis();
    
    ///////////////////////////////////////////////
    // QUEUES - Thread safe
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
		currentTime = System.currentTimeMillis();
		InputDTO dto = null;
		ServiceResult res = null;
		OutputDTO outDTO = null;
		running = true;
        System.out.println("Running " +  threadName );
	    try {
	    	  while (!stopping) {
                int pauseTime = rand.nextInt(51)+180; // random web request time 180..230
	            if (getQLength() > 0) {
                    dto = getFromWorkerQ();   // may block
	                // simulate processing = Let the thread sleep for a while.
	                System.out.println("Thread: " + threadName + ", row " + dto.getRowNbr() + ", Qlen = " + getQLength() +
	                                   ", working for " + pauseTime);
    	            Thread.sleep(pauseTime);
    	            // send back the results
    	            outDTO = new OutputDTO(dto.getData());
    	            res = new ServiceResult(MultiThreadOrchestrator.statusSuccess, outDTO);
    	            putToResultQ(res);
	            }
	            if (getQLength() == 0) {  
	            	long endTime = System.currentTimeMillis();
	            	int deltaT = (int) (endTime - currentTime);
                    if (deltaT < 100) { // less than a tenth second has elapsed
                        System.out.println("Thread: " + threadName + ", Qlen = " + getQLength() + ", sleeping for " + smallSleepTime);
                        Thread.sleep(smallSleepTime);
                    } else if (deltaT < almost1Second) { // less than a second has elapsed
                        sleepTime = 1000 - deltaT - sleepAdj;
                        if (sleepTime < 0) {
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
            InputDTO dto = workerQueue.take(); 
            isProcessing = true;
            return dto;
        }
    }
    
    private void putToResultQ(ServiceResult sr) throws InterruptedException {
        synchronized (resultQueue) {
            resultQueue.put(sr);
            isProcessing = false;
        }
    }
    
}
