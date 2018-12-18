package org.erehwon.shadowlands.GridsAndMazes;


public class FracDemo {

	static String dec1 = "0.(3)";
	static String dec2 = "0.0(25)";
	static String dec3 = "4.6";
	static String dec4 = "3.6(81)";
	static String dec5 = "40.708(3)";
	static String dec6 = "5.(717948)";
	static String dec7 = "0.8(010582)";
	static String dec8 = "0.(769230)";
	static String dec9 = "1.0(4811715)";
	static String dec13 = "1.3(724279835390946502057613168)";
	static String dec22 = "0.(402376910016977928692699490662139219015280135823429541595925297113752122241086587436332767)";
	
	static int den1 = 3;
	static int den2 = 594;
	static int den3 = 5;
	static int den4 = 264;
	static int den5 = 24;
	static int den6 = 78;
	static int den7 = 945;
	static int den8 = 65;
	static int den9 = 478;
	static int den13 = 486;
	static int den22 = 589;
	
	static int exp1 = 1;
	static int exp2 = 15;
	static int exp3 = 23;
	static int exp4 = 972;
	static int exp5 = 977;
	static int exp6 = 446;
	static int exp7 = 757;
	static int exp8 = 50;
	static int exp9 = 501;
	static int exp13 = 667;
	static int exp22 = 237;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] deci = {dec1, dec2, dec3, dec4, dec5, dec6, dec7, dec8, dec9, dec13, dec22};
		int[] deni = {den1, den2, den3, den4, den5, den6, den7, den8, den9, den13, den22};
		int[] expi = {exp1, exp2, exp3, exp4, exp5, exp6, exp7, exp8, exp9, exp13, exp22};
		
		runTest(dec6, den6, exp6, 6);
		for (int i = 0; i < deci.length;i++) {
			runTest(deci[i], deni[i], expi[i], 1+i);
		}
	}

	static void runTest(String dec, int denom, int exp, int testNum) {
		Frac fo = new Frac();
		int res = fo.extraCreditAssignment(dec, denom);
		 if (exp == res) {
			System.out.println("Test" + testNum + ": Equal:"+res);
		 } else {
		 	System.out.println("Test" + testNum + ": Expected: " + exp + ",  Results : " + res);
		 }
	}

}
