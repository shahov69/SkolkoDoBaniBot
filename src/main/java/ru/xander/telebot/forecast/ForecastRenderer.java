package ru.xander.telebot.forecast;

import ru.xander.telebot.dto.WeatherTexts;
import ru.xander.telebot.util.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Alexander Shakhov
 */
public class ForecastRenderer {

    private static final Color WHITE = new Color(255, 255, 255);
    private static final Color CYAN = new Color(0, 175, 190);
    private static final Color GRAY = new Color(121, 136, 149);
    private static final Color DARK = new Color(37, 56, 78);

    private static final int WIDTH = 425;
    private static final int HEIGHT = 247;
    private static final int HORZ_MID = WIDTH / 2;

    private static final String EMPTY_STRING = "";

    private static final Map<Integer, String> iconResources;

    static {
        iconResources = new HashMap<>();
        iconResources.put(1, "/accuweather/01-sunny.png");
        iconResources.put(2, "/accuweather/02-mostly_sunny.png");
        iconResources.put(3, "/accuweather/03-partly_sunny.png");
        iconResources.put(4, "/accuweather/04-intermittent_clouds.png");
        iconResources.put(5, "/accuweather/05-hazy_sunshine.png");
        iconResources.put(6, "/accuweather/06-mostly_cloudy.png");
        iconResources.put(7, "/accuweather/07-cloudy.png");
        iconResources.put(8, "/accuweather/08-dreary_overcast.png");
        iconResources.put(11, "/accuweather/11-fog.png");
        iconResources.put(12, "/accuweather/12-showers.png");
        iconResources.put(13, "/accuweather/13-mostly_cloudy_w_showers.png");
        iconResources.put(14, "/accuweather/14-partly_cloudy_w_showers.png");
        iconResources.put(15, "/accuweather/15-t_storms.png");
        iconResources.put(16, "/accuweather/16-mostly_cloudy_w_t_storms.png");
        iconResources.put(17, "/accuweather/17-partly_cloudy_w_t_storms.png");
        iconResources.put(18, "/accuweather/18-rain.png");
        iconResources.put(19, "/accuweather/19-flurries.png");
        iconResources.put(20, "/accuweather/20-mostly_cloudy_w_flurries.png");
        iconResources.put(21, "/accuweather/21-partly_cloudy_w_flurries.png");
        iconResources.put(22, "/accuweather/22-snow.png");
        iconResources.put(23, "/accuweather/23-mostly_cloudy_w_snow.png");
        iconResources.put(24, "/accuweather/24-ice.png");
        iconResources.put(25, "/accuweather/25-sleet.png");
        iconResources.put(26, "/accuweather/26-freezing_rain.png");
        iconResources.put(29, "/accuweather/29-rain_and_snow.png");
        iconResources.put(30, "/accuweather/30-hot.png");
        iconResources.put(31, "/accuweather/31-cold.png");
        iconResources.put(32, "/accuweather/32-windy.png");
        iconResources.put(33, "/accuweather/33-clear.png");
        iconResources.put(34, "/accuweather/34-mostly_clear.png");
        iconResources.put(35, "/accuweather/35-partly_cloudy.png");
        iconResources.put(36, "/accuweather/36-intermittent_clouds.png");
        iconResources.put(37, "/accuweather/37-hazy_moonlight.png");
        iconResources.put(38, "/accuweather/38-mostly_cloudy.png");
        iconResources.put(39, "/accuweather/39-partly_cloudy_w_showers.png");
        iconResources.put(40, "/accuweather/40-mostly_cloudy_w_showers.png");
        iconResources.put(41, "/accuweather/41-partly_cloudy_w_t_storms.png");
        iconResources.put(42, "/accuweather/42-mostly_cloudy_w_t_storms.png");
        iconResources.put(43, "/accuweather/43-mostly_cloudy_w_flurries.png");
        iconResources.put(44, "/accuweather/44-mostly_cloudy_w_snow.png");
    }

