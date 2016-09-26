package models;

/**
 * Created by Ema on 24/9/2016.
 */
public class ArrayNode extends ExpressionNode {
    private String name;
    private ExpressionNode capacity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExpressionNode getCapacity() {
        return capacity;
    }

    public void setCapacity(ExpressionNode capacity) {
        this.capacity = capacity;
    }
}
