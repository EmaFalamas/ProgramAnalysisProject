package models;

public class Tuple<S,T> {

    private S left;
    private T right;

    public Tuple(S ls, T rs) {
        this.left = ls;
        this.right = rs;
    }

    public S getLeft() {
        return left;
    }

    public void setLeft(S _left) {
        this.left = _left;
    }

    public T getRight() {
        return right;
    }

    public void setRight(T _right) {
        this.right = _right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + ", " + right.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tuple) {
            Tuple t = (Tuple) o;
            return this.left.equals(t.getLeft()) && (this.right.equals(t.getRight())
                    || ((this.right instanceof String) && this.right.equals("?") || t.getRight().equals("?")));
        }
        return false;
    }
}