package models;

import java.util.Iterator;
import java.lang.Cloneable;

public abstract class Worklist implements Cloneable {

    public enum WorklistType { FIFO, LIFO }

    private Iterator<Tuple<String, String>> iterator;

    public Worklist() {

    }

    public void add(Tuple<String, String> t) {}

    public void remove() {}

    public boolean contains(Tuple<String, String> t) {return false;}

    public Tuple<String, String> getLast() { return null; }

    public Iterator<Tuple<String, String>> iterator() {return null;}

    public void printWorklist()
    {

    }

}