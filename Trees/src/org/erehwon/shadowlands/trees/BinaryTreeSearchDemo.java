package org.erehwon.shadowlands.trees;

import org.erehwon.shadowlands.trees.GsonBinaryTree.Tree;

import com.google.gson.JsonElement;

public class BinaryTreeSearchDemo {

	public static String tree1 = "{ " +
			"    \"value\": 5, " +
			"    \"left\": { " +
			"        \"value\": 3, " +
			"        \"left\": { " +
			"            \"value\": 2, " +
			"            \"left\": null, " +
			"            \"right\": null " +
			"        }, " +
			"        \"right\": null " +
			"    }, " +
			"    \"right\": { " +
			"        \"value\": 8, " +
			"        \"left\": { " +
			"            \"value\": 6, " +
			"            \"left\": null, " +
			"            \"right\": null " +
			"        }, " +
			"        \"right\": { " +
			"            \"value\": 9, " +
			"            \"left\": null, " +
			"            \"right\": null " +
			"        } " +
			"    } " +
			"} " ;
	public static boolean exp1 = true;
	public static String tree2 = "{ " +
			"    \"value\": 9, " +
			"    \"left\": { " +
			"        \"value\": 8, " +
			"        \"left\": { " +
			"            \"value\": 2, " +
			"            \"left\": null, " +
			"            \"right\": null " +
			"        }, " +
			"        \"right\": { " +
			"            \"value\": 6, " +
			"            \"left\": null, " +
			"            \"right\": null " +
			"        } " +
			"    }, " +
			"    \"right\": { " +
			"        \"value\": 5, " +
			"        \"left\": null, " +
			"        \"right\": null " +
			"    } " +
			"} " ;
	public static boolean exp2 = false;
	public static String tree3 = "{ " +
			"    \"value\": 1, " +
			"    \"left\": null, " +
			"    \"right\": { " +
			"        \"value\": 2, " +
			"        \"left\": null, " +
			"        \"right\": { " +
			"            \"value\": 3, " +
			"            \"left\": null, " +
			"            \"right\": { " +
			"                \"value\": 4, " +
			"                \"left\": null, " +
			"                \"right\": null " +
			"            } " +
			"        } " +
			"    } " +
			"} " ;
	public static boolean exp3 = true;
	public static String tree4 = "{ " +
			"    \"value\": 1, " +
			"    \"left\": null, " +
			"    \"right\": { " +
			"        \"value\": 0, " +
			"        \"left\": null, " +
			"        \"right\": null " +
			"    } " +
			"} " ;
	public static boolean exp4 = false;
	public static String tree5 = " { " +
			"    \"value\": 79, " +
			"    \"left\": { " +
			"        \"value\": -56, " +
			"        \"left\": null, " +
			"        \"right\": null " +
			"    }, " +
			"    \"right\": { " +
			"        \"value\": 0, " +
			"        \"left\": null, " +
			"        \"right\": { " +
			"            \"value\": -47, " +
			"            \"left\": { " +
			"                \"value\": -91, " +
			"                \"left\": null, " +
			"                \"right\": { " +
			"                    \"value\": -92, " +
			"                    \"left\": null, " +
			"                    \"right\": null " +
			"                } " +
			"            }, " +
			"            \"right\": { " +
			"                \"value\": -48, " +
			"                \"left\": { " +
			"                    \"value\": 56, " +
			"                    \"left\": null, " +
			"                    \"right\": null " +
			"                }, " +
			"                \"right\": { " +
			"                    \"value\": 16, " +
			"                    \"left\": null, " +
			"                    \"right\": null " +
			"                } " +
			"            } " +
			"        } " +
			"    } " +
			"} " ;
	public static boolean exp5 = false;
	public static String tree6 = "{ " +
			"    \"value\": 27, " +
			"    \"left\": { " +
			"        \"value\": 24, " +
			"        \"left\": { " +
			"            \"value\": -21, " +
			"            \"left\": { " +
			"                \"value\": -29, " +
			"                \"left\": { " +
			"                    \"value\": -86, " +
			"                    \"left\": { " +
			"                        \"value\": -98, " +
			"                        \"left\": { " +
			"                            \"value\": -99, " +
			"                            \"left\": null, " +
			"                            \"right\": null " +
			"                        }, " +
			"                        \"right\": { " +
			"                            \"value\": -96, " +
			"                            \"left\": null, " +
			"                            \"right\": { " +
			"                                \"value\": -89, " +
			"                                \"left\": null, " +
			"                                \"right\": null " +
			"                            } " +
			"                        } " +
			"                    }, " +
			"                    \"right\": { " +
			"                        \"value\": -98, " +
			"                        \"left\": null, " +
			"                        \"right\": { " +
			"                            \"value\": -42, " +
			"                            \"left\": { " +
			"                                \"value\": -84, " +
			"                                \"left\": { " +
			"                                    \"value\": -85, " +
			"                                    \"left\": null, " +
			"                                    \"right\": null " +
			"                                }, " +
			"                                \"right\": { " +
			"                                    \"value\": -72, " +
			"                                    \"left\": { " +
			"                                        \"value\": -75, " +
			"                                        \"left\": { " +
			"                                            \"value\": -83, " +
			"                                            \"left\": null, " +
			"                                            \"right\": { " +
			"                                                \"value\": -77, " +
			"                                                \"left\": null, " +
			"                                                \"right\": null " +
			"                                            } " +
			"                                        }, " +
			"                                        \"right\": null " +
			"                                    }, " +
			"                                    \"right\": { " +
			"                                        \"value\": -79, " +
			"                                        \"left\": null, " +
			"                                        \"right\": { " +
			"                                            \"value\": -53, " +
			"                                            \"left\": { " +
			"                                                \"value\": -55, " +
			"                                                \"left\": { " +
			"                                                    \"value\": -67, " +
			"                                                    \"left\": null, " +
			"                                                    \"right\": { " +
			"                                                        \"value\": -66, " +
			"                                                        \"left\": null, " +
			"                                                        \"right\": { " +
			"                                                            \"value\": -58, " +
			"                                                            \"left\": null, " +
			"                                                            \"right\": null " +
			"                                                        } " +
			"                                                    } " +
			"                                                }, " +
			"                                                \"right\": null " +
			"                                            }, " +
			"                                            \"right\": { " +
			"                                                \"value\": -62, " +
			"                                                \"left\": null, " +
			"                                                \"right\": { " +
			"                                                    \"value\": -46, " +
			"                                                    \"left\": { " +
			"                                                        \"value\": -48, " +
			"                                                        \"left\": null, " +
			"                                                        \"right\": null " +
			"                                                    }, " +
			"                                                    \"right\": null " +
			"                                                } " +
			"                                            } " +
			"                                        } " +
			"                                    } " +
			"                                } " +
			"                            }, " +
			"                            \"right\": { " +
			"                                \"value\": -33, " +
			"                                \"left\": { " +
			"                                    \"value\": -39, " +
			"                                    \"left\": null, " +
			"                                    \"right\": null " +
			"                                }, " +
			"                                \"right\": null " +
			"                            } " +
			"                        } " +
			"                    } " +
			"                }, " +
			"                \"right\": { " +
			"                    \"value\": -65, " +
			"                    \"left\": null, " +
			"                    \"right\": { " +
			"                        \"value\": -25, " +
			"                        \"left\": null, " +
			"                        \"right\": { " +
			"                            \"value\": -22, " +
			"                            \"left\": { " +
			"                                \"value\": -24, " +
			"                                \"left\": null, " +
			"                                \"right\": null " +
			"                            }, " +
			"                            \"right\": null " +
			"                        } " +
			"                    } " +
			"                } " +
			"            }, " +
			"            \"right\": { " +
			"                \"value\": -14, " +
			"                \"left\": { " +
			"                    \"value\": -20, " +
			"                    \"left\": null, " +
			"                    \"right\": null " +
			"                }, " +
			"                \"right\": { " +
			"                    \"value\": -8, " +
			"                    \"left\": { " +
			"                        \"value\": -12, " +
			"                        \"left\": null, " +
			"                        \"right\": { " +
			"                            \"value\": -10, " +
			"                            \"left\": null, " +
			"                            \"right\": null " +
			"                        } " +
			"                    }, " +
			"                    \"right\": { " +
			"                        \"value\": 1, " +
			"                        \"left\": { " +
			"                            \"value\": -5, " +
			"                            \"left\": null, " +
			"                            \"right\": { " +
			"                                \"value\": -4, " +
			"                                \"left\": null, " +
			"                                \"right\": { " +
			"                                    \"value\": -2, " +
			"                                    \"left\": null, " +
			"                                    \"right\": null " +
			"                                } " +
			"                            } " +
			"                        }, " +
			"                        \"right\": { " +
			"                            \"value\": 8, " +
			"                            \"left\": { " +
			"                                \"value\": 6, " +
			"                                \"left\": { " +
			"                                    \"value\": 3, " +
			"                                    \"left\": null, " +
			"                                    \"right\": null " +
			"                                }, " +
			"                                \"right\": { " +
			"                                    \"value\": 7, " +
			"                                    \"left\": null, " +
			"                                    \"right\": null " +
			"                                } " +
			"                            }, " +
			"                            \"right\": { " +
			"                                \"value\": 17, " +
			"                                \"left\": { " +
			"                                    \"value\": 14, " +
			"                                    \"left\": null, " +
			"                                    \"right\": null " +
			"                                }, " +
			"                                \"right\": null " +
			"                            } " +
			"                        } " +
			"                    } " +
			"                } " +
			"            } " +
			"        }, " +
			"        \"right\": { " +
			"            \"value\": 26, " +
			"            \"left\": { " +
			"                \"value\": 25, " +
			"                \"left\": null, " +
			"                \"right\": null " +
			"            }, " +
			"            \"right\": null " +
			"        } " +
			"    }, " +
			"    \"right\": { " +
			"        \"value\": 42, " +
			"        \"left\": { " +
			"            \"value\": 35, " +
			"            \"left\": { " +
			"                \"value\": 32, " +
			"                \"left\": { " +
			"                    \"value\": 31, " +
			"                    \"left\": null, " +
			"                    \"right\": null " +
			"                }, " +
			"                \"right\": { " +
			"                    \"value\": 33, " +
			"                    \"left\": null, " +
			"                    \"right\": null " +
			"                } " +
			"            }, " +
			"            \"right\": { " +
			"                \"value\": 40, " +
			"                \"left\": { " +
			"                    \"value\": 38, " +
			"                    \"left\": { " +
			"                        \"value\": 37, " +
			"                        \"left\": { " +
			"                            \"value\": 36, " +
			"                            \"left\": null, " +
			"                            \"right\": null " +
			"                        }, " +
			"                        \"right\": null " +
			"                    }, " +
			"                    \"right\": null " +
			"                }, " +
			"                \"right\": null " +
			"            } " +
			"        }, " +
			"        \"right\": { " +
			"            \"value\": 57, " +
			"            \"left\": { " +
			"                \"value\": 54, " +
			"                \"left\": { " +
			"                    \"value\": 50, " +
			"                    \"left\": { " +
			"                        \"value\": 49, " +
			"                        \"left\": null, " +
			"                        \"right\": null " +
			"                    }, " +
			"                    \"right\": null " +
			"                }, " +
			"                \"right\": null " +
			"            }, " +
			"            \"right\": { " +
			"                \"value\": 69, " +
			"                \"left\": { " +
			"                    \"value\": 67, " +
			"                    \"left\": { " +
			"                        \"value\": 64, " +
			"                        \"left\": null, " +
			"                        \"right\": null " +
			"                    }, " +
			"                    \"right\": null " +
			"                }, " +
			"                \"right\": { " +
			"                    \"value\": 88, " +
			"                    \"left\": { " +
			"                        \"value\": 82, " +
			"                        \"left\": { " +
			"                            \"value\": 75, " +
			"                            \"left\": { " +
			"                                \"value\": 71, " +
			"                                \"left\": null, " +
			"                                \"right\": null " +
			"                            }, " +
			"                            \"right\": { " +
			"                                \"value\": 76, " +
			"                                \"left\": null, " +
			"                                \"right\": null " +
			"                            } " +
			"                        }, " +
			"                        \"right\": { " +
			"                            \"value\": 86, " +
			"                            \"left\": { " +
			"                                \"value\": 83, " +
			"                                \"left\": null, " +
			"                                \"right\": null " +
			"                            }, " +
			"                            \"right\": { " +
			"                                \"value\": 87, " +
			"                                \"left\": null, " +
			"                                \"right\": null " +
			"                            } " +
			"                        } " +
			"                    }, " +
			"                    \"right\": { " +
			"                        \"value\": 99, " +
			"                        \"left\": { " +
			"                            \"value\": 91, " +
			"                            \"left\": null, " +
			"                            \"right\": null " +
			"                        }, " +
			"                        \"right\": null " +
			"                    } " +
			"                } " +
			"            } " +
			"        } " +
			"    } " +
			"} " ;
	public static boolean exp6 = false;
	
	public static void main(String[] args) {
		String[] tests = {tree1, tree2, tree3, tree4, tree5, tree6};
		boolean[] exps = {exp1, exp2, exp3, exp4, exp5, exp6};
		for (int i=0;i<exps.length;i++) 
			runTest(tests[i], exps[i], i+1);
	}
	
 	static void runTest(String sJ, boolean exp, int testNum) {
		GsonBinaryTree gb = new GsonBinaryTree();
		JsonElement jTree = gb.constructJsonTree(sJ);
		boolean res = gb.isBinarySearchTree(jTree);
		Tree tree = gb.constructTree(sJ);
		boolean res1 = gb.isBinarySearchTree(tree);
		if (res==exp && res==res1) {
			System.out.println("Test" + testNum + ": Equal:"+res);
		} else {
			System.out.println("Test" + testNum + ": Expected:" + exp + ", Results:" + res+", "+res1);
		}
 	}
}
