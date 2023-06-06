package org.example.repository;

import org.example.model.Forecast;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

public class ForecastRepository {

    public void insert(Forecast forecast){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("weatherlady-em");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(forecast);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    public List<Forecast> getForecastAllDays(String location, LocalDate date) throws Exception {

            List<Forecast> forecasts = new ArrayList<>();

            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("weatherlady-em");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            StringBuilder sb = new StringBuilder(location).append(", Shqipëria");


            forecasts= entityManager.createQuery("select m from Forecast m where m.location like :location and m.date = :date")
                    .setParameter("location",sb.toString())
                    .setParameter("date",date)
                    .getResultList();


            entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();

            return  forecasts;
        }

        public void saveForecasts (List<Forecast> forecasts){
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("weatherlady-em");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

        for (Forecast forecast:forecasts) {

                entityManager.persist(forecast);

            }
            entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();
        }


    }


