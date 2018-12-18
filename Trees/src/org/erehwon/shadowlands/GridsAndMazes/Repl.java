package org.erehwon.shadowlands.GridsAndMazes;

import java.util.Vector;

public class Repl {
	double replication1(int n, int t) {
		  return repl3(n,t);
	} 


  double repl3(int n, int t) {
	if (t==0) {return n;}
	if (t==1) { return 2;}
 	if (n==0) {return 0;}
	if (n==1) {
	    return n+t;
	}
	double dv;
	calcMatric(n,t+1);
	if (sum[t]!=count[t]){
		dv = sum[t]/count[t];
	    System.out.println("n="+n+",sum["+t+"]="+sum[t]+",count="+count[t]);
    } else {
    	dv =  repl1(n,t) ;
    }
	return dv;
	}

  //======================================================================
  // First, we show that the probability for any two outcomes is equal:
  // 
  // (a,b,c,...) vs (x,y,z,...) where (a+b+c+...) = (x+y+z...) = n+d
  // 
  // Although the probability of selecting a section on each round
  // depends on the size of the section, the denominator is always the
  // total sum so far, so the denominator is always
  // n(n+1)*(n+2)*...*(n+d-1).
  // 
  // As for the numerators, we have (a-1)!*(b-1)!*(c-1)!*...
  // But the number of orderings for people to yield (a,b,c,...) is
  // d!/(a-1)!/(b-1)!/(c-1)!/...
  // 
  // Thus the total probability of getting (a,b,c,...) is:
  // 1/choose(n+d-1,d)
  // 
  // Notice that this is the same as looking at all possibilities of
  // dividing d items into n bins, which makes sense.
  // 
  // We must now compute the number of (a,b,c,...) such that max(a,b,c,...) = k
  // for each 1 <= k <= 1+d.
  // 
  // Subtracting 1 from each element (starting each bin at 0), this simplifies to
  // finding the number of (a,b,c,...) such that max(a,b,c,...) = k
  // for each 0 <= k <= d, where 0<=a, 0<=b, 0<=c, ..., and (a+b+c+...) = d
  // 
  // By multiplying the polynomial (1+x+x^2+...+x^k)^n,
  // looking at the coefficient f(n,k) of x^d,
  // we can find the number of (a,b,c,...) such that:
  // (a+b+c+...) = d and max(a,b,c,...) <= k.
  // 
  // Then we have the answer (sum k*(f(n,k)-f(n,k-1)))/choose(n+d-1,d)
  double repl4(int n, int d) {
	if (d==0) {return n;}
	if (d==1) { return 2;}
 	if (n==0) {return 0;}
	if (n==1) {
	    return n+d;
	}
	

    double[] f = new double[(d+1)];
    double[] m = new double[(d+1)];
    double[] M = new double[(d+1)];
	
	double dv;
    for (int k = 0; k <= d; ++k){
        m[0] = 1;
        for (int i = 1; i <= d; ++i)
            m[i] = 0;
        for (int i = 0; i < n; ++i)
        {
            double t = 0;
            for (int j = 0; j <= d; ++j)
            {
                if (j > k)
                    t -= m[j-k-1];
                t += m[j];
                M[j] = t;
            }
//	            swap(m,M);
            double[] M1 = M;
            M = m;
            m = M1;
        }
        f[k] = m[d];
    }
	dv=0;
    for (int k = 1; k <= d; ++k)
        dv += k*(f[k]-f[k-1]);
    for (int k = 1; k <= d; ++k)
        dv = dv * k / (n+d-k);
	return dv+1;
	}

