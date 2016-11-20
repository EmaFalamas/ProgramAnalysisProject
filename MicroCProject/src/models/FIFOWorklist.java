package models;

import java.util.LinkedList;
import java.util.Iterator;

public class FIFOWorklist extends Worklist {

    private LinkedList<Tuple<String, String>> worklist;

    public FIFOWorklist() {
        this.worklist = new LinkedList<Tuple<String, String>>();
    }

    @Override
    public void add(Tuple<String, String> t) {
        this.worklist.add(t);
    }

    @Override
    public void remove() {
        this.worklist.remove();
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