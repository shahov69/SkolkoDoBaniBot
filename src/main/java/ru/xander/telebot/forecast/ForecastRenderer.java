package ru.xander.telebot.forecast;

import ru.xander.telebot.dto.Fonts;
import ru.xander.telebot.util.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Alexander Shakhov
 */
public class ForecastRenderer {

    private static final Color WHITE = new Color(255, 255, 255);
    private static final Color CYAN = new Color(0, 175, 190);
    private static final Color GRAY = new Color(70, 80, 100);
    private static final Color DARK = new Color(27, 46, 68);

    private static final int WIDTH = 425;
    private static final int HEIGHT = 218;
    private static final int HORZ_MID = (int) (WIDTH / 2.0d) - 5;

    private static final DateTimeFormatter MMDD = DateTimeFormatter.ofPattern("MMDD");
    private static final DateTimeFormatter HH_MM = DateTimeFormatter.ofPattern("HH:mm");

    public InputStream render(Forecast forecast) {
        try {
            BufferedImage image = drawImage(forecast);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Cannot render forecast: " + e.getMessage(), e);
        }
    }

    private BufferedImage drawImage(Forecast forecast) {
        final DailyForecast dailyForecast = getDailyForecast(forecast);
        final Sun sun = dailyForecast.getSun();
        final Conditions day = dailyForecast.getDay();
        final Conditions night = dailyForecast.getNight();
        final Collection<String> dayPhrase = splitPhrase(day.getShortPhrase());
        final Collection<String> nightPhrase = splitPhrase(night.getShortPhrase());
        final int phraseOffset = Math.max(dayPhrase.size() - 1, nightPhrase.size() - 1) * 20;
        final int trueWidth = WIDTH;
        final int trueHeight = HEIGHT + phraseOffset;
        final Font font = Fonts.NEWS_CYCLE.getFont();

        BufferedImage image = new BufferedImage(trueWidth, trueHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics.setColor(WHITE);
        graphics.fill(new Rectangle(0, 0, trueWidth, trueHeight));

        graphics.setColor(CYAN);
        graphics.setFont(font.deriveFont(Font.BOLD, 14.0f));

        graphics.drawString("погодка на " + Utils.formatRusDay(dailyForecast.getDate()), 10, 23);

        graphics.setColor(DARK);
        graphics.setFont(font.deriveFont(Font.BOLD, 15.0f));
        graphics.drawString("ДЕНЬ", 10, 50);
        graphics.drawString("НОЧЬ", HORZ_MID + 10, 50);

        // основная температура
        graphics.setFont(font.deriveFont(26.0f));
        graphics.drawString(formatTemperature(dailyForecast.getTemperature().getMaximum()), 10, 82);
        graphics.drawString(formatTemperature(dailyForecast.getTemperature().getMinimum()), HORZ_MID + 10, 82);

        // картинки
        graphics.setColor(GRAY);
        graphics.fill(new Rectangle(10, 91, 77, 77));
        graphics.fill(new Rectangle(HORZ_MID + 10, 91, 77, 77));
        graphics.drawImage(ForecastIsons.loadIcon(day.getIcon(), 1), 11, 92, 75, 75, WHITE, null);
        graphics.drawImage(ForecastIsons.loadIcon(night.getIcon(), 33), HORZ_MID + 11, 92, 75, 75, WHITE, null);

        // фразы
        graphics.setColor(DARK);
        graphics.setFont(font.deriveFont(14.0f));
        int phraseY = 188;
        for (String phrase : dayPhrase) {
            graphics.drawString(phrase, 10, phraseY);
            phraseY += 20;
        }
        phraseY = 188;
        for (String phrase : nightPhrase) {
            graphics.drawString(phrase, HORZ_MID + 10, phraseY);
            phraseY += 20;
        }

        // УФ-индекс
        graphics.setFont(font.deriveFont(14.0f));
        graphics.drawString("Уровень ультрафиолета " + formatUVIndex(dailyForecast.getAirAndPollens()), 10, trueHeight - 12);

        // условия
        graphics.setColor(GRAY);
        graphics.setFont(font.deriveFont(12.0f));

        Map<String, String> dayConditions = new LinkedHashMap<>();
        dayConditions.put("ощущается", formatFeelTemp(dailyForecast.getRealFeelTemperature().getMaximum()));
        dayConditions.put("осадки", formatPrecipitation(day.getTotalLiquid()));
        dayConditions.put("облачность", formatPercent(day.getCloudCover()));
        dayConditions.put("влажность", formatPercent(day.getPrecipitationProbability()));
        dayConditions.put("гроза", formatPercent(day.getThunderstormProbability()));
        dayConditions.put("ветер", formatWind(day.getWind()));
        dayConditions.put("рассвет", formatSunRiseSet(sun.getRise()));

        drawConditions(graphics, dayConditions, 94, 65);

        Map<String, String> nightConditions = new LinkedHashMap<>();
        nightConditions.put("ощущается", formatFeelTemp(dailyForecast.getRealFeelTemperature().getMinimum()));
        nightConditions.put("осадки", formatPrecipitation(night.getTotalLiquid()));
        nightConditions.put("облачность", formatPercent(night.getCloudCover()));
        nightConditions.put("влажность", formatPercent(night.getPrecipitationProbability()));
        nightConditions.put("гроза", formatPercent(night.getThunderstormProbability()));
        nightConditions.put("ветер", formatWind(night.getWind()));
        nightConditions.put("закат", formatSunRiseSet(sun.getSet()));

        drawConditions(graphics, nightConditions, HORZ_MID + 94, 65);

        graphics.setColor(CYAN);
        graphics.drawLine(0, trueHeight - 1, trueWidth, trueHeight - 1);

        graphics.dispose();

        return image;
    }

    private DailyForecast getDailyForecast(Forecast forecast) {
        final String now = LocalDateTime.now(Utils.ZONE_ID_MOSCOW).format(MMDD);
        for (DailyForecast dailyForecast : forecast.getDailyForecasts()) {
            String date = dailyForecast.getDate().format(MMDD);
            if (date.equals(now)) {
                return dailyForecast;
            }
        }
        return forecast.getDailyForecasts().get(0);
    }

    private void drawConditions(Graphics2D graphics, Map<String, String> conditions, int left, int top) {
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int condTop = top;
        for (Map.Entry<String, String> condition : conditions.entrySet()) {
            int condWidth = fontMetrics.stringWidth(condition.getValue());
            graphics.drawString(condition.getKey(), left, condTop);
            graphics.drawString(condition.getValue(), left + 110 - condWidth, condTop);
            condTop += 17;
        }
    }

    static List<String> splitPhrase(String phrase) {
        List<String> result = new LinkedList<>();
        int space = phrase.indexOf(' ');
        int last = 0;
        StringBuilder sub = new StringBuilder();
        while (space > -1) {
            int len = sub.length();
            if ((len > 0) && ((len + space - last + 1) > 20)) {
                result.add(sub.toString());
                sub.setLength(0);
            }
            if (sub.length() > 0) {
                sub.append(' ');
            }
            sub.append(phrase, last, space);
            last = space + 1;
            space = phrase.indexOf(' ', last);
        }
        if (sub.length() > 0) {
            sub.append(' ');
        }
        sub.append(phrase, last, phrase.length());
        result.add(sub.toString());
        return result;
    }

    private static String formatTemperature(Value temperature) {
        return String.format("%.1f° %s",
                temperature.getValue(),
                temperature.getUnit());
    }

    private static String formatFeelTemp(Value temperature) {
        return String.format("%.0f° %s",
                temperature.getValue(),
                temperature.getUnit());
    }

    private static String formatWind(Wind wind) {
        return String.format("%s %.0f %s",
                wind.getDirection().getLocalized(),
                wind.getSpeed().getValue(),
                wind.getSpeed().getUnit());
    }

    private static String formatPrecipitation(Value precipitation) {
        return String.format("%.1f %s",
                precipitation.getValue(),
                precipitation.getUnit());
    }

    private static String formatSunRiseSet(LocalDateTime time) {
        return time.format(HH_MM);
    }

    private static String formatPercent(Integer percent) {
        if (percent == null) {
            return Utils.EMPTY_STRING;
        }
        return String.format("%d %%", percent);
    }

    private static String formatUVIndex(Collection<AirAndPollen> airAndPollenList) {
        Optional<AirAndPollen> uvIndex = airAndPollenList.stream().filter(a -> a.getName().equalsIgnoreCase("UVIndex")).findFirst();
        return uvIndex.map(airAndPollen -> airAndPollen.getCategoryValue() + " " + airAndPollen.getCategory()).orElse("");
    }


}
