package org.erehwon.shadowlands.trees;

import org.erehwon.shadowlands.utils.SimpleStringScanner;

class Struct {
	int nbr;
	String prefix;
	String encoded;
	String sNbr;
	int parseIdx;

	Struct() {
		nbr = parseIdx = 0;
		encoded = prefix = sNbr = "";
	}
}

public class DecodeString {
	SimpleStringScanner scan;

	String decodeString(String s) {
		scan = new SimpleStringScanner(s);
		String result = "";
		Struct node = parseInput(scan);
		result = node.prefix;
		for (int i = 0; i < node.nbr; i++) {
			result += node.encoded;
		}
		return result;
	}

	Struct processNode(Struct node) {
		Struct newNode = new Struct();
		String result = node.prefix;
		for (int i = 0; i < node.nbr; i++) {
			result += node.encoded;
		}
		if (node.nbr == 0 && node.encoded.length() > 0) {
			result += node.encoded;
		}
		newNode.prefix = result;
		newNode.parseIdx = node.parseIdx;
		return newNode;
	}

	Struct parseInput(SimpleStringScanner scan) {
		Struct node = new Struct();
		int i = scan.getIdx();
		while (scan.hasNext()) {
			node.parseIdx = scan.getIdx();
			char ch = scan.lookAhead1().charAt(0);
			if (Character.isAlphabetic(ch) || (ch == '-') || (ch == '_')) {
				node.prefix += scan.nextAlphaExt("\\-_");
			} else if (Character.isDigit(ch)) {
				node.sNbr = scan.nextNumeric();
			} else if (ch == '[') {
				if (node.sNbr.trim().length() > 0) {
					node.nbr = Integer.parseInt(node.sNbr);
					node.sNbr = "";
				}
				ch = scan.nextChar();
				Struct subNode = parseInput(scan);
				if (subNode.parseIdx > 0) {
					i = scan.getIdx();
				}
				// parsed node becomes the
				Struct node1 = processNode(subNode);
				node.encoded += node1.prefix;
				// now roll it up
				node = processNode(node);

			} else if (ch == ']') {
				ch = scan.nextChar();
				if (node.encoded.length() == 0) {
					node.encoded = node.prefix;
					node.prefix = "";
//				} else {
//					node.encoded = node.prefix + node.encoded;
//					node.prefix = "";
				}
				break;
			} else { // assume = rubbish
				ch = scan.nextChar();
			}
			i = scan.getIdx();
		}
		if (node.sNbr.trim().length() > 0) {
			node.nbr = Integer.parseInt(node.sNbr);
		}
		return node;
	}
}
