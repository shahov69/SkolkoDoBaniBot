package ru.xander.telebot.repository;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.xander.telebot.entity.CrownEntity;

import java.io.IOException;
import java.io.InputStream;

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
    public void save() throws IOException {
        InputStream flag = getClass().getResourceAsStream("/flag.png");

        CrownEntity entity = new CrownEntity();
        entity.setTerritory("Test");
        entity.setConfirmedToday(123);
        entity.setDeathsToday(10);
        entity.setRecoveriesToday(40);
        entity.setToday(true);
        entity.setFlag(IOUtils.toByteArray(flag));

        CrownEntity saved = crownRepo.save(entity);
        Assert.assertNotNull(saved);
        System.out.println(saved.getId());
    }
}