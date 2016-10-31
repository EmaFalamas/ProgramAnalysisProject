package models;

public class Tuple {

    private String leftString;
    private String rightString;

    public Tuple(String ls, String rs) {
        this.leftString = ls;
        this.rightString = rs;
    }

    public String getLeftString() {
        return leftString;
    }

    public void setLeftString(String leftString) {
        this.leftString = leftString;
    }

    public String getRightString() {
        return rightString;
    }

    public void setRightString(String rightString) {
        this.rightString = rightString;
    }

    @Override
    public String toString() {
        return "(" + leftString + ", " + rightString + ")";
    }
}