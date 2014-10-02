package com.dialogue.multithreading;

import java.util.Random;

public class MultiThreadWorker  implements Runnable {
    private Thread t;
    private String threadName;
    private boolean stopping = false;
    private boolean running = false;
    public int sleepTime = 20;
    public int sleepAdj = 23;
    public int qLength = 0;
	public MultiThreadWorker(String name) {
		threadName = name;
		System.out.println("Creating " +  threadName);		
	}

	@Override
	public void run() {
		Random rand = new Random();
		long currentTime = System.currentTimeMillis();
		running = true;
		Long cnt = (long) 0;
	      System.out.println("Running " +  threadName );
	      try {
	    	  while (!stopping) {
	    		  cnt++;
	            System.out.println("Thread: " + threadName + ", row " + cnt + ", Qlen = " + qLength);
                int pauseTime = rand.nextInt(61)+180; // random web request time 180..240
	            if (qLength > 0) {
	                // Let the thread sleep for a while.
	                // simulate processing
    	            System.out.println("Thread: " + threadName + ", waiting for " + pauseTime);
    	            Thread.sleep(pauseTime);
	            }
	            if (qLength > 0) { // decrement Q after processing
	            	qLength -= 1;
	            }
	            if (qLength == 0) {  // simulate q empty
	            	long endTime = System.currentTimeMillis();
	            	int deltaT = (int) (endTime - currentTime);
	            	if (deltaT < 1000) { // less than a second has elapsed
	            		sleepTime = sleepAdj;
	            		System.out.println("Thread: " + threadName + ", sleeping for " + sleepTime);
	            		Thread.sleep(sleepTime);
	            	} else {
	            		System.out.println("Thread: " + threadName + ", NOT sleeping elapsed " + deltaT);
	            	}
            		currentTime = System.currentTimeMillis();
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
        t = new Thread (this, threadName);
        t.start ();
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
}
