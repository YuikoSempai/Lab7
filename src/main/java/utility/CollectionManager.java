/**
 * Class to work with collection
 */
package utility;

import data.Ticket;
import utility.interfaces.CollectionManagerInterface;

import java.util.*;
import java.util.function.Predicate;

public class CollectionManager implements CollectionManagerInterface {
    static public String initTime;
    private final TreeSet<Ticket> collection;
    private String pathForFile = "";
    public CollectionManager() {
        collection = new TreeSet<>();
        initTime = new Date().toString();
    }

    public void add(Ticket ticket) {
        collection.add(ticket);
    }

    /**
     * Clear collection.
     */
    public void clear() {
        collection.clear();
    }
    /**
     * return size of collection
     * @return int
     */
    public int size() {
        return collection.size();
    }

    public void remove(Ticket obj){
        collection.remove(obj);
    }

    /**
     * Return initialization time of collection.
     * @return String
     */
    public String getInitTime() {
        return initTime;
    }

    /**
     * Return last element of collection.
     * @return Ticket
     */
    public Ticket last(){
        return collection.last();
    }

    /**
     * Return all elements of collection into List.
     * @return List<Ticket>
     */
    public List<Ticket> getAllElements(){
        return new ArrayList<>(collection);
    }

    /**
     * Return iterator of collection.
     * @return Iterator<Ticket>
     */
    public Iterator<Ticket> iterator(){
        return collection.iterator();
    }

    /**
     * Remove element from collection if it is matches the filter.
     */
    public void removeIf(Predicate<Ticket> filter) {
        Objects.requireNonNull(filter);
        collection.removeIf(filter);
    }

    public void setPathForFile(String path){
        pathForFile = path;
    }
    public String getPathForFile(){
        return pathForFile;
    }
}