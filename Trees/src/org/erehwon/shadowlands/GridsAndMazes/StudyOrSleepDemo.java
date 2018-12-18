package org.erehwon.shadowlands.GridsAndMazes;

public class StudyOrSleepDemo {

	static int familiarity1 = 38;
	static int familiarity3 = 75;
	static int familiarity6 = 35;
	
	static int hoursRemain1 = 9;
	static int hoursRemain3 = 8;
	static int hoursRemain6 = 1;
	
	static int exp1 = 5;
	static int exp3 = 0;
	static int exp6 = 1;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		runTest(familiarity6, hoursRemain6, exp6, 6);
		runTest(familiarity1, hoursRemain1, exp1, 1);
		runTest(familiarity3, hoursRemain3, exp3, 3);
	}

	static void runTest(int famil, int hRem, int exp, int testNum) {
		StudyOrSleep so = new StudyOrSleep();
		int res = so.studyOrSleep(famil, hRem);
		 if (exp == res) {
			System.out.println("Test" + testNum + ": Equal:"+res);
		 } else {
		 	System.out.println("Test" + testNum + ": Expected: " + exp + ",  Results : " + res);
		 }
	}

}
