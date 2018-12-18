package org.erehwon.shadowlands.GridsAndMazes;

import java.util.ArrayList;
import java.util.List;

public class SupplyHeur {
	
	int stringChainReplacements(String[] sA) { //stringArray
		int c; // change counter
		int l; // length of string
		int m; // minimum

	    l=sA.length;
	    m=0;
	    if (l<2) return 0;
	    for (int i=0;i<l-1;i++)    {
	        if (sA[i].charAt(sA[i].length()-1)!=sA[i+1].charAt(0))
	            m++;
	        if (sA[i+1].length()==1) {
	            int j=i+1;
	            while (sA[j].length()==1 && j<l-1) {
	                if (sA[i].charAt(sA[i].length()-1)==sA[++j].charAt(0))
	                    m--;
	            }
	        }
	            
	    }
	    return m;
	}

	int greedySupplyGigs(String[][] o) {
		g = 0;
		b = new ArrayList<>();  // blocked list
		u  = new ArrayList<>();  // used list
		for (int i=0;i<o.length;i++){
			int m = 0;
			ur = null; // used during round
			for (int j=0;j<o[i].length;j++) {
				
				String s = o[i][j];
				if (s.length() == 0) {
					continue;
				}
				Node c = new Node(s);
				if (b.contains(c)) {
					continue;
				}
				if (u.contains(c)) {
					Node d = c;
					c = u.get(u.indexOf(c));
					c.v = d.v;
				} else {
					u.add(c);
				}
				c.oc++;
				if (c.v>m) {
					if (ur!=null) {
						ur.uc--;
						unUse(ur);
					}
					m = c.v;
					ur = c;
					c.uc++;
				} else {
					unUse(c);
				}
				
			} // end loop
			g += m;
		}
		
		return g ;
	}

	int optimalSupplyGigs(String[][] offers) {
	    if (offers.length == 0) {
	        return 0;
	    }
	    Node[][] nO = new Node[offers.length][];
	    for (int i=0;i<offers.length;i++){
	        nO[i] = new Node[offers[i].length];
	        for (int j=0;j<offers[i].length;j++) {
	            String s = offers[i][j];
	            if (s.length() == 0) {
	                continue;
	            }
	            Node n = new Node(s) ;
	            nO[i][j]=n;
	        }
	    }
	    mG = g = 0;

	    u  = new ArrayList<>();  // used list
	    sew(0,0,nO);
	    return mG;
	}

	void sew(int row, int col, Node[][] o) {
	    Node c;
	    Node d;
	    c = d = null;
	    if (col == 0) { // add all to the used list, we need to block all those we reject.
	        for (int i = 0;i <o[row].length;i++) {
	            d = o[row][i];
	            if (u.contains(d)) {
	                d = u.get(u.indexOf(d)); 
	            } else {
	                d = d.clone();
	                u.add(d);
	            }
					d.oc++; // mark as used
	        }
	    }
	    boolean bu = false;
	    while (col < o[row].length) { 
	        c = o[row][col];
	        if (u.contains(c)) { // always true now
	            d = c;
	            c = u.get(u.indexOf(c));
	            c.v = d.v;
				c.uc++;
	        } else {
	            c = c.clone(); // clone because we need to be able to wind back using original data
	            u.add(c);
	        }
	        if (c.ok(row)) {
	            // c.oc++;  
	            g += c.v;
	            bu = true;
	            // c.uc++; 
	        } else {
	            c.uc--;
	            col++;
	            if (col < o[row].length ) {
	                continue; // pick up next
	            }
	            if (bu) { // found at least 1, so we have threaded from there
	                continue;
	            }
	        }
	        if (row < o.length -1) {
	            sew(row + 1, 0, o);
	            // we are winding back
	            if (col < o[row].length) {
	                d = o[row][col];
	                c.v = d.v;
	            }
	        } else {
	            if (g > mG) {
	                mG = g;
	            }
	        }
	        // wind back and try next offer
	        wBack(c);
	        col++;


	    } // end while
	     if (o[row].length == 0) { // special case - empty row
	         if (row < o.length -1) {
	             sew(row + 1, 0, o);
	             // we are winding back
	         } else {
	             if (g > mG) {
	                 mG = g;
	             }
	         }
	     }

		for (int i = 0;i <o[row].length;i++) {
			d = o[row][i];
			int k = u.indexOf(d);
			if (k>=0) {
				d = u.get(k);			
				d.oc = (d.oc>0)? d.oc-1: 0; // mark as used
				// if (d.oc == 0) {
				// 	u.remove(k);
				// }
			}
		}
	
	}
	
	
	Node ur;
	List<Node> b;  // blocked list
	List<Node> u;  // used list
	int g;
	int mG; // max g

	void unUse(Node x) {
        if ((x.oc - x.uc)*2 > x.uc ) {
//                x.ok = false;
			b.add(x);
		}
	}
	void wBack(Node x) {
		if (x.ok())	{
			g -= x.v;
			x.uc = (x.uc > 0)? x.uc-1: 0;
		}
	}

	class Node {
		boolean ok() {
			return 	!((oc - uc)*2 > uc); // blocked
		}
		boolean ok(int row) {
			if (row == r) {
				return 	!((oc - uc)*2 > uc); // blocked
			} else {
				return 	!(((oc-1) - (uc-1))*2 > (uc-1)); // blocked
			}
		}
		String n;
		int v;
		int oc; // offer count
		int uc; // used count
		int r; // first added row - used to differentiate between newly added not used.
		Node() {
			n = "";
			oc = uc = v = 0;
		}
		// X $zz
		Node(String s) {
			String[] z = s.split("\\ \\$");
			n = z[0];
			v = Integer.parseInt(z[1]);
			r = oc = uc = 0;
		}
		public Node clone() {
			Node c = new Node();
			c.n = n;
			c.oc = oc;
			c.uc = uc;
			c.v = v;
			c.r = r;
			return c;
		}
		@Override
		public boolean equals(Object o) {
			if (o instanceof Node) {
				return n.equals(((Node) o).n);
			}
			return false;
		}
	}
	

}
