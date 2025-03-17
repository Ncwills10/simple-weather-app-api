package com.weatherapp.weather_app;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.net.URI;
import org.springframework.core.ParameterizedTypeReference;
import com.weatherapp.weather_app.WeatherResponse.Main;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.*;

public class WeatherServiceTest {
    
    @Mock
    RestTemplate restTemplate;

    private WeatherService weatherService;

    @BeforeEach
    private void setUp() {
    
        MockitoAnnotations.openMocks(this);
        weatherService = new WeatherService(restTemplate);

    }

    @Test
    public void testGetWeather() {

        WeatherResponse mockResponseGeo = new WeatherResponse();

        mockResponseGeo.setLat(2.2);
        mockResponseGeo.setLon(3.3);

        List <WeatherResponse> mockResponses = new ArrayList<>();

        mockResponses.add(mockResponseGeo);

        ResponseEntity <List <WeatherResponse> > mockResponsesEntity = new ResponseEntity<>(mockResponses, HttpStatus.OK);

        when(restTemplate.exchange(
            
            Mockito.any(URI.class), 
            Mockito.eq(HttpMethod.GET), 
            isNull(),
            Mockito.eq(new ParameterizedTypeReference <List <WeatherResponse> >(){})
            
            )).thenReturn(mockResponsesEntity);

        WeatherResponse mockResponse = new WeatherResponse();

        mockResponse.setMain(new Main());
        mockResponse.getMain().setHumidity(5);
        mockResponse.getMain().setTemp(1.1);
        mockResponse.getMain().setFeelsLike(4.4);

        when(restTemplate.getForObject(Mockito.any(URI.class), Mockito.eq(WeatherResponse.class))).thenReturn(mockResponse);

        String result = weatherService.getWeather("London");

        assertNotNull(result);
        assertTrue(result.contains("Temperature: 1°C"));
        assertTrue(result.contains("Humidity: 5%"));
        assertTrue(result.contains("Feels like: 4°C"));

    }
}