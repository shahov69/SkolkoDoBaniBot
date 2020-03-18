package ru.xander.telebot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.xander.telebot.crown.Crown;
import ru.xander.telebot.crown.CrownExtractor;
import ru.xander.telebot.crown.CrownInfo;
import ru.xander.telebot.crown.TestFlagExtractor;

import java.io.IOException;

/**
 * @author Alexander Shakhov
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("/application-test.properties")
@ComponentScan(value = {"ru.xander.telebot.service"})
public class CrownServiceTest {

    @Autowired
    private CrownService crownService;

    @Test
    @Rollback(false)
    public void update() throws IOException {
        CrownExtractor crownExtractor = new CrownExtractor();
        crownExtractor.setFlagExtractor(new TestFlagExtractor());
        Crown crown = crownExtractor.extract(getClass().getResourceAsStream("/crown_test.htm"));
        crownService.update(crown);
    }

    @Test
    public void getCrownInfo() {
        CrownInfo crownInfo = crownService.getCrownInfo();
        System.out.println("Total territories = " + crownInfo.getTotalTerritories());
        System.out.println("Total territories delta = " + crownInfo.getTotalTerritoriesDelta());
        System.out.println("Total confirmed = " + crownInfo.getTotalConfirmed());
        System.out.println("Total deaths = " + crownInfo.getTotalDeaths());
        System.out.println("Total recoveries = " + crownInfo.getTotalRecoveries());
        System.out.println("Total sick = " + crownInfo.getTotalSick());
        System.out.println("Total confirmed delta = " + crownInfo.getTotalConfirmedDelta());
        System.out.println("Total deaths delta = " + crownInfo.getTotalDeathsDelta());
        System.out.println("Total recoveries delta = " + crownInfo.getTotalRecoveriesDelta());
        System.out.println("Total sick delta = " + crownInfo.getTotalSickDelta());
    }
}