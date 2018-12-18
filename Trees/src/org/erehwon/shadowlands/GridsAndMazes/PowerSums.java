package org.erehwon.shadowlands.GridsAndMazes;

import java.util.Vector;

public class PowerSums {
	int[][] sumOfTwoPowers(int n, int k) {
		Vector<Integer> v = new Vector<>();
		int p = 0;
		for (int i = 0;p<=n;i++) { // list out possible factors
			p=(int) Math.pow(i, k);
			if (p<=n) {
				v.add(new Integer(p));
			}
		}
		// System.out.println(dim2Str(vPwr));
		Vector z = new Vector();// candidate pairs
		for (int i=0; i<v.size();i++) {
			for (int j=i; j<v.size();j++ ) {
                int m = v.get(i)+v.get(j);
				if (m==n){
					int[] y={v.get(i),v.get(j)};
					z.add(y);
                }
				if (m>n){
					break;
				}
			}
		}
		
		return (int[][]) z.toArray(new int[z.size()][2]);
		
}

	 public static String dim2Str(Vector<Integer> x) {
	 	StringBuilder s= new StringBuilder("[");
	 	for (int i=0;i<x.size();i++) {
	 		if (i>0) {
	 			s.append(' ');
	 		}
	 		//s.append('"');
	 		s.append(x.get(i));
	 		//s.append('"');
	 		if (i<(x.size()-1)) {
	 			s.append(", ");
	 		}
	 	}
	 	s.append("]");
	 	return s.toString();
	 }


}
