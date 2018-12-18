package org.erehwon.shadowlands.GridsAndMazes;

public class Permutations {
	long rockBandMembers(String[] sts) {
	    // this one is the brute force method
	    // List<String> b,d,g,v;
	    // b=new ArrayList<>();
	    // d=new ArrayList<>();
	    // g=new ArrayList<>();
	    // v=new ArrayList<>();
	    // for (String s:sts ) {
	    //     String[] x=s.split(",");
	    //     char[] cx=x[1].toCharArray();
	    //     for (char c:cx) {
	    //         switch (c) {
	    //         case 'B':{b.add(x[0]);break;}
	    //         case 'D':{d.add(x[0]);break;}
	    //         case 'G':{g.add(x[0]);break;}
	    //         default: {v.add(x[0]);break;}
	    //         }
	    //     }
	    // }
	    // long t=0;
	    // for (String sb:b){
	    //     for (String sd:d){
	    //         if (sd.equals(sb)) continue;
	    //         for (String sg:g){
	    //             if (sg.equals(sb) || sg.equals(sd)) continue;
	    //             for (String sv:v){
	    //                 if (sv.equals(sg) || sv.equals(sd) || sv.equals(sb)) continue;
	    //                 t++;
	    //             }
	    //         }
	    //     }
	    // }
	    // t %= 1e9+7;
	    // return t;
		// brute force is fine for small sets. It gets out of hand rapidly as the permutations climb into the millions
		// this breaks the representation into a bit set, with 15 possible combinations of BDGV 
		// use an array [16] to use the bit set as an index
		// count each combination
		// permute the bits like before, catering for sets with multiple skills by subtracting 1 
		// from set count for each time we use the same index
		// multiply the counts and sum for the final result.
		// check against the brute force method for a range of numbers
		    long[] cnts = new long[16];
		    cnts[0] = 1;
		    int r;
			for (String s:sts ) {
				r=0;
				String[] x=s.split(",");
				char[] cx=x[1].toCharArray();
				for (char c:cx) {
					switch (c) {
					case 'B':{r|=8;break;}
					case 'D':{r|=4;break;}
					case 'G':{r|=2;break;}
					default: {r|=1;break;}
					}
				}
				cnts[r]+=1;
			}
			
			long t=0;
			long ac, dc, gc, vc, cc;
			for (int vi=1;vi<16;vi+=2){ // every second 1 is odd (1 in last place)
				vc = cnts[vi];
				for (int gi=2;gi<16;gi++){
					if ((gi&2)==0) continue;
					gc=cnts[gi];
					if (gi==vi) gc-- ;  // multiple talents, already using one
					if (gc<1) continue;
					for (int di=4;di<16;di++){
						if((di&4)==0) continue;
						dc=cnts[di];
						if(di==vi) dc--;
						if(di==gi) dc--;
						if (di<1) continue;
						for (int ai=8;ai<16;ai++){
							ac=cnts[ai];
							if(ai==vi) ac--;
							if(ai==gi) ac--;
							if(ai==di) ac--;
							if (ai<1) continue;
							cc = vc*gc*dc*ac;
							t += cc;
							t%= 1e9+7;
						}
					}
				}
			}
			return t;

	}

	// long rockBandMembers(String[] students) {
//	     long[] counters = new long[16];
//	     counters[0] = 1;
//	     for (String student:students){
//	         long[] add = new long[16];
//	         for (int role:student.split(",")[1].getBytes()){
//	             int pos = 1 << "BDGV".indexOf(role);
//	             for (int i = 0; i < 16; i++){
//	                 if ((pos & i) == 0){
//	                     add[pos | i] += counters[i];
//	                 }
//	             }
//	         }
//	         for (int i = 0; i < 16; i++){
//	             counters[i] += add[i];
//	             counters[i] %= 1e9+7;
//	         }
	        
//	     }

	    
//	     return counters[15];
	// }

}
