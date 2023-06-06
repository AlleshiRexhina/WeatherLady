package org.example.repository;

import org.example.model.City;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CityRepository {
    public List<City> getAllCities() throws Exception {

        List<City> cities = new ArrayList<>();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("weatherlady-em");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        cities= entityManager.createQuery("SELECT a FROM City a", City.class).getResultList();


        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();

        return  cities;
    }
    public static String getCityById(Integer id){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("weatherlady-em");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        String location = entityManager.find(City.class,id).getLocation();

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
        return location;
    }

    public void insert(City city){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("weatherlady-em");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(city);  // transient to managed

        entityManager.getTransaction().commit(); // con nje query ne db dhe behet flush
        entityManager.close();
        entityManagerFactory.close();
    }
}
