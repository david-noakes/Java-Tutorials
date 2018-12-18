package org.erehwon.shadowlands.GridsAndMazes;

import java.util.Arrays;

public class ScrtAgntDemo {
	
	static String test1 = "10.7.11.0.5.?.#.?._.15.-1.14";
	static int    diff1 = 1;
	static String[] exp1 = {"today-night-bar", "13.7"};
	static String test2 = "10.4.7.11.0.5._.10.?.*.?._.11.5.8.6.-4.2.11.-4.9.6";
	static int    diff2 = 2;
	static String[] exp2 = {"twodays-morning-restaurant", "5.9.12"};

	public static void main(String[] args) {
		runTest(test1, diff1, exp1, 1);
		runTest(test2, diff2, exp2, 2);
	}

	
	static void runTest(String img, int diff, String[] exp, int tst) {
 		ScrtAgnt sa = new ScrtAgnt();
 		String[] res= sa.secretAgentsMeetingProposal(img, diff);
 		if (Arrays.deepEquals(res, exp)) {
			System.out.println("Test" + tst + ": Equal:"+dim2Str(res));
		} else {
		 	System.out.println("Test" + tst + ": Expected: " + dim2Str(exp) + ",  Results : " + dim2Str(res));
		}
 	}

	
	 public static String dim2Str(String[] ba) {
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
