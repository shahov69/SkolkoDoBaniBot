package ru.xander.telebot.crown;

import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Alexander Shakhov
 */
public class CrownRendererTest {
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

    @Ignore
    @Test
    public void render() throws IOException {
        CrownExtractor crownExtractor = new CrownExtractor();
        crownExtractor.setFlagExtractor(new TestFlagExtractor());
        Crown crown = crownExtractor.extract(getClass().getResourceAsStream("/crown_test.htm"));
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

    private static class TestFlagExtractor implements CrownExtractor.FlagExtractor {

        private final Image flag;

        TestFlagExtractor() throws IOException {
            flag = ImageIO.read(getClass().getResourceAsStream("/flag.png"));
        }

        @Override
        public Image extractFlag(Element flagElement) {
            return flag;
        }
    }
}