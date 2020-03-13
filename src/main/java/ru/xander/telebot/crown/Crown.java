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

    public int getTotalTerritories() {
        return regions.size();
    }

    public int getTotalConfirmed() {
        return regions.stream().mapToInt(r -> r.getConfirmed() == null ? 0 : r.getConfirmed()).sum();
    }

    public int getTotalDeaths() {
        return regions.stream().mapToInt(r -> r.getDeaths() == null ? 0 : r.getDeaths()).sum();
    }

    public int getTotalRecoveries() {
        return regions.stream().mapToInt(r -> r.getRecoveries() == null ? 0 : r.getRecoveries()).sum();
    }

    public int getTotalSick() {
        return regions.stream().mapToInt(r -> r.getSick() == null ? 0 : r.getSick()).sum();
    }

    public double getCurrentMortality() {
        int totalDeaths = getTotalDeaths();
        if (totalDeaths == 0) {
            return 0.0d;
        }
        int totalRecoveries = getTotalRecoveries();
        return (totalDeaths / (double) (totalDeaths + totalRecoveries)) * 100.0d;
    }

    public double getVirtualMortality() {
        int totalDeaths = getTotalDeaths();
        if (totalDeaths == 0) {
            return 0.0d;
        }
        int totalConfirmed = getTotalConfirmed();
        return (totalDeaths / (double) (totalConfirmed)) * 100.0d;
    }

    @Getter
    @Setter
    @ToString
    public static class Region {
        private Image flag;
        private String name;
        private Integer confirmed;
        private Integer deaths;
        private Integer recoveries;

        public Integer getSick() {
            if (confirmed == null) {
                return null;
            }
            int sick = confirmed;
            if (deaths != null) {
                sick -= deaths;
            }
            if (recoveries != null) {
                sick -= recoveries;
            }
            return sick;
        }
    }
}
