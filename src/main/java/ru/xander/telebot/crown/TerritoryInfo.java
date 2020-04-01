package ru.xander.telebot.crown;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

/**
 * @author Alexander Shakhov
 */
@Getter
@Setter
public class TerritoryInfo {

    private BufferedImage flag;
    private String name;
    private int confirmed;
    private int deaths;
    private int recoveries;
    private int confirmedYesterday;
    private int deathsYesterday;
    private int recoveriesYesterday;
    private boolean today;

    public int getSick() {
        return confirmed - deaths - recoveries;
    }

    public int getSickYesterday() {
        return confirmedYesterday - deathsYesterday - recoveriesYesterday;
    }

    public int getConfirmedDelta() {
        return confirmed - confirmedYesterday;
    }

    public int getDeathsDelta() {
        return deaths - deathsYesterday;
    }

    public int getRecoveriesDelta() {
        return recoveries - recoveriesYesterday;
    }

    public int getSickDelta() {
        return getSick() - getSickYesterday();
    }

    public double getCurrentMortality() {
        return deaths == 0 ? 0.0d : (deaths / (double) (deaths + recoveries)) * 100.0d;
    }

    public double getVirtualMortality() {
        return confirmed == 0 ? 0.0d : (deaths / (double) (confirmed)) * 100.0d;
    }
}