package ru.xander.telebot.shizm;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import ru.xander.telebot.dto.Fonts;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Alexander Shakhov
 */
@Ignore
public class PosterRendererTest {

    private static final String outPath = "d:\\Sources\\.temp\\shizm";
    private static final String[] samples = {
            "Lorem ipsum",
            "Lorem ipsum dolor sit amet",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                    "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                    "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    };

    @Test
    public void render() throws IOException {
        final Poster poster = Poster.getRandomIlyaPoster();
        final Font font = Fonts.NEWS_CYCLE.getFont();
        for (HorizontalAlign horzAlign : HorizontalAlign.values()) {
            for (VerticalAlign verticalAlign : VerticalAlign.values()) {
                String text = horzAlign + " " + verticalAlign;
                String fileName = "ALIGN_" + text + ".jpg";
                renderPoster(text, fileName, poster.bubbleHAlign(horzAlign).bubbleVAlign(verticalAlign), font);
            }
        }
        for (int i = 0; i < samples.length; i++) {
            for (HorizontalAlign horzAlign : HorizontalAlign.values()) {
                String fileName = "SAMPLE_" + (i + 1) + "_" + horzAlign + ".jpg";
                renderPoster(samples[i], fileName, poster.textAlign(horzAlign), font);
            }
        }
        for (Fonts fonts : Fonts.values()) {
            String fileName = "FONT_" + fonts + ".jpg";
            renderPoster(samples[4], fileName, poster, fonts.getFont());
        }
    }

    private static void renderPoster(String text, String fileName, Poster poster, Font font) throws IOException {
        InputStream render = new PosterRenderer(true).render(poster, text, font);
        IOUtils.copy(render, new FileOutputStream(new File(outPath, fileName)));
    }
}