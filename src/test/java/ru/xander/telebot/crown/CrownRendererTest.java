package ru.xander.telebot.crown;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.xander.telebot.service.CrownService;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Alexander Shakhov
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("/application-test.properties")
@ComponentScan(value = {"ru.xander.telebot.service"})
public class CrownRendererTest {

    @Autowired
    private CrownService crownService;

    @Test
    public void calcRange() {
        assertRange(CrownRenderer.calcRange(10, 50, -1), 0, 10);
        assertRange(CrownRenderer.calcRange(10, 50, 1), 0, 10);
        assertRange(CrownRenderer.calcRange(10, 50, 2), 0, 10);
        assertRange(CrownRenderer.calcRange(10, 50, 100), 0, 10);

        assertRange(CrownRenderer.calcRange(70, 50, -1), 0, 70);
        assertRange(CrownRenderer.calcRange(70, 50, 1), 0, 50);
        assertRange(CrownRenderer.calcRange(70, 50, 2), 1, 51);
        assertRange(CrownRenderer.calcRange(70, 50, 100), 20, 70);
    }

    @Test
    public void render() throws IOException {
        CrownInfo crown = crownService.getCrownInfo();
        System.out.println("Draw with offset 0");
        CrownRenderer renderer = new CrownRenderer();
        renderer.setVisibleRows(50);
        IOUtils.copy(renderer.render(crown, -1), new FileOutputStream("d:\\Sources\\.temp\\crown_0.png"));
        System.out.println("Draw with offset 1");
        IOUtils.copy(renderer.render(crown, 1), new FileOutputStream("d:\\Sources\\.temp\\crown_1.png"));
        System.out.println("Draw with offset 2");
        IOUtils.copy(renderer.render(crown, 2), new FileOutputStream("d:\\Sources\\.temp\\crown_2.png"));
        System.out.println("Draw with offset 10");
        IOUtils.copy(renderer.render(crown, 10), new FileOutputStream("d:\\Sources\\.temp\\crown_10.png"));
        System.out.println("Draw with offset 50");
        IOUtils.copy(renderer.render(crown, 50), new FileOutputStream("d:\\Sources\\.temp\\crown_50.png"));
        System.out.println("Draw with offset 1000");
        IOUtils.copy(renderer.render(crown, 1000), new FileOutputStream("d:\\Sources\\.temp\\crown_1000.png"));
    }

    private void assertRange(CrownRenderer.Range range, int start, int end) {
        Assert.assertEquals(start, range.start);
        Assert.assertEquals(end, range.end);
    }
}