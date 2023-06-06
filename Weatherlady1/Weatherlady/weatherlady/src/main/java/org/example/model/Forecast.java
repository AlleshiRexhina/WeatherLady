package org.example.model;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "forecasts")
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="maxTemp")
    Double maxTemp;
    @Column(name="minTemp")
    Double minTemp;
    @Column(name="precipitation")
    Double precipitation;
    @Column(name="date")
    LocalDate date;
    @Column(name="location")
    String location;

    @Column(name="conditions")
    String conditions;

    public String getLocation() {
        return location;
    }

    public void setAddres(String addres) {
        location = addres;
    }

    public Forecast() {}

    public Forecast(Double maxTemp, Double minTemp, Double precipitation, LocalDate date, String location,String conditions) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.precipitation = precipitation;
        this.date= date;
        this.location = location;
        this.conditions = conditions;

    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    public Double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Double precipitation) {
        this.precipitation = precipitation;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "VENDNDODHJA : "+ location +
                "  Pershkrimi : " + conditions +
                "  Temperatura Max : " + maxTemp +
                "  Temperatura Min :" + minTemp +
                "  Precipitimi :" + precipitation +
                "  Data : " + date + '\'' ;
    }
}
