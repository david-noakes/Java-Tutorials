package org.erehwon.shadowlands.GridsAndMazes;

public class ReplDemo {
	public static int test1 = 1;
	public static int days1 = 10;
	public static int test2 = 2;
	public static int days2 = 8;
	public static int test3 = 3;
	public static int days3 = 8;
	public static int test4 = 4;
	public static int days4 = 8;
	public static int test5 = 5;
	public static int days5 = 8;
	public static int test6 = 6;
	public static int days6 = 8;
	public static int test7 = 7;
	public static int days7 = 8;
	public static int test8 = 8;
	public static int days8 = 8;
	public static int test9 = 9;
	public static int days9 = 8;
	static int test10 = 3;
	static int days10 = 8;
	static int test11 = 50;
	static int days11 = 50;
	static int test12 = 5;
	static int days12 = 11;
	static int test13 = 28;
	static int days13 = 47;
	static int test14 = 4;
	static int days14 = 7;
	static int test15 = 6;
	static int days15 = 10;
	static int test16 = 7;
	static int days16 = 10;
	static int test17 = 8;
	static int days17 = 10;
	static int test18 = 31;
	static int days18 = 47;
	static int test19 = 37;
	static int days19 = 47;
	static int test20 = 41;
	static int days20 = 47;
	static int test21 = 47;
	static int days21 = 47;
	static int test22 = 51;
	static int days22 = 51;
	static int test23 = 205;
	static int days23 = 499;
	static int test24 = 500;
	static int days24 = 500;

	public static double exp1 = 11;
	public static double exp2 = 7.727272727272727;
	public static double exp3 = 6.266666666666667;
	public static double exp4 = 5.654545454545454;
	public static double exp5 = 5.222222222222222;
	public static double exp6 = 4.897435897435898;
	public static double exp7 = 4.643356643356643;
	public static double exp8 = 4.438850038850039;
	public static double exp9 = 4.27062937062937;
	static double exp10 = 6.266666666666667;
	static double exp11 = 6.9963712730235965;
	static double exp12 = 6.6080586081;
	static double exp13 = 8.90878281962758;
	static double exp14 = 5.133333333333334;
	static double exp15 = 5.72927072927073;
	static double exp16 = 5.403846153846154;
	static double exp17 = 5.141711229946524;
	static double exp18 = 8.455747300254739;
	static double exp19 = 7.741353075709523;
	static double exp20 = 7.366263688010887;
	static double exp21 = 6.908323123780689;
	static double exp22 = 7.0245647077195414;
	static double exp23 = 17.65114739111052;
	static double exp24 = 10.300789183514535;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] peeps = {test1, test2, test3, test4, test5, test6, test7, test8, test9, test10, test11, test12, test13, test14, test15, test16, test17, test18, test19, test20, test21, test22, test23, test24};
		int[] days = {days1, days2, days3, days4, days5, days6, days7, days8, days9, days10, days11, days12, days13, days14, days15, days16, days17, days18, days19, days20, days21, days22, days23, days24};
		double[] exps = {exp1, exp2, exp3, exp4, exp5, exp6, exp7, exp8, exp9, exp10, exp11, exp12, exp13, exp14, exp15, exp16, exp17, exp18, exp19, exp20, exp21, exp22, exp23, exp24};
//		try {
//		runTest(test6, days6, exp6, 6);
//		}
//		catch (Exception e) {
//			
//		}
//		try {
//		runTest(test7, days7, exp7, 7);
//		}
//		catch (Exception e) {
//			
//		}
//		try {
//		runTest(test8, days8, exp8, 8);
//		}
//		catch (Exception e) {
//			
//		}
//		try {
//		runTest(test9, days9, exp9, 9);
//		}
//		catch (Exception e) {
//			
//		}
		for (int i=0;i<days.length;i++) {
			runTest(peeps[i], days[i], exps[i], i+1);
		}
	}
	static void runTest(int ppl, int days, double exp, int testNum) {
		Repl so = new Repl();
//		double res = so.repl1(ppl, days);
//		double res = so.repl2(ppl, days);
		double res = so.repl4(ppl,days);  // from C++ example
		res = doubleRounder(res,5);
		exp = doubleRounder(exp,5);
		 if (exp == res) {
			System.out.println("Test" + testNum + ": Equal:"+res);
		 } else {
		 	System.out.println("Test" + testNum + ": Expected: " + exp + ",  Results : " + res);
		 }
	}

	static double doubleRounder(double d, int p) {
		// round to p places
		double fact = 10;
		for (int i=2;i<=p;i++)
			fact *= 10;
		return Math.round(d*fact)/fact;
	}

}
