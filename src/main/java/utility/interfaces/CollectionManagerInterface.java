package utility.interfaces;

import data.Ticket;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public interface CollectionManagerInterface {

    void add(Ticket ticket);
    void clear();
    int size();
    String getInitTime();
    Ticket last();
    List<Ticket> getAllElements();
    Iterator<Ticket> iterator();
    void removeIf(Predicate<Ticket> filter);

}
