package org.erehwon.shadowlands.GridsAndMazes;

public class OrderedArrayUtils {
	
	Integer[] medianScores(Integer[] s) {
	    Integer[] o = new Integer[s.length];
	    int[] m = new int[s.length];
	    int c=0; int d;
	    int t=0;
	    int r;
	    for (int i: s){
	        // median, not mean
	        c++;d=c-1;
	        // m[d]=i;
	        // Arrays.sort(m,0,c); // c is exclusive
	        add(m,i,d);  // gets in under the limit, is shorter
	        // g(m,i,d); //fastest using binary search
	        if (c==1) {
	            o[0]=i;
	       } else if ((c % 2) == 0) {// even
	            //divide 2 middle ones need sorted list  c/2 & +1
	            o[d]=(m[d/2]+m[d/2+1])/2; //TODO round up
	            if  (((m[d/2]+m[d/2+1])%2)!=0) {
	                 o[d]++;
	            }
	       } else { // odd
	            // return middle one - need sorted list - c/2
	            o[d]=m[d/2];
	       }
	    }
	    return o;
	}
	void add(int[] a, int i, int c) {
		// choose between linear search and binary search
		// c = end of list +1, i = item
		if (c<10) {
			addItem(a,i,c);
		} else {
			addBinSearch(a,i,c);
		}
		
		
	}
	void addItem(int[] a, int i, int c){// add item in sorted order brute force
		// c = theoretical position, i = item
		if (c==0) {
			a[0]=i;
			return;
		}
		int m=c-1;
		for (;m>=0;m--) { // find the spot
			if (a[m]<=i)
				break;
		}
		for (int n=c-1;n>m;n--) { // move up to create the hole
			a[n+1]=a[n];
		}  
		a[m+1]=i;  // plug the hole
	}

	 void addBinSearch(int[] a, int i, int c){  // add item using binary search for better performance
	 	if (c==0) {
	 		a[0]=i;
	 		return;
	 	}
	 	int f=0;//first
	 	int l=c-1;// last
	 	int m=l/2; // middle
	 	while (f<=l){
	 		if (a[m]<i){
	 			f=m+1;
	 		} else if (a[m]==i){
	 			break;
	 		} else {
	 			l=m-1;
	 		}
	 		m=(f+l)/2;
	 	}
	 	// make the hole
	 	if (a[m]>i) m--;
	 	for (int n=c-1;n>m;n--) { // move up
	 		a[n+1]=a[n];
	 	}  
	 	a[m+1]=i;
	 }

	// note ordered lists or array sorts are too slow
	// binary search is fastest, but linear search is fast enough with the data
}
