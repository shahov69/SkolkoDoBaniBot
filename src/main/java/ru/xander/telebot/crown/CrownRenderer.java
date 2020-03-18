package ru.xander.telebot.crown;

import ru.xander.telebot.dto.Fonts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * @author Alexander Shakhov
 */
public class CrownRenderer {

    public static final int DEFAULT_CROWN_LIMIT = 50;
    private static final DecimalFormat format;
    private static final int rowHeight = 22;
    private static final int[] cols;

    static {
        format = new DecimalFormat("#,###");
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' ');
        format.setDecimalFormatSymbols(symbols);
        cols = new int[7];
        cols[0] = 0;
        cols[1] = cols[0] + 50;
        cols[2] = cols[1] + 175;
        cols[3] = cols[2] + 75;
        cols[4] = cols[3] + 75;
        cols[5] = cols[4] + 75;
        cols[6] = cols[5] + 75;
    }

    private int visibleRows = DEFAULT_CROWN_LIMIT;

    public void setVisibleRows(int visibleRows) {
        this.visibleRows = Math.max(visibleRows, 10);
    }

    public InputStream render(Crown crown, int offset) {
        try {
            final int totalTerritories = crown.getTotalTerritories();
            Range range = calcRange(totalTerritories, visibleRows, offset);

            final int rows = range.end - range.start
                    + 4 // + 4 строки (заголовок, тотал, подписи внизу)
                    + (range.start > 0 ? 1 : 0) // + 1 строка (многоточия сверху)
                    + (range.end < (totalTerritories - 1) ? 1 : 0); // + 1 строка (многоточия снизу)

            final int width = cols[cols.length - 1] + 1;
            final int height = 22 * rows + 1;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            drawCrownTable(graphics, crown, width, height, range);

            graphics.dispose();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Cannot render forecast: " + e.getMessage(), e);
        }
    }

    private void drawCrownTable(Graphics2D graphics, Crown crown, int width, int height, Range range) {
        drawCanvas(graphics, width, height);
        drawGrid(graphics, width, height);
        drawTitles(graphics, crown, height);

        graphics.setFont(Fonts.NEWS_CYCLE.getFont(14.0f));
        graphics.setColor(Color.BLACK);

        final int totalTerritories = crown.getTotalTerritories();

        int startRow = 3;
        if (range.start > 0) {
            startRow += 1;
            int rowY = rowHeight * 3 - 5;
            for (int i = 1; i < cols.length; i++) {
                drawText(graphics, "...", cols[i - 1], cols[i], rowY, Alignment.CENTER);
            }
        }
        if (range.end < (totalTerritories - 1)) {
            int rowY = height - rowHeight * 2 - 6;
            for (int i = 1; i < cols.length; i++) {
                drawText(graphics, "...", cols[i - 1], cols[i], rowY, Alignment.CENTER);
            }
        }

        for (int row = range.start; row < range.end; row++) {
            Crown.Region region = crown.getRegions().get(row);

            int rowY = startRow * rowHeight - 5;
            drawText(graphics, "" + (row + 1), cols[0], cols[1], rowY, Alignment.CENTER);
            drawText(graphics, region.getName(), cols[1], cols[2], rowY, Alignment.LEFT);
            graphics.drawImage(region.getFlag(), cols[2] - 26, rowY - 13, 23, 15, Color.WHITE, null);
            drawText(graphics, formatInteger(region.getConfirmed(), true), cols[2], cols[3], rowY, Alignment.RIGHT);
            drawText(graphics, formatInteger(region.getDeaths(), true), cols[3], cols[4], rowY, Alignment.RIGHT);
            drawText(graphics, formatInteger(region.getRecoveries(), true), cols[4], cols[5], rowY, Alignment.RIGHT);
            drawText(graphics, formatInteger(region.getSick(), false), cols[5], cols[6], rowY, Alignment.RIGHT);
            startRow++;
        }
    }

    private void drawCanvas(Graphics2D graphics, int width, int height) {
        // раскрашиваем общий фон
        graphics.setColor(new Color(0xf8, 0xf9, 0xfa));
        graphics.fill(new Rectangle(0, 0, width, height));

        // раскрашиваем служебные строки: 2 сверху и 2 снизу
        graphics.setColor(new Color(0xea, 0xec, 0xf0));
        graphics.fill(new Rectangle(0, 0, width, rowHeight * 2 + 1));
        graphics.fill(new Rectangle(0, height - rowHeight * 2 - 1, width, height));
    }

    private void drawGrid(Graphics2D graphics, int width, int height) {
        graphics.setColor(new Color(0xa2, 0xa9, 0xb1));

        // общая рамка
        graphics.drawRect(0, 0, width - 1, height - 1);

        int lastRow = height - 2 * rowHeight;

        // горизонтальные линии
        int lineY = rowHeight;
        while (lineY < lastRow) {
            graphics.drawLine(0, lineY, width, lineY);
            lineY += rowHeight;
        }

        // вертикальные линии
        for (int i = 1; i < (cols.length - 1); i++) {
            graphics.drawLine(cols[i], 0, cols[i], lastRow - 2);
        }
    }

    private void drawTitles(Graphics2D graphics, Crown crown, int height) {
        graphics.setFont(Fonts.NEWS_CYCLE.getMediumFont().deriveFont(Font.BOLD, 14.0f));
        graphics.setColor(Color.BLACK);

        final int titleRowY = 17;
        drawText(graphics, "#", cols[0], cols[1], titleRowY, Alignment.LEFT);
        drawText(graphics, "Территории", cols[1], cols[2], titleRowY, Alignment.LEFT);
        drawText(graphics, "Заражено", cols[2], cols[3], titleRowY, Alignment.LEFT);
        drawText(graphics, "Умерло", cols[3], cols[4], titleRowY, Alignment.LEFT);
        drawText(graphics, "Вылечено", cols[4], cols[5], titleRowY, Alignment.LEFT);
        drawText(graphics, "Болеют", cols[5], cols[6], titleRowY, Alignment.LEFT);

        final int totalRowY = rowHeight + 17;
        drawText(graphics, "Всего", cols[0], cols[1], totalRowY, Alignment.LEFT);
        drawText(graphics, formatInteger(crown.getTotalTerritories(), false), cols[1], cols[2], totalRowY, Alignment.LEFT);
        drawText(graphics, formatInteger(crown.getTotalConfirmed(), false), cols[2], cols[3], totalRowY, Alignment.RIGHT);
        drawText(graphics, formatInteger(crown.getTotalDeaths(), false), cols[3], cols[4], totalRowY, Alignment.RIGHT);
        drawText(graphics, formatInteger(crown.getTotalRecoveries(), false), cols[4], cols[5], totalRowY, Alignment.RIGHT);
        drawText(graphics, formatInteger(crown.getTotalSick(), false), cols[5], cols[6], totalRowY, Alignment.RIGHT);

        drawText(graphics, String.format("Текущая смертность = %.2f%%", crown.getCurrentMortality()),
                cols[0], cols[6], height - rowHeight - 6, Alignment.LEFT);
        drawText(graphics, String.format("Виртуальная смертность = %.2f%%", crown.getVirtualMortality()),
                cols[0], cols[6], height - 6, Alignment.LEFT);
    }

    private void drawText(Graphics2D graphics, String value, int x1, int x2, int y, Alignment alignment) {
        if (alignment == Alignment.LEFT) {
            graphics.drawString(value, x1 + 4, y);
        } else {
            FontMetrics fontMetrics = graphics.getFontMetrics();
            int width = fontMetrics.stringWidth(value);
            if (alignment == Alignment.CENTER) {
                int x = x1 + (x2 - x1 - width) / 2;
                graphics.drawString(value, x, y);
            } else if (alignment == Alignment.RIGHT) {
                int x = x2 - width - 4;
                graphics.drawString(value, x, y);
            }
        }
    }

    private static String formatInteger(Integer integer, boolean skipZero) {
        if (integer == null) {
            return "###";
        }
        if (skipZero && (integer <= 0)) {
            return "";
        }
        return format.format(integer);
    }

    /**
     * Высчиляет количество отображаемых строк, с учетом смещения.
     */
    static Range calcRange(int totalTerritories, int visibleRows, int offset) {
        // если территорий меньше 50, выводим их все не зависимо от смещения.
        if (totalTerritories <= visibleRows) {
            return new Range(0, totalTerritories);
        }
        // если указано смещение меньше 1, то выводим все территории
        if (offset < 1) {
            return new Range(0, totalTerritories);
        }
        // если выводим территории, начиная с первой, то нужно 50 первых строк
        if (offset == 1) {
            return new Range(0, visibleRows);
        }
        // если с учетом смещения остается меньше 50 территорий, то выводим посление 50
        if ((totalTerritories - offset) < visibleRows) {
            return new Range(totalTerritories - visibleRows, totalTerritories);
        }
        // 50 строк для территорий
        return new Range(offset - 1, offset + visibleRows - 1);
    }

    static class Range {
        final int start;
        final int end;

        private Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    enum Alignment {
        LEFT,
        CENTER,
        RIGHT
    }
}
