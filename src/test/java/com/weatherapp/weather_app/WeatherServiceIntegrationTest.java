package com.weatherapp.weather_app;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
public class WeatherServiceIntegrationTest {
    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherService weatherService;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {

        mockServer = MockRestServiceServer.createServer(restTemplate);

    }

    @Test
    public void getWeatherTest_Sucess() {

        String responseGeo = """
            [
                {
                    "name" : "London",
                    "lon" : 1.3,
                    "lat" : 1.4
                }
            ]
                """;

        mockServer.expect(ExpectedCount.once(), 
            MockRestRequestMatchers.requestTo(containsString("http://api.openweathermap.org/geo/1.0/direct")))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(responseGeo, MediaType.APPLICATION_JSON));

        String response = """
                {
                    "main" : {
                        "temp" : 2.4,
                        "humidity" : 4,
                        "feels_like" : 3.4
                    }   
                }
                """;

        mockServer.expect(ExpectedCount.once(), 
            MockRestRequestMatchers.requestTo(containsString("https://api.openweathermap.org/data/2.5/weather")))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(response, MediaType.APPLICATION_JSON));

        String result = weatherService.getWeather("London");

        assertThat(result, containsString("Temperature: 2°C"));
        assertThat(result, containsString("Feels like: 3°C"));
        assertThat(result, containsString("Humidity: 4%"));
        
    }
}