    public InputStream render(Forecast forecast, WeatherTexts texts) throws IOException {
        BufferedImage image = drawImage(forecast, texts);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private BufferedImage drawImage(Forecast forecast, WeatherTexts texts) {
        //TODO: брать погодку в соответствии с часовым поясом
        final DailyForecast dailyForecast = forecast.getDailyForecasts().get(0);
        final Sun sun = dailyForecast.getSun();
        final Conditions day = dailyForecast.getDay();
        final Conditions night = dailyForecast.getNight();
        final Collection<String> dayPhrase = Utils.getSplitPhrase(day.getShortPhrase(), 20);
        final Collection<String> nightPhrase = Utils.getSplitPhrase(night.getShortPhrase(), 20);
        final int phraseOffset = Math.max(dayPhrase.size() - 1, nightPhrase.size() - 1) * 20;
        final int trueWidth = WIDTH;
        final int trueHeight = HEIGHT + phraseOffset;
        final Font font = getFont(texts);

        BufferedImage image = new BufferedImage(trueWidth, trueHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics.setColor(WHITE);
        graphics.fill(new Rectangle(0, 0, trueWidth, trueHeight));

        graphics.setColor(CYAN);
        graphics.setFont(font.deriveFont(Font.BOLD, 14.0f));

        String ddMMMMM = dailyForecast.getDate().format(DateTimeFormatter.ofPattern("dd MMMM"));
        graphics.drawString(texts.getTitle() + ddMMMMM, 10, 23);

        graphics.setColor(DARK);
        graphics.setFont(font.deriveFont(Font.BOLD, 15.0f));
        graphics.drawString(texts.getDay(), 10, 50);
        graphics.drawString(texts.getNight(), HORZ_MID + 10, 50);

        graphics.setFont(font.deriveFont(26.0f));
        graphics.drawString(formatTemperature(dailyForecast.getTemperature().getMaximum()), 10, 82);
        graphics.drawString(formatTemperature(dailyForecast.getTemperature().getMinimum()), HORZ_MID + 10, 82);

        graphics.setFont(font.deriveFont(15.0f));
        AtomicInteger phraseY = new AtomicInteger(164);
        dayPhrase.forEach(s -> graphics.drawString(s, 10, phraseY.getAndAdd(20)));
        phraseY.set(164);
        nightPhrase.forEach(s -> graphics.drawString(s, HORZ_MID + 10, phraseY.getAndAdd(20)));
//        graphics.drawString(day.getShortPhrase(), 10, 167);
//        graphics.drawString(night.getShortPhrase(), HORZ_MID + 10, 164);

        graphics.setFont(font.deriveFont(15.0f));
        graphics.drawString(texts.getWind() + formatWind(day.getWind()), 10, 187 + phraseOffset);

        graphics.drawString(texts.getSunrise() + formatTime(sun.getRise()), 10, 210 + phraseOffset);
        graphics.drawString(texts.getUvIndex() + formatUVIndex(dailyForecast.getAirAndPollens()), 10, 233 + phraseOffset);
        graphics.drawString(texts.getWind() + formatWind(night.getWind()), HORZ_MID + 10, 187 + phraseOffset);
        graphics.drawString(texts.getSunset() + formatTime(sun.getSet()), HORZ_MID + 10, 210 + phraseOffset);

        graphics.setColor(GRAY);
        graphics.setFont(font.deriveFont(12.0f));

        FontMetrics fontMetrics = graphics.getFontMetrics();

        final String dayFeel = formatTemperature(dailyForecast.getRealFeelTemperature().getMaximum());
        final String dayPrecipitation = formatPrecipitation(day.getTotalLiquid());
        final String dayCloudy = formatPercent(day.getCloudCover());
        final String dayHumidity = formatPercent(day.getPrecipitationProbability());
        final String dayThunders = formatPercent(day.getThunderstormProbability());

        int feelWidth = fontMetrics.stringWidth(dayFeel);
        int precipitationWidth = fontMetrics.stringWidth(dayPrecipitation);
        int cloudWidth = fontMetrics.stringWidth(dayCloudy);
        int humidityWidth = fontMetrics.stringWidth(dayHumidity);
        int thundersWidth = fontMetrics.stringWidth(dayThunders);
        int maxWidth = Utils.max(feelWidth, precipitationWidth, cloudWidth, humidityWidth, thundersWidth);

        int condLeft = 94;
        int condTop = 70;

        graphics.drawString(texts.getFeel(), condLeft, condTop);
        graphics.drawString(dayFeel, 165 + maxWidth - feelWidth, condTop);
        graphics.drawString(texts.getPrecipitation(), condLeft, (condTop += 17));
        graphics.drawString(dayPrecipitation, 165 + maxWidth - precipitationWidth, condTop);
        if (!dayCloudy.isEmpty()) {
            graphics.drawString(texts.getCloudy(), condLeft, (condTop += 17));
            graphics.drawString(dayCloudy, 165 + maxWidth - cloudWidth, condTop);
        }
        if (!dayHumidity.isEmpty()) {
            graphics.drawString(texts.getHumidity(), condLeft, (condTop += 17));
            graphics.drawString(dayHumidity, 165 + maxWidth - humidityWidth, condTop);
        }
        if (!dayThunders.isEmpty()) {
            graphics.drawString(texts.getThunders(), condLeft, (condTop += 17));
            graphics.drawString(dayThunders, 165 + maxWidth - thundersWidth, condTop);
        }

        final String nightFeel = formatTemperature(dailyForecast.getRealFeelTemperature().getMinimum());
        final String nightPrecipitation = formatPrecipitation(night.getTotalLiquid());
        final String nightCloudy = formatPercent(night.getCloudCover());
        final String nightHumidity = formatPercent(night.getPrecipitationProbability());
        final String nightThunders = formatPercent(night.getThunderstormProbability());

        feelWidth = fontMetrics.stringWidth(nightFeel);
        precipitationWidth = fontMetrics.stringWidth(nightPrecipitation);
        cloudWidth = fontMetrics.stringWidth(nightCloudy);
        humidityWidth = fontMetrics.stringWidth(nightHumidity);
        thundersWidth = fontMetrics.stringWidth(nightThunders);
        maxWidth = Utils.max(feelWidth, precipitationWidth, cloudWidth, humidityWidth, thundersWidth);

        condLeft = HORZ_MID + 94;
        condTop = 70;

        graphics.drawString(texts.getFeel(), condLeft, condTop);
        graphics.drawString(nightFeel, HORZ_MID + 165 + maxWidth - feelWidth, condTop);
        graphics.drawString(texts.getPrecipitation(), condLeft, (condTop += 17));
        graphics.drawString(nightPrecipitation, HORZ_MID + 165 + maxWidth - precipitationWidth, condTop);
        if (!nightCloudy.isEmpty()) {
            graphics.drawString(texts.getCloudy(), condLeft, (condTop += 17));
            graphics.drawString(nightCloudy, HORZ_MID + 165 + maxWidth - cloudWidth, condTop);
        }
        if (!nightHumidity.isEmpty()) {
            graphics.drawString(texts.getHumidity(), condLeft, (condTop += 17));
            graphics.drawString(nightHumidity, HORZ_MID + 165 + maxWidth - humidityWidth, condTop);
        }
        if (!nightThunders.isEmpty()) {
            graphics.drawString(texts.getThunders(), condLeft, (condTop += 17));
            graphics.drawString(nightThunders, HORZ_MID + 165 + maxWidth - thundersWidth, condTop);
        }

        graphics.setColor(GRAY);
        graphics.fill(new Rectangle(10, 93, 77, 47));
        graphics.fill(new Rectangle(HORZ_MID + 10, 93, 77, 47));
        graphics.drawImage(loadIcon(day.getIcon(), 1), 11, 94, 75, 45, WHITE, null);
        graphics.drawImage(loadIcon(night.getIcon(), 33), HORZ_MID + 11, 94, 75, 45, WHITE, null);

        graphics.setColor(CYAN);
        graphics.drawLine(0, trueHeight - 1, trueWidth, trueHeight - 1);

        graphics.dispose();

        return image;
    }

    private static String formatTemperature(Value temperature) {
        return String.format("%.1f° %s",
                temperature.getValue(),
                temperature.getUnit());
    }

    private static String formatWind(Wind wind) {
        return String.format("%s %.1f %s",
                wind.getDirection().getLocalized(),
                wind.getSpeed().getValue(),
                wind.getSpeed().getUnit());
    }

    private static String formatPrecipitation(Value precipitation) {
        return String.format("%.1f %s", precipitation.getValue(), precipitation.getUnit());
    }

    private static String formatTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    private static String formatPercent(Integer percent) {
        if (percent == null) {
            return EMPTY_STRING;
        }
        return String.format("%d %%", percent);
    }

    private static String formatUVIndex(Collection<AirAndPollen> airAndPollenList) {
        Optional<AirAndPollen> uvIndex = airAndPollenList.stream().filter(a -> a.getName().equalsIgnoreCase("UVIndex")).findFirst();
        return uvIndex.map(airAndPollen -> airAndPollen.getCategoryValue() + " " + airAndPollen.getCategory()).orElse("");
    }

    private static Font getFont(WeatherTexts texts) {
        if ("JP".equalsIgnoreCase(texts.getLocale())) {
            return Utils.readResource("/fonts/msgothic.ttc", inputStream -> Font.createFont(Font.TRUETYPE_FONT, inputStream));
        } else {
            return Utils.readResource("/fonts/segoeui.ttf", inputStream -> Font.createFont(Font.TRUETYPE_FONT, inputStream));
        }
    }

    private static Image loadIcon(int index, int defaultIndex) {
        String iconResource;
        if (iconResources.containsKey(index)) {
            iconResource = iconResources.get(index);
        } else {
            iconResource = iconResources.get(defaultIndex);
        }
        return Utils.readResource(iconResource, ImageIO::read);
    }
}
