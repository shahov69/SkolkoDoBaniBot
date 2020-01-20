package ru.xander.telebot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.xander.telebot.forecast.Forecast;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Shakhov
 */
@Service
public class ForecastService {
    private static final String FORECAST_URL_PATTERN = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/%d?apikey=%s&language=ru-ru&details=true&metric=true";
    private final RestTemplate rest;
    private final Map<LocalDate, Forecast> forecastCache;

    @Value("${accuweather.apikey}")
    private String accuweatherApikey;

    @Autowired
    public ForecastService(RestTemplate rest) {
        this.rest = rest;
        this.forecastCache = new HashMap<>();
    }

    public Forecast getForecast() {
        Forecast forecast = forecastCache.get(LocalDate.now());
        if (forecast != null) {
            return forecast;
        }
        String forecastUrl = String.format(FORECAST_URL_PATTERN, 296630, accuweatherApikey);
        ResponseEntity<Forecast> forecastEntity = rest.getForEntity(forecastUrl, Forecast.class);
        HttpStatus statusCode = forecastEntity.getStatusCode();
        if (statusCode != HttpStatus.OK) {
            throw new RuntimeException(statusCode.toString());
        }
        forecast = forecastEntity.getBody();
        forecastCache.put(LocalDate.now(), forecast);
        return forecast;
    }
}
