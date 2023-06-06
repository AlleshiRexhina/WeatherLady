package org.example.weather_api;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.example.model.Forecast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.*;

import java.util.ArrayList;

public class WeatherApiServices {
    public static ArrayList<Forecast> timelineRequestHttpClient(String location,String date) throws Exception {
        String apiEndPoint="https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";


        String unitGroup="metric";
        String apiKey="SHBAKFQ4SYW6CTLQ396J8HDLJ";

        StringBuilder requestBuilder=new StringBuilder(apiEndPoint);
        requestBuilder.append(URLEncoder.encode(location, StandardCharsets.UTF_8.toString()));

        if (date!=null ) {
            requestBuilder.append("/").append(date);

        }

        URIBuilder builder = new URIBuilder(requestBuilder.toString());

        builder.setParameter("unitGroup", unitGroup)
                .setParameter("key", apiKey);



        HttpGet get = new HttpGet(builder.build());

        CloseableHttpClient httpclient = HttpClients.createDefault();

        CloseableHttpResponse response = httpclient.execute(get);

        String rawResult=null;
        try {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                System.out.printf("Bad response status code:%d%n", response.getStatusLine().getStatusCode());
                return null;
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                rawResult= EntityUtils.toString(entity, Charset.forName("utf-8"));
            }


        } finally {
            response.close();
        }

        return  parseTimelineJson(rawResult);

    }
    public static ArrayList<Forecast> timelineRequestHttpClient(String location,String startDate,String endDate) throws Exception {
        String apiEndPoint="https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";




        String unitGroup="metric";
        String apiKey="SHBAKFQ4SYW6CTLQ396J8HDLJ";

        StringBuilder requestBuilder=new StringBuilder(apiEndPoint);
        requestBuilder.append(URLEncoder.encode(location, StandardCharsets.UTF_8.toString()));

        if (startDate!=null && !startDate.isEmpty()) {
            requestBuilder.append("/").append(startDate);
            if (endDate!=null && !endDate.isEmpty()) {
                requestBuilder.append("/").append(endDate);
            }
        }

        URIBuilder builder = new URIBuilder(requestBuilder.toString());

        builder.setParameter("unitGroup", unitGroup)
                .setParameter("key", apiKey);



        HttpGet get = new HttpGet(builder.build());

        CloseableHttpClient httpclient = HttpClients.createDefault();

        CloseableHttpResponse response = httpclient.execute(get);

        String rawResult=null;
        try {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                System.out.printf("Bad response status code:%d%n", response.getStatusLine().getStatusCode());
                return null;
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                rawResult= EntityUtils.toString(entity, Charset.forName("utf-8"));
            }


        } finally {
            response.close();
        }

        return  parseTimelineJson(rawResult);

    }
    private static ArrayList<Forecast> parseTimelineJson(String rawResult) throws JSONException {
        ArrayList<Forecast> forecasts = new ArrayList<>();
        if (rawResult==null || rawResult.isEmpty()) {
            System.out.printf("No raw data%n");
            return null;
        }

        JSONObject timelineResponse = new JSONObject(rawResult);

        ZoneId zoneId=ZoneId.of(timelineResponse.getString("timezone"));

        JSONArray values=timelineResponse.getJSONArray("days");

        for (int i = 0; i < values.length(); i++) {
            JSONObject dayValue = values.getJSONObject(i);
            ZonedDateTime datetime=ZonedDateTime.ofInstant(Instant.ofEpochSecond(dayValue.getLong("datetimeEpoch")), zoneId);
            LocalDate date = datetime.toLocalDate();
            double maxtemp=dayValue.getDouble("tempmax");
            double mintemp=dayValue.getDouble("tempmin");
            double pop=dayValue.getDouble("precip");

            String conditions = dayValue.getString("conditions");

            forecasts.add(new Forecast(maxtemp, mintemp ,pop ,date,timelineResponse.getString("resolvedAddress"),conditions));
        }
        return forecasts;
    }



}
