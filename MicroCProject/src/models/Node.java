package models;

import java.util.ArrayList;
import java.lang.Cloneable;

/**
 * Created by Ema on 19/9/2016.
 */
public abstract class Node implements Cloneable {
    private String label;
	private boolean isBlock;
	private boolean visited;
    private ArrayList<Node> children;
	private ArrayList<Block> blocks;
	private Node parent;
    
    public Node(Node parent){
    	children = new ArrayList<Node>();
		blocks = new ArrayList<Block>();
		this.parent = parent;
		this.isBlock = false;
    }

	public Object clone() {
		try{
		return super.clone();
		} catch (CloneNotSupportedException exception) {}
		return null;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}
	
	public void addChild(Node child){
		children.add(child);
	}

	public ArrayList<Block> getBlocks() { return blocks; }

	public void addBlock(Block b) { blocks.add(b); }

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public boolean isBlock() { return isBlock; }

	public void isBlock(boolean block) { isBlock = block; }

	public boolean isVisited() { return visited; }

	public void isVisited(boolean visited) { this.visited = visited; }

	public void removeChild(Node node) {
		for (Node n : children) {
			if (n.equals(node)) {
				children.remove(node);
				break;
			}
		}
	}
}
