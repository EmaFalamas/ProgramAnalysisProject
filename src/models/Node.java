package models;

import java.util.ArrayList;

/**
 * Created by Ema on 19/9/2016.
 */
public abstract class Node {
    private String label;
    private ArrayList<Node> children;
    
    public Node(){
    	children = new ArrayList<Node>();
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
}
