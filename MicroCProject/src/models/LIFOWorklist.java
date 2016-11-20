package models;

import java.util.Stack;
import java.util.Iterator;

public class LIFOWorklist extends Worklist {

    Stack<Tuple> worklist;

    public LIFOWorklist() {
        this.worklist = new Stack<Tuple>();
    }

    @Override
    public void add(Tuple t) {
        worklist.push(t);
    }

    @Override
    public void remove() {
        worklist.pop();
    }

    @Override
    public boolean contains(Tuple t) {
        return worklist.contains(t);
    }

    @Override
    public Iterator<Tuple> iterator() {
        return worklist.iterator();
    }

}