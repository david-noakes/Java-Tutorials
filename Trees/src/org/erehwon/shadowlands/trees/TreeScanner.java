package org.erehwon.shadowlands.trees;

import java.util.Scanner;
import java.util.regex.Pattern;  
import org.erehwon.shadowlands.utils.SimpleStringScanner;
/*
 * 
 * abc…    Letters
 * 123…    Digits
 * \d      Any Digit
 * \D      Any Non-digit character
 * .       Any Character
 * \.      Period
 * [abc]   Only a, b, or c
 * [^abc]  Not a, b, nor c
 * [a-z]   Characters a to z
 * [0-9]   Numbers 0 to 9
 * \w      Any Alphanumeric character
 * \W      Any Non-alphanumeric character
 * {m}     m Repetitions
 * {m,n}   m to n Repetitions
 * *       Zero or more repetitions
 * +       One or more repetitions
 * ?       Optional character
 * \s      Any Whitespace
 * \S      Any Non-whitespace character
 * ^…$     Starts and ends
 * (…)     Capture Group
 * (a(bc)) Capture Sub-group
 * (.*)    Capture all
 * (ab|cd) Matches ab or cd
 */

public class TreeScanner {

	SimpleStringScanner scan;
	String BasicDelimiterSet = "[\\()]";
	String BasicPattern = "([0-9]*|[\\(]|[\\)])";
	Pattern basicPtrn = Pattern.compile(BasicPattern) ;
	String leftBracket = "[\\(]";  // ************
	Pattern leftBPtrn = Pattern.compile(leftBracket);
	String rightBracket = "[)]";
	String numberRegex = "[0-9]*";
	public void createTree(String sTree) {
	   Scanner sc=new Scanner(sTree);
//	   sc.useDelimiter(BasicDelimiterSet);
	   System.out.println("input: " + sTree);
	   System.out.println("scanner output");
	   while (sc.hasNext()) {
		   if (sc.hasNext("[(]")) {
			   // remove 
			   System.out.println(sc.next(leftBPtrn));
			   parseNode(sc);
		   } else {
			   System.out.println("" + sc.next());
		   }
	   }
	}
	
	public void parseNode(Scanner sc) {
		// n (...) (...) )
 	   System.out.println("parseNode");
		if (sc.hasNextInt()) {
		   System.out.println(sc.nextInt());
		}
  	    if (sc.hasNext(leftBracket)) {
		   // remove 
		   System.out.println(sc.next(leftBracket));
		   parseLeft(sc);
	    } else {
		   System.out.println(sc.next());
	    }
  	    if (sc.hasNext(leftBracket)) {
		   // remove 
		   System.out.println(sc.next(leftBracket));
		   parseRight(sc);
	    } else {
		   System.out.println(sc.next());
	    }
  	    if (sc.hasNext(rightBracket)) {
		   // remove 
		   System.out.println(sc.next(rightBracket));
	    } else {
		   System.out.println(sc.next());
	    }
	}
	
	public void parseLeft(Scanner sc) {
		// n )
 	   System.out.println("parseLeft");
		if (sc.hasNextInt()) {
		   System.out.println(sc.nextInt());
		}
  	    if (sc.hasNext(rightBracket)) {
		   // remove 
		   System.out.println(sc.next(rightBracket));
	    } else {
		   System.out.println(sc.next());
	    }
	}
	
	public void parseRight(Scanner sc) {
		// n )
 	   System.out.println("parseRight");
		if (sc.hasNextInt()) {
			   System.out.println(sc.nextInt());
			}
  	    if (sc.hasNext(rightBracket)) {
		   // remove 
		   System.out.println(sc.next(rightBracket));
	    } else {
		   System.out.println(sc.next());
	    }
	}
}
