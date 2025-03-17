package com.weatherapp.weather_app;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    private Main main;
    private double lat;
    private double lon;

    public Main getMain() {

        return main;

    }

    public void setMain(Main main) {

        this.main = main;

    }

    public double getLat() {

        return lat;

    }

    public void setLat(Double lat) {

        this.lat = lat;

    }

    public double getLon() {

        return lon;

    }

    public void setLon(Double lon) {

        this.lon = lon;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {

        @JsonProperty("temp")
        private Double temp;

        @JsonProperty("feels_like")
        private Double feels_like;

        @JsonProperty("humidity")
        private int humidity;

        public Double getTemp() {

            return temp;

        }

        public void setTemp(Double temp) {

            this.temp = temp;

        }

        public Double getFeelsLike() {

            return feels_like;

        }

        public void setFeelsLike(Double feels_like) {

            this.feels_like = feels_like;

        }

        public int getHumidity() {

            return humidity;

        }

        public void setHumidity(int humidity) {

            this.humidity = humidity;

        }
    }
}