package ru.xander.telebot.forecast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author Alexander Shakhov
 */
@Getter
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Headline {
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime effectiveDate;
    private Long effectiveEpochDate;
    private Integer severity;
    private String text;
    private String category;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;
    private Long endEpochDate;
    private String mobileLink;
    private String link;
}
