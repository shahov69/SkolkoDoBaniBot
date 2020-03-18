package ru.xander.telebot.crown;

import org.jsoup.nodes.Element;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * @author Alexander Shakhov
 */
public class TestFlagExtractor implements CrownExtractor.FlagExtractor {

    private final Image flag;

    public TestFlagExtractor() throws IOException {
        flag = ImageIO.read(getClass().getResourceAsStream("/flag.png"));
    }

    @Override
    public Image extractFlag(Element flagElement) {
        return flag;
    }
}
