package org.erehwon.shadowlands.GridsAndMazes;

import java.util.Arrays;

public class OrderdArraysDemo {

	static Integer[] test1 = {100, 20, 50, 70, 45};
	static Integer[] expt1 = {100, 60, 50, 60, 50};
	static Integer[] test2 = {10, 20, 30};
	static Integer[] expt2 = {10, 15, 20};
	static Integer[] test3 = {98, 91, 70, 26, 75, 91, 30, 88, 86};
	static Integer[] expt3 = {98, 95, 91, 81, 75, 83, 75, 82, 86};
	static Integer[] test4 = {93, 65, 48, 30, 23, 91, 24, 57, 98, 71, 60, 97};
	static Integer[] expt4 = {93, 79, 65, 57, 48, 57, 48, 53, 57, 61, 60, 63};
	static Integer[] test5 = {82, 65, 70, 67, 70, 50, 67, 85, 52, 98, 51, 76, 60, 51, 73, 61, 75, 89, 57, 50, 73, 96, 76, 65, 76, 64, 71, 55, 86, 50, 50, 87, 65, 59, 60, 56, 57, 74, 80, 50};
	static Integer[] expt5 = {82, 74, 70, 69, 70, 69, 67, 69, 67, 69, 67, 69, 67, 67, 67, 67, 67, 69, 67, 67, 67, 69, 70, 69, 70, 69, 70, 69, 70, 69, 67, 69, 67, 67, 67, 66, 65, 66, 67, 66};
	static Integer[] test6 = {1, 2, 3};
	static Integer[] expt6 = {1, 2, 2};
	static Integer[] test7 = {20, 11, 45, 0, 0, 40, 12, 88, 56, 66, 96, 32, 79, 98, 96, 57, 72, 33, 15, 14};
	static Integer[] expt7 = {20, 16, 20, 16, 11, 16, 12, 16, 20, 30, 40, 36, 40, 43, 45, 51, 56, 51, 45, 43};
	static Integer[] test8 = {100};
	static Integer[] expt8 = {100};
	static Integer[] test9 = {97, 65, 90, 95, 95, 78, 80, 84, 70, 67, 87, 71, 53, 65, 86, 76, 81, 67, 78, 84, 92, 84, 75, 55, 58, 55, 59, 71, 91, 76, 54, 58};
	static Integer[] expt9 = {97, 81, 90, 93, 95, 93, 90, 87, 84, 82, 84, 82, 80, 79, 80, 79, 80, 79, 78, 79, 80, 81, 80, 79, 78, 78, 78, 77, 78, 77, 76, 76};
	static Integer[] test10 = {54, 68, 57, 91, 50, 78, 75, 55, 51, 56, 59, 60, 65, 63, 82, 86, 90, 62, 99, 53, 63, 56, 50, 56, 91, 50, 99, 95, 52, 69, 70, 53, 80, 83, 81, 91, 59, 51, 87, 66, 62, 57, 82, 67, 68, 51, 71, 66, 53, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	static Integer[] expt10 = {54, 61, 57, 63, 57, 63, 68, 63, 57, 57, 57, 58, 59, 60, 60, 62, 63, 63, 63, 63, 63, 63, 62, 61, 62, 61, 62, 63, 62, 63, 63, 63, 63, 63, 63, 64, 63, 63, 63, 64, 63, 63, 63, 64, 65, 64, 65, 66, 65, 64, 63, 63, 63, 63, 62, 62, 62, 61, 60, 60, 60, 60, 59, 59, 59, 58, 57, 57, 57, 57, 56, 56, 56, 56, 56, 56, 55, 55, 54, 54, 53, 53, 53, 53, 53, 53, 52, 52, 51, 51, 51, 51, 51, 51, 50, 50, 50, 50, 50, 25};

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[][] tests = {test1, test2, test3, test4, test5, test6, test7, test8, test9, test10};
		Integer[][] expts = {expt1, expt2, expt3, expt4, expt5, expt6, expt7, expt8, expt9, expt10};
		for (int i=0;i<tests.length;i++) {
			runTest(tests[i], expts[i], i+1);
		}
	}
 	static void runTest(Integer[] nbrs, Integer[] exp, int tst) {
 		OrderedArrayUtils dd = new OrderedArrayUtils();
 		Integer[] res = dd.medianScores(nbrs); // 12 fails
 		if (Arrays.deepEquals((Integer[]) exp, (Integer[]) res)) {
 			System.out.println("Test" + tst + ": Equal:"+int2Str(res));
 		} else {
 			System.out.println("Test" + tst + ": Expected: " + int2Str(exp) +"    Results: " + int2Str(res));
		
 		}
	}
	 public static String int2Str(Integer[] ba) {
		 	StringBuilder s= new StringBuilder("[");
		 	for (int i=0;i<ba.length;i++) {
		 		if (i>0) {
		 			s.append(' ');
		 		}
		 		//s.append('"');
		 		s.append(ba[i]);
		 		//s.append('"');
		 		if (i<(ba.length-1)) {
		 			s.append(", ");
		 		}
		 	}
		 	s.append("]");
		 	return s.toString();
		 }

	

}
