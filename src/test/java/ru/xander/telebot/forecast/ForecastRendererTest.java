package ru.xander.telebot.forecast;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * @author Alexander Shakhov
 */
@Ignore
public class ForecastRendererTest {
    @Test
    public void render() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Forecast forecast = objectMapper.readValue(getClass().getResource("/forecast.json"), Forecast.class);
        ForecastRenderer renderer = new ForecastRenderer();
        InputStream input = renderer.render(forecast);
        OutputStream output = new FileOutputStream("D:\\weather.png");
        IOUtils.copy(input, output);
    }

    @Test
    public void getSplitPhrase() {
        final String s = "Временами снег и порывистый ветер";
        Assert.assertEquals(Arrays.asList("Временами снег и", "порывистый ветер"), ForecastRenderer.splitPhrase(s));
    }
}