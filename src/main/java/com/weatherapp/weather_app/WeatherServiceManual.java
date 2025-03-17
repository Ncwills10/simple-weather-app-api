package com.weatherapp.weather_app;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

@Service
public class WeatherServiceManual {
    
    private static final String API_KEY = "";

    public String getWeatherManual(String city) {

        String geoCodingUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=5&appid=" + API_KEY;

        URL geoUrl = null;

        try {
            
            URI geoUri = new URI(geoCodingUrl);
            geoUrl = geoUri.toURL();

        } catch (Exception e) {

            System.out.println("The URL is broken");

        }

        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;

        try {

            connection = (HttpURLConnection) geoUrl.openConnection();
            connection.setRequestMethod("GET");
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        } catch (Exception e) {

            System.out.println("Connection error");

        }

        String line; 
        StringBuilder sb = new StringBuilder();

        double latitude = 0;
        double longitude = 0;

        try {

            while ((line = bufferedReader.readLine()) != null) {

                sb.append(line);

            }

            bufferedReader.close();
            connection.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(sb.toString());

            latitude = node.get(0).get("lat").asDouble();
            longitude = node.get(0).get("lon").asDouble();

        } catch (Exception e) {

            System.out.println("Parsing error");

        }
        
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY + "&units=metric";

        URL url = null;

        try {
            
            URI uri = new URI(apiUrl);
            url = uri.toURL();

        } catch (Exception e) {

            System.out.println("The URL is broken");

        }

        connection = null;
        bufferedReader = null;

        try {

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        } catch (Exception e) {
            
            System.out.println("Connection error");

        }
 
        sb = new StringBuilder();

        double temperature = -1000;
        double feelsLike = -1000;
        double humidity = -1000;

        int temp = -1000;
        int feelsLikeInt = -1000;

        try {

            while ((line = bufferedReader.readLine()) != null) {

                sb.append(line);

            }

            bufferedReader.close();
            connection.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(sb.toString());

            temperature = node.get("main").get("temp").asDouble();
            feelsLike = node.get("main").get("feels_like").asDouble();
            humidity = node.get("main").get("humidity").asDouble();

            temp = (int) temperature;
            feelsLikeInt = (int) feelsLike;

        } catch (Exception e) {

            System.out.println("Parsing error");

        }

        return "Temperature: " + temp + "°C \n" + "Feels like: " + feelsLikeInt + "°C \n" + "Humidity: " + humidity + "%";

    }
}