package org.erehwon.shadowlands.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// basic tree node

class Node {

	int key;
	Node left, right;

	public Node(int item) {
		key = item;
		left = right = null;
	}

	public Node() {
		key = -1;
		left = null;
		right = null;
	}
}

// A Java program to introduce Binary Tree

class BinaryTree {

	// Root of Binary Tree
	Node root;
	String[] source;
	int maxDepth;
	int srcIdx;

	// Constructors
	BinaryTree(int key) {
		root = new Node(key);
		source = null;
		maxDepth = 1;
	}

	BinaryTree() {
		root = null;
		source = null;
		maxDepth = 0;
	}
}

public class TreeBottom {
	String VALUE = "value";
	String LEFT_BRANCH = "leftBranch";
	String RIGHT_BRANCH = "rightBranch";
	String DEPTH = "depth";
	List<HashMap> bottomNodes = new ArrayList<HashMap>();
	int currentDepth = 0;
	BinaryTree forest = new BinaryTree();

	public int[] treeBottom(String tree) {
		tree = tree.substring(1);
		String[] parts = tree.split("\\(");
		for (int i = 0; i < parts.length; i++) {
			parts[i] = parts[i].trim();
		}
		forest.source = parts;
		forest.srcIdx = 0;
		HashMap nMap = new HashMap();
		int val = Integer.parseInt(parts[0].trim());
		forest.srcIdx = 1;
		currentDepth = 1;
		nMap.put(VALUE, parts[0].trim());
		nMap.put(DEPTH, currentDepth);
		Node node = new Node(val);
		forest.root = node;
		// remove the (nn and final)

		if (!parts[forest.srcIdx].equals(")")) {
			HashMap x = traverseLeft(nMap, tree, 1, forest, node);
			nMap.put(LEFT_BRANCH, x);
		} else {
			forest.srcIdx++;
		}

		// System.out.println("About to call traverseRight idx="+forest.srcIdx);
		while ((forest.srcIdx < forest.source.length - 1)) {
			if (!parts[forest.srcIdx].startsWith(")")) {
				HashMap y = traverseRight(nMap, tree, 1, forest, node);
				nMap.put(RIGHT_BRANCH, y);
			} else if (parts[forest.srcIdx].startsWith("))") && parts[forest.srcIdx].length() > 2) {
				parts[forest.srcIdx] = parts[forest.srcIdx].substring(2);
			} else {
				forest.srcIdx++;
			}
		}

		assert ((forest.source.length - 1) == forest.srcIdx);
		addToTree(nMap);
		int[] result = new int[bottomNodes.size()];
		for (int i = 0; i < bottomNodes.size(); i++) {
			result[i] = (int) Integer.parseInt((String) (bottomNodes.get(i).get(VALUE)));
		}

		return result;
	}

	// (n (left) (right))
	public HashMap traverseLeft(HashMap map, String tree, int depth, BinaryTree forest, Node currNode) {
		String[] parts = forest.source;
		HashMap nMap = new HashMap();
		if (parts[forest.srcIdx].startsWith(")")) {
			forest.srcIdx++;
			return nMap;
		}
		int currentDepth = depth + 1;
		nMap.put(VALUE, parts[forest.srcIdx].trim());
		nMap.put(DEPTH, currentDepth);
		Node node = new Node(Integer.parseInt(parts[forest.srcIdx].trim()));
		currNode.left = node;
		forest.srcIdx++;
		if (forest.srcIdx < parts.length) {
			if (!parts[forest.srcIdx].equals(")")) {
				HashMap x = traverseLeft(nMap, tree, currentDepth, forest, node);
				nMap.put(LEFT_BRANCH, x);
			} else {
				forest.srcIdx++;
			}
		}
		if (forest.srcIdx < parts.length && parts[forest.srcIdx].equals(")")) {
			forest.srcIdx++;
		}
		if (forest.srcIdx < parts.length) {
			if (!parts[forest.srcIdx].startsWith(")")) {
				HashMap y = traverseRight(nMap, tree, currentDepth, forest, node);
				nMap.put(RIGHT_BRANCH, y);
			} else if (parts[forest.srcIdx].length() > 2) {
				parts[forest.srcIdx] = parts[forest.srcIdx].substring(2);
			} else {
				forest.srcIdx++;
			}
		}
		if (forest.srcIdx < parts.length && parts[forest.srcIdx].equals(")")) {
			forest.srcIdx++;
		}
		addToTree(nMap);
		map.put(LEFT_BRANCH, nMap);
		return map;
	}

	public HashMap traverseRight(HashMap map, String tree, int depth, BinaryTree forest, Node currNode) {
		String[] parts = forest.source;
		HashMap nMap = new HashMap();
		if (forest.srcIdx < parts.length && parts[forest.srcIdx].startsWith(")")) {
			if (parts[forest.srcIdx].startsWith("))")) {
				if (parts[forest.srcIdx].length() > 2) {
					parts[forest.srcIdx] = parts[forest.srcIdx].substring(2);
				} else {
					forest.srcIdx++;
				}
			} else {
				forest.srcIdx++;
			}
			return nMap;
		}
		if (forest.srcIdx < parts.length) {
			if (parts[forest.srcIdx].trim().length() == 0) {
				forest.srcIdx++;
				return nMap;
			}
		}
		int currentDepth = depth + 1;
		nMap.put(VALUE, parts[forest.srcIdx].trim());
		nMap.put(DEPTH, currentDepth);
		Node node = new Node(Integer.parseInt(parts[forest.srcIdx]));
		currNode.right = node;
		forest.srcIdx++;
		if (forest.srcIdx < parts.length) {
			if (!parts[forest.srcIdx].equals(")")) {
				HashMap x = traverseLeft(nMap, tree, currentDepth, forest, node);
				nMap.put(LEFT_BRANCH, x);
			} else {
				forest.srcIdx++;
			}
		}
		if (forest.srcIdx < parts.length) {
			if (!parts[forest.srcIdx].startsWith(")")) {
				HashMap y = traverseRight(nMap, tree, currentDepth, forest, node);
				nMap.put(RIGHT_BRANCH, y);
			} else {
				if (parts[forest.srcIdx].length() > 2) {
					parts[forest.srcIdx] = parts[forest.srcIdx].substring(2);
				} else {
					forest.srcIdx++;
				}
			}
		}
		addToTree(nMap);
		map.put(RIGHT_BRANCH, nMap);
		return map;
	}

	public void addToTree(HashMap x) {
		// System.out.println("In addToTree " + x);
		int iDepth = (Integer) x.get(DEPTH);
		if (iDepth > currentDepth) {
			bottomNodes = new ArrayList<HashMap>();
			currentDepth = iDepth;
			bottomNodes.add(x);
		} else if (iDepth == currentDepth) {
			bottomNodes.add(x);
		}
	}
}
