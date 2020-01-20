package ru.xander.telebot.forecast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

/**
 * @author Alexander Shakhov
 */
@Getter
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Conditions {
    private Integer icon;
    private String iconPhrase;
    private Boolean hasPrecipitation;
    private String precipitationType;
    private String precipitationIntensity;
    private String shortPhrase;
    private String longPhrase;
    private Integer precipitationProbability;
    private Integer thunderstormProbability;
    private Integer rainProbability;
    private Integer snowProbability;
    private Integer iceProbability;
    private Wind wind;
    private Wind windGust;
    private Value totalLiquid;
    private Value rain;
    private Value snow;
    private Value ice;
    private Integer hoursOfPrecipitation;
    private Integer hoursOfRain;
    private Integer hoursOfSnow;
    private Integer hoursOfIce;
    private Integer cloudCover;
}
