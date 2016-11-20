package models;

import java.util.LinkedList;
import java.util.Iterator;

public class FIFOWorklist extends Worklist {

    private LinkedList<Tuple> worklist;

    public FIFOWorklist() {
        this.worklist = new LinkedList<Tuple>();
    }

    @Override
    public void add(Tuple t) {
        this.worklist.add(t);
    }

    @Override
    public void remove() {
        this.worklist.remove();
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