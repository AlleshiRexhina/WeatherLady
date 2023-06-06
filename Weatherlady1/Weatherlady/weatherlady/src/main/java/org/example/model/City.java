package org.example.model;

import javax.persistence.*;


@Entity
@Table(name="cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name="location")
    String location;

    public City(){};

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return
                  id + " : " +
                location + '\'' ;
    }
}
