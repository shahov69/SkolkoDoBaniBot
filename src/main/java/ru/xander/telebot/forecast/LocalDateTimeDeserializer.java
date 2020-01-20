package ru.xander.telebot.forecast;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @author Alexander Shakhov
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private final ThreadLocal<DateTimeFormatter> formatterHolder = new ThreadLocal<>();

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TemporalAccessor temporalAccessor = getFormatter().parse(jsonParser.getText());
        return LocalDateTime.from(temporalAccessor);
    }

    private DateTimeFormatter getFormatter() {
        DateTimeFormatter formatter = this.formatterHolder.get();
        if (formatter == null) {
            formatter = DateTimeFormatter.ofPattern("yyyy'-'MM'-'dd'T'HH':'mm':'ssXXX");
            this.formatterHolder.set(formatter);
        }
        return formatter;
    }
}