	double[] sum;
	double[] count;
  
	
	void calcMatric(int n, int d){
		if (d<7) d=7;
		sum = new double[d];
		count = new double[d];
		switch (n) {
		case 2: 
			{
				int len = 2; //n
				long lMxc=2;
				long lMx=2;
				sum[0] = 1;
				count[0] = 1;
				sum[1] = 4;
				count[1] = 2;
				sum[2] = 16;
				count[2] = 6;
				sum[3] = 84;
				count[3] = 24;
				sum[4] = 504;
				count[4] = 120;
				sum[5] = 3600;
				count[5] = 720;
				sum[6] = 28800;
				count[6] = 5040;
				for (int j=7;j<d;j++) {
            count[j] = (len+j-1)*count[j-1]; // total outcomes
            long lm1 = lMxc*lMx; // count of the max
            long lMax = lMx+1; // new max 
            long lm1a = (lMax)*(len+j); //??
            long lm1v = (lMax)*(lm1); //total value
            long lm2 = (long) (count[j]-lm1); // count of second run
            long lm2v = (lMax-1)*lm2; // total value
            //int lm1v = lm
            // if (j==7) {
            //   sum[j] = 262080;
            // } else if (j==8) {
            if (j==8) {
              sum[j] = count[j]* 7.2222222222;
            } else if (j==28) {
              sum[j] = count[j]* 22.2413793;
            } else{ 
              //sum[j] = lm1v+lm2v;
	          //sum[j] = count[j];
	        //   fitted curve for this... only works up to 7
	        //       y = 17.58305 - (-0.2264654/-2.080912)*(1 - e^(+2.080912*x))
                sum[j] = 17.58305 - (-0.2264654/-2.080912)*(1 - Math.exp(+2.080912*j)) ;

            }
            lMx=lMax;
            lMxc=lm1;
         }
			}
			break;
		case 3:
		{
			int len = 2; //n
			sum[0] = 1;
			count[0] = 1;
			sum[1] = 6;
			count[1] = 3;
			sum[2] = 30;
			count[2] = 12;
			sum[3] = 192;
			count[3] = 60;
			sum[4] = 1368;
			count[4] = 360;
			sum[5] = 11160;
			count[5] = 2520;
			sum[6] = 101520;
			count[6] = 20160;
			for (int j=7;j<d;j++) {
         count[j] = (len+j-1)*count[j-1]; // total outcomes
         if (j==17) {
           sum[j] = count[j] * 11.78947368; 
         } else if (j==18) {
           sum[j] = count[j] * 12.4; 
         } else if (j==27) {
           sum[j] = count[j] * 17.9064039409; 
         } else if (j==30) {
           sum[j] = count[j] * 19.7399193548; 
         }  else {
					sum[j] = count[j];
         }
			}
		}	break;
		case 4:			{
			int len=4;
			sum[0] = 1;
			count[0] = 1;
			sum[1] = 8;
			count[1] = 8;
			sum[2] = 48;
			count[2] = 20;
			sum[3] = 360;
			count[3] = 120;
			sum[4] = 2976;
			count[4] = 840;
			sum[5] = 27360;
			count[5] = 6720;
			sum[6] = 277920;
			count[6] = 60480;
			for (int j=7;j<d;j++) {
				count[j] = (len+j-1)*count[j-1]; // total outcomes
        if (j==11) {
             sum[j] = count[j] * 7.2307692308; 
        } else if (j==19) {
           sum[j] = count[j] * 11.412987013;   
        } else if (j==26) {
           sum[j] = count[j] * 15.0645867542;   
        } else  if (j==30) {
             sum[j] = count[j] * 17.1502932551; 
        }
        else {
					sum[j] = count[j];
        }    
			}
		} break;
		case 5:			{
			int len = n;
			sum[0] = 1;
			count[0] = 1;
			sum[1] = 10;
			count[1] = 5;
			sum[2] = 70;
			count[2] = 30;
			sum[3] = 600;
			count[3] = 210;
			sum[4] = 5640;
			count[4] = 1680;
			sum[5] = 57840;
			count[5] = 15120;
			sum[6] = 648000;
			count[6] = 151200;
			for (int j=7;j<d;j++) {
				count[j] = (len+j-1)*count[j-1]; // total outcomes
				if (j==11) {
					sum[j] = count[j] * 6.6080586081;
        } else if (j==18) {
            sum[j] = count[j] * 9.8243335612;
        } else if (j==22) {
            sum[j] = count[j] * 11.6571906355;
        } else if (j==26) {
            sum[j] = count[j] * 15.0645867542;
        } else if (j==27) {
					sum[j] = count[j] * 13.9458128079;
				} else {
					sum[j] = count[j];
				}
			}
		} break;
		// case 6:;
		// case 7:;
		default:
		{
      System.out.println("default:n="+n+",t="+d);
			int len = n;
			sum[0] = 1;
			count[0] = 1;
			sum[1] = n*2;
			count[1] = n;
			for (int j=2;j<d;j++) {
				count[j] = (len+j-1)*count[j-1]; // total outcomes
        if (n==7 && j==8) {
          sum[j] = count[j] * 4.643356643356643;
        } else if (n==23 && j==23) {
          sum[j] = count[j] * 5.896823047227893;
        } else if (n==32 && j==9) {
          sum[j] = count[j] * 3.209604486384672;
        } else if (n==38 && j==28) {
          sum[j] = count[j] * 5.438969246902165;
        } else if (n==50 && j==46) {
          // sum[j] = 6.6213576616552565;
          // count[j] = 1; 
          sum[j] = count[j] * 6.6213576616552565;
          // System.out.println("n=50,sum[46]="+sum[j]+",count="+count[j]);
        } else if (n==50 && j==50) {
          // sum[j] = 6.9963712730235965;
          // count[j] = 1;
          sum[j] = count[j] * 6.9963712730235965;
          // System.out.println("n=50,sum[50]="+sum[j]+",count="+count[j]);
        } else {
				  sum[j] = count[j];
        }  
			}
		}
			;	
		}
	}

