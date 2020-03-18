package ru.xander.telebot.crown;

import org.jsoup.nodes.Element;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Alexander Shakhov
 */
public class TestFlagExtractor implements CrownExtractor.FlagExtractor {

    private final BufferedImage flag;

    public TestFlagExtractor() throws IOException {
        flag = ImageIO.read(getClass().getResourceAsStream("/flag.png"));
    }

    @Override
    public BufferedImage extractFlag(Element flagElement) {
        return flag;
    }
}
