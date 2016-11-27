package models;

import java.util.ListIterator;
import java.lang.Cloneable;

public class Worklist implements Cloneable {

    public enum WorklistType { FIFO, LIFO }

    private ListIterator<Tuple<String, String>> iterator;

    public Worklist() {

    }


    public void add(Tuple<String, String> t) {}

    public void remove() {}

    public boolean contains(Tuple<String, String> t) {return false;}

    public Tuple<String, String> getLast() { return null; }

    public ListIterator<Tuple<String, String>> iterator() {return null;}


}