package ru.xander.telebot.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.xander.telebot.entity.Ilushizm;

/**
 * @author Alexander Shakhov
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("/application-test.properties")
public class IlushizmRepoTest {

    @Autowired
    private IlushizmRepo ilushizmRepo;

    @Test
    public void findRandom() {
        Ilushizm ilushizm = ilushizmRepo.findRandom();
        Assert.assertNotNull(ilushizm);
        System.out.println(ilushizm);
    }

    @Test
    public void findByText() {
        ilushizmRepo.findByText("вагин").forEach(System.out::println);
    }
}