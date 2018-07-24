package org.erehwon.shadowlands.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.erehwon.shadowlands.utils.SimpleStringScanner;

public class TreeParser {
	BinaryTree tree = null;
	List<Node> bottomNodes = null;
	int currentDepth = 0;


	
	
	
	// constructors
	TreeParser() {
		tree = new BinaryTree();
	}

	TreeParser(String sTree) {
		tree = new BinaryTree();
		SimpleStringScanner scnr = new SimpleStringScanner(sTree);
		if (scnr.hasNextChar()) {
			tree.setRoot(parseNode(scnr));
		}
	}
	
	// (9 () () )
	Node parseNode(SimpleStringScanner scnr) {
		Node node = new Node();
		char ch = scnr.lookAhead1().charAt(0);
		if (ch == '(') {
			scnr.nextChar(); // consume the '('
			ch = scnr.lookAhead1().charAt(0);
			if (ch == ')') {
				// empty node
				scnr.nextChar(); // consume the ')'
				return null;
			}
			node.key = parseKey(scnr);
			node.left = parseNode(scnr);
			node.right = parseNode(scnr);
			if (scnr.hasNextChar()) {
				ch = scnr.nextChar();  // consume the ')'
			}
		}
		return node;
	}
	
	int parseKey(SimpleStringScanner scnr) {
		String s = scnr.lookAhead1();
		int i = -1;
		if (s.matches("[\\d]")) {
			i = Integer.parseInt(scnr.nextNumeric());
		}
		return i;
	}
	
	public int[] treeBottom(BinaryTree tree) {
	    bottomNodes = new ArrayList<Node>();
        currentDepth = 0;
		
		processDepth(tree.root, 0);
		
  	    int[] result = new int[bottomNodes.size()];
		for (int i = 0; i < bottomNodes.size(); i++) {
		    result[i] = (int)  (bottomNodes.get(i).key);
		}
		return result; 

	}
	
	void processDepth(Node node, int iDepth) {
		if (node == null || node.key == null) return;
		iDepth++;
		processDepth(node.left, iDepth);
		processDepth(node.right, iDepth);
		addToTree(node, iDepth);
	}

	void addToTree(Node x, int iDepth){

//		   System.out.println("In addToTree " + x);
		   if (iDepth > currentDepth) {
		       bottomNodes = new ArrayList<Node>();
		       currentDepth = iDepth;
		       bottomNodes.add(x);
		    } else
		    if (iDepth == currentDepth) {
		       bottomNodes.add(x);
		    }
		}
		 

}




