package data;

import java.io.Serializable;

/**
 * Class for ticket coordinates
 */
public class Coordinates implements Comparable<Coordinates>, Serializable {
    private static final long serialVersionUID = 1L;


    private final Integer x; // Значение поля должно быть больше -960, Поле не может быть null
    private final Float y; // Поле не может быть null


    /**
     * @param x - x coordinate
     * @param y - y coordinate
     */
    public Coordinates(Integer x, Float y) {
        this.x = x;
        this.y = y;
    }

    public Float getY() {
        return y;
    }

    public Integer getX() {
        return x;
    }

    /**
     * Compares coordinates with each other.
     *
     * @param o - the object with which the comparison takes place
     * @return int > 0 if this > o. int = 0, if this = o. int < 0, if this < o.
     */
    @Override
    public int compareTo(Coordinates o) {
        double thisLength = Math.hypot(x, y);
        double otherLength = Math.hypot(o.x, o.y);
        return (int) (thisLength - otherLength);
    }

    @Override
    public String toString() {
        return "Coordinates{" + "x=" + x + ", y=" + y + '}';
    }
}