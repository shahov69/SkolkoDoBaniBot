package ru.xander.telebot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xander.telebot.crown.Crown;
import ru.xander.telebot.crown.CrownInfo;
import ru.xander.telebot.crown.TerritoryInfo;
import ru.xander.telebot.dto.SettingName;
import ru.xander.telebot.entity.CrownEntity;
import ru.xander.telebot.repository.CrownRepo;
import ru.xander.telebot.util.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Alexander Shakhov
 */
@Service
@Slf4j
public class CrownService {

    private final CrownRepo crownRepo;
    private final SettingService settingService;

    @Autowired
    public CrownService(CrownRepo crownRepo, SettingService settingService) {
        this.crownRepo = crownRepo;
        this.settingService = settingService;
    }

    public void update(Crown crown) {
        LocalDate crownDay = settingService.getLocalDate(SettingName.CROWN_DAY);
        LocalDate now = LocalDate.now(Utils.ZONE_ID_MOSCOW);
        boolean updateYesterday = (crownDay != null) && (crownDay.compareTo(now) < 0);

        Map<String, CrownEntity> crownEntities = crownRepo.findAll().stream().collect(Collectors.toMap(CrownEntity::getTerritory, c -> c));

        for (Crown.Region region : crown.getRegions()) {
            CrownEntity crownEntity = crownEntities.get(region.getName());
            if (crownEntity == null) {
                crownEntity = new CrownEntity();
                crownEntity.setTerritory(region.getName());
                crownEntity.setConfirmedToday(region.getConfirmed() == null ? 0 : region.getConfirmed());
                crownEntity.setDeathsToday(region.getDeaths() == null ? 0 : region.getDeaths());
                crownEntity.setRecoveriesToday(region.getRecoveries() == null ? 0 : region.getRecoveries());
                crownEntity.setConfirmedYesterday(0);
                crownEntity.setDeathsYesterday(0);
                crownEntity.setRecoveriesYesterday(0);
                crownEntity.setFlag(imageToByteArray(region.getFlag()));
                crownEntity.setToday(true);
            } else {
                if (updateYesterday) {
                    crownEntity.setConfirmedYesterday(crownEntity.getConfirmedToday());
                    crownEntity.setDeathsYesterday(crownEntity.getDeathsToday());
                    crownEntity.setRecoveriesYesterday(crownEntity.getRecoveriesToday());
                    crownEntity.setToday(false);
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

        settingService.setLocalDate(SettingName.CROWN_DAY, now);
    }

    public CrownInfo getCrownInfo() {
        List<TerritoryInfo> territories = new ArrayList<>();
        List<CrownEntity> crownEntities = crownRepo.findAll();
        for (CrownEntity crownEntity : crownEntities) {
            TerritoryInfo territory = new TerritoryInfo();
            territory.setName(crownEntity.getTerritory());
            territory.setFlag(byteArrayToImage(crownEntity.getFlag()));
            territory.setConfirmed(crownEntity.getConfirmedToday());
            territory.setDeaths(crownEntity.getDeathsToday());
            territory.setRecoveries(crownEntity.getRecoveriesToday());
            territory.setConfirmedYesterday(crownEntity.getConfirmedYesterday());
            territory.setDeathsYesterday(crownEntity.getDeathsYesterday());
            territory.setRecoveriesYesterday(crownEntity.getRecoveriesYesterday());
            territory.setToday(crownEntity.getToday());
            territories.add(territory);
        }
        return new CrownInfo(territories);
    }

    private byte[] imageToByteArray(BufferedImage image) {
        if (image == null) {
            return null;
        }
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", output);
            return output.toByteArray();
        } catch (Exception e) {
            log.warn("Cannot convert image to byte array: {}", e.getMessage());
            return null;
        }
    }

    private BufferedImage byteArrayToImage(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            return ImageIO.read(input);
        } catch (Exception e) {
            log.warn("Cannot convert byte array to image: {}", e.getMessage());
            return null;
        }
    }
}
