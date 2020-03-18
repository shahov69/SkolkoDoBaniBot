package ru.xander.telebot.crown;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * @author Alexander Shakhov
 */
@Getter
public class CrownInfo {

    private final List<TerritoryInfo> territories;
    private final int totalTerritories;
    private final int totalTerritoriesDelta;
    private final int totalConfirmed;
    private final int totalConfirmedDelta;
    private final int totalDeaths;
    private final int totalDeathsDelta;
    private final int totalRecoveries;
    private final int totalRecoveriesDelta;
    private final int totalSick;
    private final int totalSickDelta;
    private final double currentMortality;
    private final double virtualMortality;

    public CrownInfo(List<TerritoryInfo> territories) {
        Objects.requireNonNull(territories);
        this.territories = territories;
        int _totalTerritoriesDelta = 0;
        int _totalConfirmed = 0;
        int _totalConfirmedDelta = 0;
        int _totalDeaths = 0;
        int _totalDeathsDelta = 0;
        int _totalRecoveries = 0;
        int _totalRecoveriesDelta = 0;
        int _totalSick = 0;
        int _totalSickDelta = 0;
        for (TerritoryInfo territory : territories) {
            if (territory.isToday()) {
                _totalTerritoriesDelta++;
            }
            _totalConfirmed += territory.getConfirmed();
            _totalConfirmedDelta += territory.getConfirmedDelta();
            _totalDeaths += territory.getDeaths();
            _totalDeathsDelta += territory.getDeathsDelta();
            _totalRecoveries += territory.getRecoveries();
            _totalRecoveriesDelta += territory.getRecoveriesDelta();
            _totalSick += territory.getSick();
            _totalSickDelta += territory.getSickDelta();
        }
        this.totalTerritories = territories.size();
        this.totalTerritoriesDelta = _totalTerritoriesDelta;
        this.totalConfirmed = _totalConfirmed;
        this.totalConfirmedDelta = _totalConfirmedDelta;
        this.totalDeaths = _totalDeaths;
        this.totalDeathsDelta = _totalDeathsDelta;
        this.totalRecoveries = _totalRecoveries;
        this.totalRecoveriesDelta = _totalRecoveriesDelta;
        this.totalSick = _totalSick;
        this.totalSickDelta = _totalSickDelta;

        this.currentMortality = totalDeaths == 0 ? 0.0d : (totalDeaths / (double) (totalDeaths + totalRecoveries)) * 100.0d;
        this.virtualMortality = totalConfirmed == 0 ? 0.0d : (totalDeaths / (double) (totalConfirmed)) * 100.0d;
    }
}
