package ru.xander.telebot.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Alexander Shakhov
 */
@Getter
@Setter
public class WeatherTexts {

    private String locale;
    private String title;
    private String day;
    private String night;
    private String sunrise;
    private String sunset;
    private String feel;
    private String precipitation;
    private String cloudy;
    private String humidity;
    private String thunders;
    private String wind;
    private String uvIndex;

    public static WeatherTexts parse(String s) {
        String[] splitted = s.split("\\|");
        WeatherTexts texts = new WeatherTexts();
        texts.locale = splitted[0];
        texts.title = splitted[1];
        texts.day = splitted[2];
        texts.night = splitted[3];
        texts.sunrise = splitted[4];
        texts.sunset = splitted[5];
        texts.feel = splitted[6];
        texts.precipitation = splitted[7];
        texts.cloudy = splitted[8];
        texts.humidity = splitted[9];
        texts.thunders = splitted[10];
        texts.wind = splitted[11];
        texts.uvIndex = splitted[12];
        return texts;
    }

    public static WeatherTexts defaultTexts() {
        WeatherTexts texts = new WeatherTexts();
        texts.locale = "RU";
        texts.title = "погодка на ";
        texts.day = "ДЕНЬ";
        texts.night = "НОЧЬ";
        texts.sunrise = "Рассвет ";
        texts.sunset = "Закат ";
        texts.feel = "ощущается";
        texts.precipitation = "осадки";
        texts.cloudy = "облачность";
        texts.humidity = "влажность";
        texts.thunders = "гроза";
        texts.wind = "Ветер ";
        texts.uvIndex = "Уровень ультрафиолета ";
        return texts;
    }
}
