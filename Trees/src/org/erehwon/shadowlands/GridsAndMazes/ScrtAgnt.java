package org.erehwon.shadowlands.GridsAndMazes;

import java.util.Arrays;
import java.util.List;

public class ScrtAgnt {
	String[] secretAgentsMeetingProposal(String iM, int cD) {
	    ky=Arrays.asList(ks);
		String[] a=new String[2];
		a[0] = decode(iM, cD);
		String b[] = a[0].split("\\-"); 
	    if (b[0].equals("today")) {
	      if (b[2].equals("park")){a[1]=y;}
	      else {a[1]=n;}
	    } else if (b[0].equals("tomorrow")){
	      if((b[1].equals("afternoon")&& b[2].equals("park"))||
	        (b[1].equals("night")&&b[2].equals("bar"))){a[1]=y;} 
	      else {a[1]=n;}
	   } else {
	      if(b[1].equals("morning")&&b[2].equals("restaurant")){a[1]=y;}
	      else {a[1]=n;}
	   }		
			
		return a;
	}

	List<String> ky;       
	String[] ks = {"0","9","8","7","6","5","4","10","11","12","13","14","15","16","17","18","*",      "@",        "#",    "?"};
	String[] vs = {"a","e","i","o","u","y","w","t", "d", "s", "n", "m", "r", "b", "k", "p", "morning","afternoon","night","-"};
	String y="5.9.12";
	String n="13.7";
	String decode(String s, int c){
		StringBuilder sb=new StringBuilder();
			// TODO;
	    String[] x=s.split("\\.");
	    for (String t:x) {
	          //loop
	    	int j = ky.indexOf(t);
	    	if (j<0) {
	    		// this is the augment code "_"
	    		for (int i=0;i<16;i++) {
	    			String q=ks[i];
    				int k = Integer.parseInt(q);
    				k-=c;
    				ks[i]=Integer.toString(k);
	    		}
	    		j+=0;
	    	} else if (j<ks.length) {
	    		sb.append(vs[j]);
	    	} 
	    }
			
		return sb.toString();
	}
}
