package org.example.services;

import org.example.csvutils.CSVWriter;
import org.example.model.Forecast;
import org.example.regex.FormattedDateMatcher;
import org.example.repository.CityRepository;
import org.example.repository.ForecastRepository;
import org.example.weather_api.WeatherApiServices;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ForecastService {

    public static void getForecastForToday() throws Exception {

        ForecastRepository forecastRepository = new ForecastRepository();

        String location = CityRepository.getCityById(getIdByUser());

        List<Forecast> forecasts = forecastRepository.getForecastAllDays(location, LocalDate.now());

        if (forecasts.isEmpty()){
            forecastRepository.saveForecasts(WeatherApiServices.timelineRequestHttpClient(location,null,null));
            forecasts=forecastRepository.getForecastAllDays(location,LocalDate.now());
        }

        printList(forecasts);
        savecsv(forecasts);

    }

    public static void getForecastFifteenDays() throws Exception {
        List<Forecast> forecasts  = WeatherApiServices.timelineRequestHttpClient(CityRepository.getCityById(getIdByUser()), null, null).stream().toList();
        printList(forecasts);
        savecsv(forecasts);
    }
    public static void getForecastForSpecificDate() throws Exception {

        ForecastRepository forecastRepository = new ForecastRepository();
        Integer id = getIdByUser();
        if (id == null || id<1 || id > 21 ){
            System.out.println("Id e qytetit e pnjohur");
            return;
        }
        String location = CityRepository.getCityById(id);
        String date = getDateByUser();
        if (date==null) {
            System.out.println("Keni futur daten ne faormatin e gabuar");
            return;
        }




        List<Forecast> forecasts = forecastRepository.getForecastAllDays(location, LocalDate.parse(date));

        if (forecasts.isEmpty()){
            forecastRepository.saveForecasts(WeatherApiServices.timelineRequestHttpClient(location,date));
            forecasts=forecastRepository.getForecastAllDays(location,LocalDate.now());
        }

        printList(forecasts);
        savecsv(forecasts);

    }

    public static void getCityForecastFromUser() throws Exception {
        String cityName = getCityNameFromUser();
        List<Forecast> forecasts = new ArrayList<>();
        try {
            forecasts  = WeatherApiServices.timelineRequestHttpClient(cityName, null, null).stream().toList();

        }catch (Exception e){
            System.out.println("Nuk gjenden te dhena per : " + cityName );
            return;
        }
        if (forecasts.isEmpty()){
            return;
        }

        printList(forecasts);
        savecsv(forecasts);

    }

    public static void getCityForecastAndDateFromUser() throws Exception {
        String cityName = getCityNameFromUser();
        String date = getDateByUser();
        List<Forecast> forecasts = new ArrayList<>();
        if(date==null){
            return;
        }
        try {
            forecasts  = WeatherApiServices.timelineRequestHttpClient(cityName, date, null).stream().toList();
        }catch (Exception e){
            System.out.println("Nuk gjenden te dhena per : "+ cityName );
            return;
        }
        if (forecasts.isEmpty()){
            System.out.println("Nuk gjenden te dhena per : "+ cityName );
            return;
        }
        printList(forecasts);
        savecsv(forecasts);

    }

    private static String getDateByUser() {
        Scanner scanner = new Scanner(System.in);
        FormattedDateMatcher dateMatcher = new FormattedDateMatcher();
        String date;
        try {

            System.out.println("Vendosni daten ne formatin YYYY-MM-DD");
            date = scanner.nextLine() ;
            LocalDate ld = LocalDate.parse(date);
            LocalDate fromWhenDate = LocalDate.parse("2010-01-01");
            if (!dateMatcher.matches(date)){
                return null;
            }

            if (LocalDate.now().isBefore(ld) || fromWhenDate.isAfter(ld)){
                System.out.println("Ju Lutemi Vendosni nje date ne harkun kohor 01 Janar 2010 --- Sot ");
            }
        }catch (Exception e){
            System.out.println("Ju keni futur vlere te gabuar\n\n");
            return null;
        }
        return date;
    }

    public static void printList(List<Forecast> forecasts){
        for (Forecast forecast: forecasts) {
            System.out.println(forecast.toString());
        }
    }

    public static Integer getIdByUser(){
        Scanner scanner = new Scanner(System.in);
        Integer id;
        try {

            System.out.println("Jep id e qytetit");
            id = scanner.nextInt();

        }catch (Exception e){
            System.out.println("Ju keni futur vlere te gabuar\n\n");
            return null;
        }
        return id;
    }
    public static String getCityNameFromUser(){
        Scanner scanner = new Scanner(System.in);
        String cityName;
        try {

            System.out.println("Jep Emrin e qytetit ");

            cityName = scanner.nextLine();

        }catch (Exception e){
            System.out.println("Ju keni futur vlere te gabuar\n\n");
            return null;
        }
        return cityName;
    }

    public static void savecsv(List<Forecast> forecasts){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Deshironi ta ruani si file ? y / n");
        String c = scanner.nextLine();
        Forecast[] forecasts1 = forecasts.toArray(new Forecast[0]);
        if (c!="y"){
            return;
        }else {
            CSVWriter.generateCSV(new File("C:\\Users\\User\\Desktop\\weatherlady\\weatherlady1\\forecasts.csv"),forecasts1);
            }
        }
    }


