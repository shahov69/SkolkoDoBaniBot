package ru.xander.telebot.forecast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Alexander Shakhov
 */
@Getter
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyForecast {
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;
    private Long epochDate;
    private Sun sun;
    private Moon moon;
    private Temperature temperature;
    private Temperature realFeelTemperature;
    private Temperature realFeelTemperatureShade;
    private Double hoursOfSun;
    private DegreeDaySummary degreeDaySummary;
    @JsonProperty("AirAndPollen")
    private List<AirAndPollen> airAndPollens;
    private Conditions day;
    private Conditions night;
    private List<String> sources;
    private String mobileLink;
    private String link;
}
