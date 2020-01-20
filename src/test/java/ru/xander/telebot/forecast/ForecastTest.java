package ru.xander.telebot.forecast;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexander Shakhov
 */
public class ForecastTest {

    private static final double DELTA = 1e-10;

    @Test
    public void parseTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Forecast forecast = objectMapper.readValue(getClass().getResource("/forecast.json"), Forecast.class);

        Assert.assertNotNull(forecast);

        Headline headline = forecast.getHeadline();
        Assert.assertNotNull(headline);
        Assert.assertEquals(LocalDateTime.of(2020, 1, 21, 1, 0, 0), headline.getEffectiveDate());
        Assert.assertEquals(1579557600L, headline.getEffectiveEpochDate().longValue());
        Assert.assertEquals(4, headline.getSeverity().intValue());
        Assert.assertEquals("Понедельник, поздняя ночь: снегопад с общим уровнем покров до 1 см", headline.getText());
        Assert.assertEquals("snow", headline.getCategory());
        Assert.assertEquals(LocalDateTime.of(2020, 1, 21, 7, 0, 0), headline.getEndDate());
        Assert.assertEquals(1579579200L, headline.getEndEpochDate().longValue());
        Assert.assertEquals("http://m.accuweather.com/ru/ru/rybinsk/296630/extended-weather-forecast/296630?unit=c", headline.getMobileLink());
        Assert.assertEquals("http://www.accuweather.com/ru/ru/rybinsk/296630/daily-weather-forecast/296630?unit=c", headline.getLink());

        List<DailyForecast> dailyForecasts = forecast.getDailyForecasts();
        Assert.assertNotNull(dailyForecasts);
        Assert.assertEquals(1, dailyForecasts.size());

        DailyForecast dailyForecast = dailyForecasts.get(0);
        Assert.assertNotNull(dailyForecast);
        Assert.assertEquals(LocalDateTime.of(2020, 1, 17, 7, 0, 0), dailyForecast.getDate());
        Assert.assertEquals(1579233600L, dailyForecast.getEpochDate().longValue());
        Assert.assertEquals(0.7d, dailyForecast.getHoursOfSun(), DELTA);
        Assert.assertEquals(Collections.singletonList("AccuWeather"), dailyForecast.getSources());
        Assert.assertEquals("http://m.accuweather.com/ru/ru/rybinsk/296630/daily-weather-forecast/296630?unit=c", dailyForecast.getMobileLink());
        Assert.assertEquals("http://www.accuweather.com/ru/ru/rybinsk/296630/daily-weather-forecast/296630?unit=c", dailyForecast.getLink());

        Sun sun = dailyForecast.getSun();
        Assert.assertNotNull(sun);
        Assert.assertEquals(LocalDateTime.of(2020, 1, 17, 8, 57, 0), sun.getRise());
        Assert.assertEquals(1579240620L, sun.getEpochRise().longValue());
        Assert.assertEquals(LocalDateTime.of(2020, 1, 17, 16, 14, 0), sun.getSet());
        Assert.assertEquals(1579266840L, sun.getEpochSet().longValue());

        Moon moon = dailyForecast.getMoon();
        Assert.assertNotNull(moon);
        Assert.assertEquals(LocalDateTime.of(2020, 1, 17, 0, 8, 0), moon.getRise());
        Assert.assertEquals(1579208880L, moon.getEpochRise().longValue());
        Assert.assertEquals(LocalDateTime.of(2020, 1, 17, 11, 46, 0), moon.getSet());
        Assert.assertEquals(1579250760L, moon.getEpochSet().longValue());
        Assert.assertEquals("Last", moon.getPhase());
        Assert.assertEquals(22, moon.getAge().intValue());

        Temperature temperature = dailyForecast.getTemperature();
        Assert.assertNotNull(temperature);
        assertValue(0.7, "C", 17, temperature.getMinimum());
        assertValue(4.2, "C", 17, temperature.getMaximum());

