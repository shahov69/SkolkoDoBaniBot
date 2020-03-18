package ru.xander.telebot.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.xander.telebot.entity.CrownEntity;

/**
 * @author Alexander Shakhov
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("/application-test.properties")
public class CrownRepoTest {

    @Autowired
    private CrownRepo crownRepo;

    @Test
    public void save() {
        CrownEntity entity = new CrownEntity();
        entity.setTerritory("Test");
        entity.setConfirmedToday(123);
        entity.setDeathsToday(10);
        entity.setRecoveriesToday(40);

        CrownEntity saved = crownRepo.save(entity);
        Assert.assertNotNull(saved);
        System.out.println(saved.getId());
    }
}