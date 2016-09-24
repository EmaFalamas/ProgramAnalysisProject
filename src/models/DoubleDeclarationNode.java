package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class DoubleDeclarationNode extends DeclarationNode {
    private DeclarationNode firstDeclarationNode;
    private DeclarationNode secondDeclarationNode;

    public DeclarationNode getFirstDeclarationNode() {
        return firstDeclarationNode;
    }

    public void setFirstDeclarationNode(DeclarationNode firstDeclarationNode) {
        this.firstDeclarationNode = firstDeclarationNode;
    }

    public DeclarationNode getSecondDeclarationNode() {
        return secondDeclarationNode;
    }

    public void setSecondDeclarationNode(DeclarationNode secondDeclarationNode) {
        this.secondDeclarationNode = secondDeclarationNode;
    }
}
