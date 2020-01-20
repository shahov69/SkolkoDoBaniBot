package ru.xander.telebot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import ru.xander.telebot.forecast.Forecast;

/**
 * @author Alexander Shakhov
 */
@RunWith(SpringRunner.class)
@RestClientTest(ForecastService.class)
public class ForecastServiceTest {

    @Autowired
    private ForecastService forecastService;

    @Test
    public void getForecast() {
        Forecast forecast = forecastService.getForecast();
        System.out.println(forecast);
        System.out.println(forecast.getHeadline());
        System.out.println(forecast.getDailyForecasts());
    }

    @TestConfiguration
    static class ForecastServiceConfiguration {
        @Bean
        public RestTemplate getRestTemplate() {
            return new RestTemplate();
        }
    }
}