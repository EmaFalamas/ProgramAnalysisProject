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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tuple) {
            Tuple t = (Tuple) o;
            return this.leftString.equals(t.getLeftString()) && (this.rightString.equals(t.getRightString())
                    || this.rightString.equals("?") || t.getRightString().equals("?"));
        }
        return false;
    }
}