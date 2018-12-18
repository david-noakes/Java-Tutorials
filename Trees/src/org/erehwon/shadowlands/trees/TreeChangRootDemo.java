package org.erehwon.shadowlands.trees;

import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Arrays;

public class TreeChangRootDemo {
	
	static int[] Test1 = {0, 0, 0, 1};	
	static int[] Test2 = {0, 0, 0, 1, 1, 1, 2, 2, 7};	
	static int[] Test3 = {0, 0, 0, 1, 1, 1, 2, 2, 7, 7};	
	static int[] Test4 = {3, 3, 2, 2, 0};	
	static int[] Test5 = {8, 6, 8, 8, 7, 6, 8, 8, 8, 7};	
	static int[] Test6 = {5, 3, 0, 5, 10, 5, 5, 0, 10, 10, 0, 13, 3, 3, 2};	
	static int[] Test7 = {5, 18, 18, 1, 19, 19, 0, 2, 4, 2, 11, 11, 1, 1, 5, 2, 18, 4, 4, 11};	
	static int[] Test8 = {21, 20, 24, 7, 1, 13, 0, 13, 20, 12, 2, 20, 20, 12, 12, 0, 24, 10, 12, 1, 20, 20, 1, 7, 12};	

	static int NewRoot1 = 1;
	static int NewRoot2 = 7;
	static int NewRoot3 = 2;
	static int NewRoot4 = 0;
	static int NewRoot5 = 7;
	static int NewRoot6 = 8;
	static int NewRoot7 = 2;
	static int NewRoot8 = 10;
	
	static int[] Expected1 = new int[]	{1, 1, 0, 1};
	static int[] Expected2 = new int[]	{2, 0, 7, 1, 1, 1, 2, 7, 7};
	static int[] Expected3 = new int[]	{2, 0, 2, 1, 1, 1, 2, 2, 7, 7};
	static int[] Expected4 = new int[]	{0, 3, 3, 0, 0};
	static int[] Expected5 = new int[]	{8, 6, 8, 8, 7, 6, 8, 7, 7, 7};
	static int[] Expected6 = new int[]	{10, 3, 0, 5, 10, 0, 5, 0, 8, 10, 8, 13, 3, 3, 2};
	static int[] Expected7 = new int[]	{5, 18, 2, 1, 18, 19, 0, 2, 4, 2, 11, 19, 1, 1, 5, 2, 18, 4, 2, 4};
	static int[] Expected8 = new int[]	{21, 20, 10, 7, 1, 13, 0, 13, 20, 12, 10, 20, 24, 12, 12, 0, 24, 10, 12, 1, 12, 20, 1, 7, 2};


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] tests = new int[][] {Test1, Test2, Test3, Test4, Test5, Test6, Test7, Test8};
		int[][] expecteds = new int[][] {Expected1, Expected2, Expected3, Expected4, Expected5, Expected6, Expected7, Expected8};
		int[] newroots = new int[] {NewRoot1, NewRoot2, NewRoot3, NewRoot4, NewRoot5, NewRoot6, NewRoot7, NewRoot8};
		
//		runTest(Test1, NewRoot1, Expected1, 1);
		
		for (int i = 0; i<tests.length; i++) {
			runTest(tests[i], newroots[i], expecteds[i], i+1);			
		}

	}
	
	static void runTest(int[] theTest, int newRoot, int[] expected, int testNum) {
		TreeChangeRoot underTest = new TreeChangeRoot();
		int[] results = underTest.changeRoot(theTest, newRoot);
		String exp = Arrays.toString(expected); 
		String res = Arrays.toString(results);
		if (exp.equals(res)) {
			System.out.println("Test" + testNum + ": Equal:"+res);
		} else {
			System.out.println("Test" + testNum + ": Expected: " + exp);
			System.out.println("       Results : " + res);
		}
		
	}

}
