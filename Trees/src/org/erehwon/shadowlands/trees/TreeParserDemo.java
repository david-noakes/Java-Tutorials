package org.erehwon.shadowlands.trees;

import java.util.Arrays;

public class TreeParserDemo {

	   //Tree data:
		static String Test1 = "(1 () ())";	
		static String Test2 = "(413 (683 () ()) (355 (913 (985 () ()) ()) ()))";
		static String Test3 = "(2 (7 (2 () ()) (6 (5 () ()) (11 () ()))) (5 () (9 (4 () ()) ())))";
		static String Test4 = "(1 (2 (4 (8 () ()) (9 () ())) (5 (10 () ()) (11 () ()))) (3 (6 (12 () ()) (13 () ())) (7 (14 () ()) (15 () ()))))";
		static String Test5 = "(1 () (2 () (3 () (5 () (8 () (13 () (21 () (34 () ()))))))))";

	   // Expected Output:
		static int[] Expected1 = new int[]	{1};
		static int[] Expected2 = new int[]	{985};
		static int[] Expected3 = new int[]	{5, 11, 4};
		static int[] Expected4 = new int[]  {8, 9, 10, 11, 12, 13, 14, 15};
		static int[] Expected5 = new int[]  {34};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		TreeParser underTest = new TreeParser(Test1) ;
//		System.out.println("input: " + Test1);
//		System.out.println("result: " + underTest.tree.toString());
		runTest(Test1, Expected1, "1");
		runTest(Test2, Expected2, "2");
		runTest(Test3, Expected3, "3");
		runTest(Test4, Expected4, "4");
		runTest(Test5, Expected5, "5");
	}
	public static void runTest(String test, int[] expected, String testNum) {
		TreeParser leaf = new TreeParser(test) ;
		System.out.println("input: " + test);
		System.out.println("result: " + leaf.tree.toString());
		int[] results = leaf.treeBottom(leaf.tree);
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
