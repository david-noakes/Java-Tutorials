package org.erehwon.shadowlands.GridsAndMazes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FlameCrestAttack {

	public class Cell implements Comparable{
	    public Cell() {
	    	visitors=new HashSet<>();
	    	attacks=new HashSet<>();
	    }
	    public Cell withEnemy(boolean enemy){
	        this.enemy=enemy;
	        return this;
	    }
	    public Cell withFriend(boolean friend, boolean archer){
	        this.friend=friend;
	        this.archer=archer;
	        return this;
	    }
	    Cell above;
	    Cell below;
	    Cell left;
	    Cell right;
	    Set<Cell> visitors;
	    Set<Cell> attacks;
	    boolean enemy;
	    boolean friend;
	    boolean archer;
	    boolean visited;
	    String label;
	    @Override
	    public int compareTo(Object o){
	        Cell other=(Cell)o;
	        int thisOne=this.visitors.size();
	        int thatOne=other.visitors.size();
	        if(thisOne==thatOne) return 0;
	        if(thisOne>thatOne) return -1;
	        return 1;
	    }
	    @Override
	    public String toString(){
	        return label;
	    }
	    @Override
	    public Cell clone() {
	    	Cell c = new Cell().withEnemy(enemy).withFriend(friend, archer);
	    	c.visited = visited;
	    	return c;
	    }
	    void setDestinations(){
	        findDestinations(this,this,(archer)?4:7,0);
	    }
	}
	Map<Cell,Set<Cell>> archerHits;
	Map<Cell,Set<Cell>> knightHits;
	Cell[][] nodeGrid;

	int swordsAndArrows(String[] grid) {
	    nodeGrid=new Cell[grid.length][grid[0].length()];
	    List<Cell> enemies=new ArrayList<>();
	    List<Cell> friends=new ArrayList<>();
	    int row=0;
	    for(String s:grid){
	        int col=0;
	        for(char c:s.toCharArray()){
	            if(c=='A'){
	                nodeGrid[row][col]=new Cell().withFriend(true,true);
	                friends.add(nodeGrid[row][col]);
	            }else if(c=='E'){
	                nodeGrid[row][col]=new Cell().withEnemy(true);
	                enemies.add(nodeGrid[row][col]);
	            }else if(c=='K'){
	                nodeGrid[row][col]=new Cell().withFriend(true,false);
	                friends.add(nodeGrid[row][col]);
	            }else{
	                nodeGrid[row][col]=new Cell().withEnemy(false);
	            }
	            nodeGrid[row][col].label=row+","+col+":'"+c+"'";
	            col++;
	        }
	        row++;
	    }
	    for(int i=0;i<nodeGrid.length;i++){
	        for(int j=0;j<nodeGrid[0].length;j++){
	            if(i!=0) nodeGrid[i-1][j].below=nodeGrid[i][j];
	            if(i!=nodeGrid.length-1) nodeGrid[i+1][j].above=nodeGrid[i][j];
	            if(j!=0) nodeGrid[i][j-1].left=nodeGrid[i][j];
	            if(j!=nodeGrid[0].length-1) nodeGrid[i][j+1].right=nodeGrid[i][j];
	        }
	    }
	    archerHits=new HashMap<>();
	    knightHits=new HashMap<>();
	    for(Cell g:friends){
	        g.setDestinations();
	    }
	    for(Cell g:enemies){
	        updateHits(g); 
	    }
	    return exploreBattle(enemies,0,new HashSet<Cell>());
	}
	int exploreBattle(List<Cell> enemies, int enemy, Set<Cell> attacker){
	    if(enemy>=enemies.size()){ 
	        return attacker.size();
	    }
	    Cell current=enemies.get(enemy);
	    int answer=0;
	    for(Cell n:current.attacks){
	        if(n.visited) continue;
	        n.visited=true;
	        for(Cell v:n.visitors){
	            if(attacker.contains(v) || v.archer && !archerHits.containsKey(n)
	                    || v.archer && !archerHits.get(n).contains(current)
	                    || !v.archer && !knightHits.containsKey(n)
	                    || !v.archer && !knightHits.get(n).contains(current)){ 
	                continue;
	            }
	            attacker.add(v);
	            answer=Math.max(exploreBattle(enemies,enemy+1,attacker),answer);
	            attacker.remove(v);
	            if(answer==enemies.size()) return answer;
	        }
	        n.visited=false;
	    }
	    answer=Math.max(exploreBattle(enemies,enemy+1,attacker),answer);
	    return answer;
	}
	void updateHits(Cell g){
	    HashSet<Cell> toAdd=new HashSet<>();
	    if(g.above!=null) toAdd.add(g.above);
	    if(g.left!=null) toAdd.add(g.left);
	    if(g.right!=null) toAdd.add(g.right);
	    if(g.below!=null) toAdd.add(g.below);
	    for(Cell n:toAdd){
	        for(Cell v:n.visitors){
	            if(!v.archer){
	                g.attacks.add(n);
	                if(!knightHits.containsKey(n)) knightHits.put(n,new HashSet<Cell>());
	                knightHits.get(n).add(g);
	                break;
	            }
	        }
	    }
	    toAdd.clear();
	    if(g.above!=null){
	        if(g.above.left!=null&&g.above.left.left!=null) toAdd.add(g.above.left.left);
	        if(g.above.right!=null&&g.above.right.right!=null) toAdd.add(g.above.right.right);
	        if(g.above.above!=null){
	            if(g.above.above.left!=null) toAdd.add(g.above.above.left);
	            if(g.above.above.right!=null) toAdd.add(g.above.above.right);
	            if(g.above.above.above!=null) toAdd.add(g.above.above.above);
	        }
	    }
	    if(g.below!=null){
	        if(g.below.left!=null&&g.below.left.left!=null) toAdd.add(g.below.left.left);
	        if(g.below.right!=null&&g.below.right.right!=null) toAdd.add(g.below.right.right);
	        if(g.below.below!=null){
	            if(g.below.below.left!=null) toAdd.add(g.below.below.left);
	            if(g.below.below.right!=null) toAdd.add(g.below.below.right);
	            if(g.below.below.below!=null) toAdd.add(g.below.below.below);
	        }
	    }
	    if(g.left!=null&&g.left.left!=null&&g.left.left.left!=null) toAdd.add(g.left.left.left);
	    if(g.right!=null&&g.right.right!=null&&g.right.right.right!=null) toAdd.add(g.right.right.right);
	    for(Cell n:toAdd){
	        for(Cell v:n.visitors){
	            if(v.archer){
	                g.attacks.add(n);
	                if(!archerHits.containsKey(n)) archerHits.put(n,new HashSet<Cell>());
	                archerHits.get(n).add(g);
	                break;
	            }
	        }
	    }       
	}
	void findDestinations(Cell g, Cell v, int distance, int travelled){
	    if(g==null || g.enemy) return;
	    g.visitors.add(v);
	    if(distance==travelled) return;
	    findDestinations(g.above,v,distance,travelled+1);
	    findDestinations(g.below,v,distance,travelled+1);
	    findDestinations(g.left,v,distance,travelled+1);
	    findDestinations(g.right,v,distance,travelled+1);
	}
}
