package org.erehwon.shadowlands.GridsAndMazes;

public class SupplyHeurDemo {

	
	static String[] test4 = {"q", "p", "r", "qrpst", "tv", "a"};
	static int      exp4 = 3;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		runTest(test4, exp4, 4);
	}

	static void runTest(String[] s, int exp, int testNum ) {
		SupplyHeur sh = new SupplyHeur();
		int res = sh.stringChainReplacements(s);
		if (exp == res) {
			System.out.println("Test" + testNum + ": Equal:"+res);
		} else {
		 	System.out.println("Test" + testNum + ": Expected: " + exp + ",  Results : " + res);
		}
	}
	static void runTest(String[][] offers, int exp, int testNum) {
		SupplyHeur sh = new SupplyHeur();
		int res = sh.greedySupplyGigs(offers);
		if (exp == res) {
			System.out.println("Test" + testNum + ": Equal:"+res);
		} else {
		 	System.out.println("Test" + testNum + ": Expected: " + exp + ",  Results : " + res);
		}
	}
}
