package data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * class for person who has ticketed.
 */
public class Person implements Comparable<Person>, Serializable {

    private final LocalDateTime birthday; // Поле не может быть null
    private final Float height; // Поле не может быть null, Значение поля должно быть больше 0
    private final Integer weight; // Поле не может быть null, Значение поля должно быть больше 0
    private final String passportID; // Поле не может быть null

    /**
     *
     * @param birthday - person birthday
     * @param height - person height
     * @param weight - person weight
     * @param passportID - person passport id
     */
    public Person(LocalDateTime birthday, Float height, Integer weight, String passportID){
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.passportID = passportID;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public Float getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getPassportID() {
        return passportID;
    }

    @Override
    public String toString() {
        return "Person{" +
                "birthday=" + birthday +
                ", height=" + height +
                ", weight=" + weight +
                ", passportID='" + passportID + '\'' +
                '}';
    }

    /**
     * Compares person with each other.
     * @param o - the object with which the comparison takes place
     * @return int>0, if this>o. int=0, if this=o. int<0, if this<0.
     */
    @Override
    public int compareTo(Person o) {
        int res = this.birthday.compareTo(o.birthday);
        if(res==0){
            res = this.height.compareTo(o.height);
            if (res == 0) {
                res = this.weight.compareTo(o.weight);
                if(res==0){
                    res = this.passportID.compareTo(o.passportID);
                }
            }
        }
        return res;
    }
}