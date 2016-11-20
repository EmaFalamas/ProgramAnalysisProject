package models;

import java.util.Iterator;
import java.lang.Cloneable;

public abstract class Worklist implements Cloneable {

    public enum WorklistType { FIFO, LIFO }

    private Iterator<Tuple> iterator;

    public Worklist() {

    }

    public void add(Tuple t) {}

    public void remove() {}

    public boolean contains(Tuple t) {return false;}

    public Iterator<Tuple> iterator() {return null;}

}