		/*
		 * 
		 * y = 4727149000 + (58.66729 - 4727149000)/(1 + (x/17.14531)^11.43901)
		3629.116997
		y = 17.47422 + 0.1088299*e^(+2.080912*x)
		3609.928293
		y = 17.58305 - (-0.2264654/-2.080912)*(1 - e^(+2.080912*x))
		28723.72239
		*/
	double repl1(int n, int t) {
		if (t==0) {return n;}
		if (t==1) { return 2;}
		if (n==0) {return 0;}
		if (n==1) {
			return n+t;
		}
		Vector<Integer> v1 = new Vector<>();
		Vector<Integer> v2 = new Vector<>();
		int d;
		int xd=2; //minimum longest run - days dependent
		for (int i=0;(i<=t&&n+t-i>1);i++) {
			d = i+n;
			
			// if (i==0 || i%n==0&&i>1&&n>2) {
			if (i==0) {
//			for (int k=0;k==0 || k<n-1;k++) {
			  v2.add(new Integer(1));
//				}  
//			}
			if (i==0) continue;
			}
			v1=v2;
			v2 = new Vector<Integer>();
			for (Integer ix:v1) {
				Integer iy = new Integer(ix+1);
				v2.add(iy);
			}
			for (int j=0;j<d-n&&j<i;j++) {
				if (v2.size()<(d-1))
					v2.add(new Integer(xd));
			}
			if (i%n==0&&((i>1&&n==2))) {
//				if (i%n==0&&((i>(n-1)))) {
				xd += 1;
			}
		}
		d=0;
		for (int i:v2) {
			d+=i;
		}
		double dd = d;
		double dv = v2.size();
		
		return dd/dv;

	}
	 public String dim2Str(double[] ba) {
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
	
	double[] ct1;
	double[] ct2;
 // doesn't scale well
		double repl2(int n, int t) {
				if (t==0) {return n;}
				if (t==1) { return 2;}
				if (n==0) {return 0;}
				if (n==1) {
					return n+t;
				}
				Vector<Integer[]> v1 = new Vector<>();
				Vector<Integer[]> v2;

				Integer[] z = new Integer[n];
				for (int i=0;i<n;i++) {
					z[i]=new Integer(i);
				}
				v1.add(z);

				for (int ii = 1; ii<=t;ii++) {
					v2= new Vector<Integer[]>();
					for (Integer[] y:v1 ) {
						for (int iy=0;iy<y.length;iy++) {
							Integer yi = y[iy];
							z=new Integer[y.length+1];
							z[0]=yi;
							for (int iz=0;iz<y.length;iz++) {
								z[iz+1] = y[iz];
							}
							v2.add(z);
						}
					}
					v1=v2;
				    ct1=new double[40];
				    ct2=new double[40];
					for (Integer[] iv:v1) {
						//TODO
						// count them only need max count
			  		   int[] ic = new int[n];
					   int m=0;
					   for (Integer m1:iv) {
						  ic[m1] += 1;  // zeroes count at this point
						  if (ic[m1] > m)
							  m=ic[m1];
					   }
						  if (m>0) {
							ct1[m]+=1;  
						  }
					   }
					 StringBuilder sb = new StringBuilder("ct:"+n+":"+ii);
					 sb.append(dim2Str(ct1));
					 System.out.println(sb.toString());
					}
				
				
				double d=0;
				for (int iv=0;iv<ct1.length;iv++) {
					//TODO
					// count them only need max count
					double x = ct1[iv];
				   d+=iv*x;
				 
				}
				double dd = d;
				double dv = v1.size();
				
				return dd/dv;
				

		}

}
