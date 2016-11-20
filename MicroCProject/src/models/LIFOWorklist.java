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

}