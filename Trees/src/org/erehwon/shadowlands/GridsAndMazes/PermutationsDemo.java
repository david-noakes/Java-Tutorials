package org.erehwon.shadowlands.GridsAndMazes;

public class PermutationsDemo {

	static String[] std01 = {"Dave,BG","Lynn,BG","Andy,V","Lucy,D"};
	static int      exp01 = 2;
	static String[] std02 = {"Kim,GV", "Ben,BDG", "Edd,DGV", "Lyn,BDG"};
	static int      exp02 = 6;
	static String[] std03 = {"Jim,BD", "Ann,V", "Ken,G", "Joe,GV"};
	static int      exp03 = 0;
	static String[] std04 = {"Sara,V", "Mark,BD", "Anna,GV", "Tina,G", "Tony,BDGV", "Neil,BG"};
	static int      exp04 = 20;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		runTest(std01, exp01,1);
		runTest(std02, exp02,2);
		runTest(std03, exp03,3);
		runTest(std04, exp04,4);
		String[] std05 = new String[(std01.length+std02.length+std03.length+std04.length)*10];
		int x=0;
		for (int i = 0;i<10;i++) {
			for (String s:std01) {
				std05[x++]=s;
			}
			for (String s:std02) {
				std05[x++]=s;
			}
			for (String s:std03) {
				std05[x++]=s;
			}
			for (String s:std04) {
				std05[x++]=s;
			}
		}
		runTest(std05, 52147840,5);
		
	}
	static void runTest(String[] s, int exp, int testNum ) {
		Permutations sh = new Permutations();
		int res = (int) sh.rockBandMembers(s);
		if (exp == res) {
			System.out.println("Test" + testNum + ": Equal:"+res);
		} else {
		 	System.out.println("Test" + testNum + ": Expected: " + exp + ",  Results : " + res);
		}
	}

}
