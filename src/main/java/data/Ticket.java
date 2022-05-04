package data;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Admin class for ticket
 */
public class Ticket implements Comparable<Ticket>, Serializable {
    static long newId = 0;
    // уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; // Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; // Поле не может быть null
    private final LocalDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться
    // автоматически
    private final double price; // Значение поля должно быть больше 0
    private final String comment; // Длина строки не должна быть больше 356, Поле может быть null
    private final TicketType type; // Поле не может быть null
    private final Person person; // Поле не может быть null
    private long id; // Значение поля должно быть больше 0, Значение этого поля должно быть

    /**
     * Class constructor
     *
     * @param name        - name of ticket
     * @param coordinates - coordinates ticket
     * @param price       - price of ticket
     * @param comment     - comment
     * @param type        - type of ticket
     * @param person      - person who has this ticket
     */
    public Ticket(String name, Coordinates coordinates,
                  double price, String comment, TicketType type, Person person) {
        newId += 1;
        this.id = newId;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.price = price;
        this.comment = comment;
        this.type = type;
        this.person = person;
    }

    /**
     * Converts an object to a string representation.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", comment='" + comment + '\'' +
                ", type=" + type +
                ", person=" + person +
                '}';
    }

    /**
     * @param type - what we compare it with
     * @return boolean. True if object type equals input type.
     */
    public boolean equalsByType(TicketType type) {
        return type.equals(this.type);
    }

    /**
     * Compares two objects across all fields
     *
     * @param o - the object we want to compare with
     * @return boolean. True if objects are equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && Double.compare(ticket.price, price) == 0 && Objects.equals(name, ticket.name) && Objects.equals(coordinates, ticket.coordinates) && Objects.equals(creationDate, ticket.creationDate) && Objects.equals(comment, ticket.comment) && type == ticket.type && Objects.equals(person, ticket.person);
    }

    /**
     * Generate a hashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, price, comment, type, person);
    }

    /**
     * Compares tickets with each other.
     *
     * @param o - the object with which the comparison takes place
     * @return int > 0 if this > o. int = 0, if this = o. int < 0, if this < o.
     */
    @Override
    public int compareTo(Ticket o) {
        int res = this.name.compareTo(o.name);
        if (res == 0) {
            res = this.coordinates.compareTo(o.coordinates);
            if (res == 0) {
                res = this.creationDate.compareTo(o.creationDate);
                if (res == 0) {
                    res = this.comment.compareTo(o.comment);
                    if (res == 0) {
                        res = this.person.compareTo(o.person);
                    }
                }
            }
        }
        return res;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getPrice() {
        return price;
    }

    public TicketType getType() {
        return type;
    }

    public Person getPerson() {
        return person;
    }

    public String getComment() {
        return comment;
    }

    public long getId() {
        return id;
    }

    public void updateId() {
        try {
            this.id = SecureRandom.getInstanceStrong().nextLong(); // Уникальный != случайный
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("No such algorithm");
        }
    }

    public void setId(long newId) {
        id = newId;
    }
}