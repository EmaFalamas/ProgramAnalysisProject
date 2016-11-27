package models;

import java.util.Stack;
import java.util.Iterator;

public class LIFOWorklist extends Worklist {

    Stack<Tuple<String, String>> worklist;

    public LIFOWorklist() {
        this.worklist = new Stack<Tuple<String, String>>();
    }

    @Override
    public void add(Tuple<String, String> t) {
        worklist.push(t);
    }

    @Override
    public void remove() {
        worklist.pop();
    }

    @Override
    public boolean contains(Tuple<String, String> t) {
        return worklist.contains(t);
    }

    @Override
    public Iterator<Tuple<String, String>> iterator() {
        return worklist.iterator();
    }

    @Override
    public Tuple<String, String> getLast()
    {
        return worklist.lastElement();
    }

    @Override
    public void printWorklist()
    {
        Iterator iterator = worklist.iterator();
        while (iterator.hasNext()) {
            Tuple<String, String> object = (Tuple<String, String>)iterator.next();
            System.out.println("Left: " + object.getLeft() + " right: " + object.getRight());
        }
    }
}