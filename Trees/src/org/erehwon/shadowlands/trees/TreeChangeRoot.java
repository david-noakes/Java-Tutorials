package org.erehwon.shadowlands.trees;

public class TreeChangeRoot {

	public int[] changeRoot(int[] parent, int newRoot) {
		int[] results = new int[parent.length];
		int oldRoot = -1;
		for (int i = 0; i < parent.length; i++) {
			results[i] = -1;
			if (parent[i] == i) {
				oldRoot = i;
			}
		}
		
		results[newRoot] = newRoot; 
		changeRoot(parent, newRoot, parent[newRoot], results);
		
		for (int i = 0; i < parent.length; i++) {
			if (results[i] == -1) {
				results[i] = parent[i];
			}
		}
		
		return results;	
	}
	
	void changeRoot(int[] parent, int newParent, int currNode, int[] results) {
		results[currNode] = newParent;
		if (parent[currNode] == currNode) {
			// reached the old top
			return;
		}
		changeRoot(parent, currNode, parent[currNode], results);
	}
}
