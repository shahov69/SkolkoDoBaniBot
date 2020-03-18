package ru.xander.telebot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xander.telebot.crown.Crown;
import ru.xander.telebot.dto.SettingName;
import ru.xander.telebot.entity.CrownEntity;
import ru.xander.telebot.repository.CrownRepo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Alexander Shakhov
 */
@Service
public class CrownService {

    private final CrownRepo crownRepo;
    private final SettingService settingService;

    @Autowired
    public CrownService(CrownRepo crownRepo, SettingService settingService) {
        this.crownRepo = crownRepo;
        this.settingService = settingService;
    }

    public void update(Crown crown) {
        LocalDate crownDay = settingService.getLocalDate(SettingName.CROWN_DAY, "yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        boolean updateYesterday = (crownDay != null) && (crownDay.compareTo(now) < 0);

        Map<String, CrownEntity> crownEntities = crownRepo.findAll().stream().collect(Collectors.toMap(CrownEntity::getTerritory, c -> c));

        for (Crown.Region region : crown.getRegions()) {
            CrownEntity crownEntity = crownEntities.get(region.getName());
            if (crownEntity == null) {
                crownEntity = new CrownEntity();
                crownEntity.setTerritory(region.getName());
                crownEntity.setConfirmedToday(region.getConfirmed());
                crownEntity.setDeathsToday(region.getDeaths());
                crownEntity.setRecoveriesToday(region.getRecoveries());
                crownEntity.setConfirmedYesterday(0);
                crownEntity.setDeathsYesterday(0);
                crownEntity.setRecoveriesYesterday(0);
            } else {
                if (updateYesterday) {
                    crownEntity.setConfirmedYesterday(crownEntity.getConfirmedToday());
                    crownEntity.setDeathsYesterday(crownEntity.getDeathsToday());
                    crownEntity.setRecoveriesYesterday(crownEntity.getRecoveriesToday());
                }
                crownEntity.setConfirmedToday(region.getConfirmed());
                crownEntity.setDeathsToday(region.getDeaths());
                crownEntity.setRecoveriesToday(region.getRecoveries());
            }
            crownRepo.save(crownEntity);
        }

        // удаляем из базы регионы, которых нет в текущей выборке
        List<String> regions = crown.getRegions().stream().map(Crown.Region::getName).collect(Collectors.toList());
        crownEntities.values().stream().filter(c -> !regions.contains(c.getTerritory())).forEach(crownRepo::delete);

        settingService.saveParam(SettingName.CROWN_DAY.name(), now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
