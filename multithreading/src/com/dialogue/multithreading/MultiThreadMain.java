package com.dialogue.multithreading;

import java.io.File;


public class MultiThreadMain {

	private String configFilePath = "/opt/local/geocode/conf/geocode_config.json";	

	public static void main(String[] args) {
	
		MultiThreadOrchestrator orchestrator = new MultiThreadOrchestrator();
		
		orchestrator.run();

	}

}
