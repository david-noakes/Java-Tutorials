package org.erehwon.shadowlands.GridsAndMazes;

import java.util.Arrays;

public class PowerSumsDemo {
 	static int num1 = 17;
 	static int pow1 = 2;
 	static int[][] exp1 = {{1,16}};
 	static int num2 = 27;
 	static int pow2 = 3;
 	static int[][] exp2 = {{0,27}};
 	static int num3 = 16;
 	static int pow3 = 5;
 	static int[][] exp3 = {};
 	static int num4 = 65;
 	static int pow4 = 2;
 	static int[][] exp4 = {{1,64}, {16, 49}};
 	static int num5 = 1107625;
 	static int pow5 = 10;
 	static int[][] exp5 = {{59049,1048576}};
 	static int num6 = 50;
 	static int pow6 = 2;
 	static int[][] exp6 = {{1,49}, {25, 25}};
 	static int num7 = 2465;
 	static int pow7 = 2;
 	static int[][] exp7 = {{64,2401}, {256, 2209}, {529, 1936}, {784, 1681}};
 	static int num8 = 125000000;
 	static int pow8 = 2;
 	static int[][] exp8 = {{1345600,123654400},{4000000,121000000},{25000000,100000000},{32993536,92006464},{57760000,67240000}};
 	static int num9 = 1729;
 	static int pow9 = 3;
 	static int[][] exp9 = {{1,1728}, {729, 1000}};
 	static int numa = 4104;
 	static int powa = 3;
 	static int[][] expa = {{8,4096}, {729, 3375}};
 	static int numb = 87539319;
 	static int powb = 3;
 	static int[][] expb = {{4657463,82881856}, {11852352,75686967}, {16581375,70957944}};
 	static int numc = 2315;
 	static int powc = 7;
 	static int[][] expc = {{128,2187}};



	
 	public static void main(String[] args) {
 		// TODO Auto-generated method stub
 		int[] nums = {num1, num2, num3, num4, num5, num6, num7, num8, num9, numa, numb, numc};
 		int[] pows = {pow1, pow2, pow3, pow4, pow5, pow6, pow7, pow8, pow9, powa, powb, powc};
 		int[][][] exps = {exp1, exp2, exp3, exp4, exp5, exp6, exp7, exp8, exp9, expa, expb, expc};
		
 		for (int i = 0;i<nums.length;i++) {
 			runTest(nums[i], pows[i], exps[i], i+1 );
 		}
 	}

 	public static void runTest(int num, int pwr, int[][] exp, int tst) {
 		PowerSums ps = new PowerSums();
 		int[][] res = ps.sumOfTwoPowers(num, pwr);
 		if (Arrays.deepEquals(exp, res)) {
 			System.out.println("Test" + tst + ": Equal:"+dim2Str(res));
 		} else {
 			System.out.println("Test" + tst + ": Expected: " + dim2Str(exp) +"    Results: " + dim2Str(res));
		
 		}
 	}
 	public static String dim2Str(int[][] x) {
 		StringBuilder s= new StringBuilder("[");
 		for (int i=0;i<x.length;i++) {
 			s.append("[");
 			for (int j=0;j<x[i].length;j++) {
 				s.append(x[i][j]);
 				if (j<x[i].length-1) {
 					s.append(", ");
 				}
 			}
 			if (i<(x.length-1)) {
 				s.append("], ");
 			} else {
 				s.append("]");
 			}
 		}
 		s.append("]");
 		return s.toString();
 	}

}
