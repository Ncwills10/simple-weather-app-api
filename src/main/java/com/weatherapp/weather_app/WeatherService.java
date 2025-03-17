package com.weatherapp.weather_app;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WeatherService {
    
    private static final String API_KEY = "";

    public final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;

    }

    public String getWeather(String city) {

        String baseUri = "http://api.openweathermap.org/geo/1.0/direct";

        URI uri = null;

        try {

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUri);

            builder.queryParam("q", city);
            builder.queryParam("limit", 2);
            builder.queryParam("appid", API_KEY);

            uri = builder.build().toUri();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        } 

        Double latitude = 0.0;
        Double longitude = 0.0;

        ResponseEntity <List <WeatherResponse> > responseEntity = null;

        try {
            
            responseEntity = restTemplate.exchange(
                
                uri, 
                HttpMethod.GET, 
                null, 
                new ParameterizedTypeReference <List <WeatherResponse> >(){}
            
            );

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        List <WeatherResponse> weatherResponses = responseEntity.getBody();

        WeatherResponse response = weatherResponses.get(0);

        try {

            latitude = response.getLat();
            longitude = response.getLon();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        baseUri = "https://api.openweathermap.org/data/2.5/weather";

        try {

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUri);

            builder.queryParam("lat", latitude);
            builder.queryParam("lon", longitude);
            builder.queryParam("appid", API_KEY);
            builder.queryParam("units", "metric");

            uri = builder.build().toUri();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        System.out.println(uri);

        try {
            
            response = restTemplate.getForObject(uri, WeatherResponse.class);

        } catch (Exception e) {

            e.printStackTrace();

        }

        double temp = 0.0;
        double feelsLike = 0.0;
        int humidity = 0;

        try {

            temp = response.getMain().getTemp();
            feelsLike = response.getMain().getFeelsLike();
            humidity = response.getMain().getHumidity();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        int temperature = (int) temp;
        int feelsLikeInt = (int) feelsLike;

        return "Temperature: " + temperature + "°C \n" + "Feels like: " + feelsLikeInt + "°C \n" + "Humidity: " + humidity + "%";

    }
}