package ru.xander.telebot.service;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import ru.xander.telebot.forecast.Forecast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    @Test
    public void getForecastRender() throws IOException {
        InputStream input = forecastService.getForecastRender();
        IOUtils.copy(input, new FileOutputStream("D:\\weather.png"));
    }

    @TestConfiguration
    static class ForecastServiceConfiguration {
        @Bean
        public RestTemplate getRestTemplate() {
            return new RestTemplate();
        }
    }
}