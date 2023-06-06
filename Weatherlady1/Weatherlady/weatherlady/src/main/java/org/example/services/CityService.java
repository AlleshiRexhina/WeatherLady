package org.example.services;

import org.example.model.City;
import org.example.repository.CityRepository;

import java.util.List;

public class CityService {

    public static void printCityList() throws Exception {
        CityRepository cityRepository = new CityRepository();
        List<City> cities = cityRepository.getAllCities();

        printList(cities);
    }

    public static void printList(List<City> cities){
        for (City city : cities) {
            System.out.println(city.toString());
        }
    }
}
