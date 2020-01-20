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
public class Sun {
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime rise;
    private Long epochRise;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime set;
    private Long epochSet;
}