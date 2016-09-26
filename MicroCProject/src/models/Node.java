package models;

import java.util.ArrayList;
import java.lang.Cloneable;

/**
 * Created by Ema on 19/9/2016.
 */
public abstract class Node implements Cloneable {
    private String label;
    private ArrayList<Node> children;
	private Node parent;
    
    public Node(Node parent){
    	children = new ArrayList<Node>();
		this.parent = parent;
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

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
}
