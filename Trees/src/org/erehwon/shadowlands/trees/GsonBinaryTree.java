package org.erehwon.shadowlands.trees;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonBinaryTree {
	//
	// Definition for binary tree:
	 class Tree<T> {
	   Tree(int x) {
	     value = x;
	   }
	   int value;
	   Tree<T> left;
	   Tree<T> right;
	 }
	boolean isBinarySearchTree(Tree<Integer> tree) {
			return dig(tree, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	boolean dig(Tree<Integer> tree, int l, int h) {
			if (tree==null)
				return true;
			return tree.value > l && tree.value < h &&
				dig(tree.left, l, tree.value) &&
				dig(tree.right, tree.value, h);
	    
	}

 	boolean isBinarySearchTree(JsonElement jsonTree) {
		
 		return dig(jsonTree, Integer.MIN_VALUE, Integer.MAX_VALUE);
		
 	}
	
 	boolean dig(JsonElement jsonTree, int l, int h) {
 		if (jsonTree==null || jsonTree.isJsonNull())
 			return true;
 		int iTop;
 		JsonObject jObj = jsonTree.getAsJsonObject();
 		JsonElement jLeft = jObj.get("left");
 		JsonElement jRight = jObj.get("right");
 		JsonElement jVal = jObj.get("value");
		
 		iTop = jVal.getAsInt();
 		return  iTop > l && iTop < h &&
 				dig(jLeft, l, iTop) &&
 				dig(jRight, iTop, h);
		
 	}

	Tree constructTree(String json) {
 		Gson gson = new Gson();
 		Tree<Integer> tree = gson.fromJson(json, Tree.class);
 		return tree;
 	}
 	JsonElement constructJsonTree(String json) {
 		JsonParser jsonParser = new JsonParser();  // not required if using Gson to objects
 		JsonElement jsonTree = jsonParser.parse(json);
 		return jsonTree;
 	}

}