        Temperature realFeelTemperature = dailyForecast.getRealFeelTemperature();
        Assert.assertNotNull(realFeelTemperature);
        assertValue(-1.3, "C", 17, realFeelTemperature.getMinimum());
        assertValue(3.1, "C", 17, realFeelTemperature.getMaximum());

        Temperature realFeelTemperatureShade = dailyForecast.getRealFeelTemperatureShade();
        Assert.assertNotNull(realFeelTemperatureShade);
        assertValue(-1.3, "C", 17, realFeelTemperatureShade.getMinimum());
        assertValue(3.7, "C", 17, realFeelTemperatureShade.getMaximum());

        DegreeDaySummary degreeDaySummary = dailyForecast.getDegreeDaySummary();
        Assert.assertNotNull(degreeDaySummary);
        assertValue(16, "C", 17, degreeDaySummary.getHeating());
        assertValue(0, "C", 17, degreeDaySummary.getCooling());

        List<AirAndPollen> airAndPollens = dailyForecast.getAirAndPollens();
        Assert.assertNotNull(airAndPollens);
        Assert.assertEquals(6, airAndPollens.size());

        AirAndPollen airAndPollen = airAndPollens.get(0);
        Assert.assertNotNull(airAndPollen);
        Assert.assertEquals("AirQuality", airAndPollen.getName());
        Assert.assertEquals(0, airAndPollen.getValue().intValue());
        Assert.assertEquals("Хорошо", airAndPollen.getCategory());
        Assert.assertEquals(1, airAndPollen.getCategoryValue().intValue());
        Assert.assertEquals("Озон", airAndPollen.getType());

        Conditions day = dailyForecast.getDay();
        Assert.assertNotNull(day);
        Assert.assertEquals(7, day.getIcon().intValue());
        Assert.assertEquals("Облачно", day.getIconPhrase());
        Assert.assertTrue(day.getHasPrecipitation());
        Assert.assertEquals("Rain", day.getPrecipitationType());
        Assert.assertEquals("Light", day.getPrecipitationIntensity());
        Assert.assertEquals("Возможен ливень", day.getShortPhrase());
        Assert.assertEquals("Возможен ливень", day.getLongPhrase());
        Assert.assertEquals(55, day.getPrecipitationProbability().intValue());
        Assert.assertEquals(20, day.getThunderstormProbability().intValue());
        Assert.assertEquals(55, day.getRainProbability().intValue());
        Assert.assertEquals(14, day.getSnowProbability().intValue());
        Assert.assertEquals(0, day.getIceProbability().intValue());
        assertValue(1.2, "mm", 3, day.getTotalLiquid());
        assertValue(1.2, "mm", 3, day.getRain());
        assertValue(0, "cm", 4, day.getSnow());
        assertValue(0, "mm", 3, day.getIce());
        Assert.assertEquals(1, day.getHoursOfPrecipitation().intValue());
        Assert.assertEquals(1, day.getHoursOfRain().intValue());
        Assert.assertEquals(0, day.getHoursOfSnow().intValue());
        Assert.assertEquals(0, day.getHoursOfIce().intValue());
        Assert.assertEquals(93, day.getCloudCover().intValue());

        Wind wind = day.getWind();
        Assert.assertNotNull(wind);
        assertValue(11.1, "km/h", 7, wind.getSpeed());

        WindDirection windDirection = wind.getDirection();
        Assert.assertNotNull(windDirection);
        Assert.assertEquals(258, windDirection.getDegrees().intValue());
        Assert.assertEquals("ЗЮЗ", windDirection.getLocalized());
        Assert.assertEquals("WSW", windDirection.getEnglish());

        Conditions night = dailyForecast.getNight();
        Assert.assertNotNull(night);
    }

    private static void assertValue(double value, String unit, int unitType, Value val) {
        Assert.assertNotNull(val);
        Assert.assertEquals(value, val.getValue(), DELTA);
        Assert.assertEquals(unit, val.getUnit());
        Assert.assertEquals(unitType, val.getUnitType().intValue());
    }

}