package org.example.controller;


import org.example.services.CityService;
import org.example.services.ForecastService;
import java.util.Scanner;

public class Controller {
    public Controller() throws Exception {

        ForecastService forecastService = new ForecastService();

        System.out.println("PERSHENDETJE");

        Scanner scanner= new Scanner(System.in);

        String doVazhdosh = "";
        while (!doVazhdosh.equals("jo")) {
            System.out.println("Zgjidh Veprim : ");
            System.out.println(
                    "\n\n"+
                            "Printo listen e Qyteteve : 1 \n" +
                            "Parashikimi i motit per 15 ditet ne vazhdim  : 2\n"+
                            "Parashikimi i motit per diten e sotme : 3\n"+
                            "Parashikimi i motit per date te caktuar : 4 \n"+
                            "Parashikimi i motit per nje Qytet qe nuk ndodhet ne liste : 5 \n"+
                            "Parashikimi i motit per nje Qytet qe nuk ndodhet ne liste ne nje date te caktuar : 6 \n"

            );
            veprimet(zgjidhVeprim());
            System.out.println("Per te vazhduar shtyp po , Shtyp jo nese deshironi te mbyllni programin \n");
            doVazhdosh = scanner.nextLine();
        }

    }

    public Integer zgjidhVeprim(){
        Scanner scanner = new Scanner(System.in);
        Integer veprimi = null;
        while (veprimi==null){

            try {
                int vep = Integer.parseInt(scanner.nextLine());
                if ((vep > 0) && (vep < 7)) {
                    veprimi=vep;
                }else {throw new Exception();}
            }catch (Exception e){
                System.out.println("Keni futur veprim te paligjshem!");
            }
        }
        return veprimi;
    }

    public void veprimet(Integer veprimi) throws Exception {
        switch (veprimi) {
            case 1 -> CityService.printCityList();
            case 2 -> ForecastService.getForecastFifteenDays();
            case 3 -> ForecastService.getForecastForToday();
            case 4 -> ForecastService.getForecastForSpecificDate();
            case 5 -> ForecastService.getCityForecastFromUser();
            case 6 -> ForecastService.getCityForecastAndDateFromUser();
        }
    }



}
