package org.erehwon.shadowlands.GridsAndMazes;

import java.math.BigDecimal;

public class Frac {
	/*
	 * 1. replace repeat out to 10 places and trunc to this limit
	 * 2. convert to big decimal
	 * 3. mpy by denominator
	 * 4. round to 0 places
	 * 5. return int 
	*/
	int extraCreditAssignment(String di, int dn) {
		String[] s = di.split("\\.");
		String[] n = s[1].split("\\(");
		StringBuilder b = new StringBuilder(s[0]);
		b.append(".");
		//b.append(n[0]); // non repeating fraction part
		if (n.length>1) {
			s[1] = n[1].substring(0, n[1].length()-1);
			if (s[1].length() > 0) {
				while (n[0].length() < 10) {
					n[0] += s[1];
				}
			}
		}
		int i = n[0].length() > 10? 10 : n[0].length() ;
		b.append(n[0].substring(0, i));

		BigDecimal d = new BigDecimal(b.toString());
		
		d = d.multiply(new BigDecimal(dn));
		double dx = d.doubleValue();
		int ix = (int) Math.round(dx);
		
		
		return ix;
	}


}
