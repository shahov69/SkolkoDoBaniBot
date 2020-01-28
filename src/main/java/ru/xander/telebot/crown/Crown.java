package ru.xander.telebot.crown;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.*;
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
        private Image flag;
        private String name;
        private int confirmed;
        private int deaths;
        private int recovered;
    }
}
