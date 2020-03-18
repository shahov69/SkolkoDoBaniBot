package ru.xander.telebot.crown;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author Alexander Shakhov
 */
@Getter
@Setter
@ToString
public class Crown {

    private List<Region> regions;

    @Getter
    @Setter
    @ToString
    public static class Region {
        private BufferedImage flag;
        private String name;
        private Integer confirmed;
        private Integer deaths;
        private Integer recoveries;
    }
}
