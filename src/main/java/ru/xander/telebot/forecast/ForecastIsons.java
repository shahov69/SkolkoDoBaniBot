package ru.xander.telebot.forecast;

import ru.xander.telebot.util.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Shakhov
 */
public final class ForecastIsons {

    private static final Map<Integer, String> iconResources;

    static {
        iconResources = new HashMap<>();
//        populateV1();
        populateV2();
    }

    private static void populateV1() {
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

    private static void populateV2() {
        iconResources.put(1, "/accuweather2/01.png");
        iconResources.put(2, "/accuweather2/02.png");
        iconResources.put(3, "/accuweather2/03.png");
        iconResources.put(4, "/accuweather2/04.png");
        iconResources.put(5, "/accuweather2/05.png");
        iconResources.put(6, "/accuweather2/06.png");
        iconResources.put(7, "/accuweather2/07.png");
        iconResources.put(8, "/accuweather2/08.png");
        iconResources.put(11, "/accuweather2/11.png");
        iconResources.put(12, "/accuweather2/12.png");
        iconResources.put(13, "/accuweather2/13.png");
        iconResources.put(14, "/accuweather2/14.png");
        iconResources.put(15, "/accuweather2/15.png");
        iconResources.put(16, "/accuweather2/16.png");
        iconResources.put(17, "/accuweather2/17.png");
        iconResources.put(18, "/accuweather2/18.png");
        iconResources.put(19, "/accuweather2/19.png");
        iconResources.put(20, "/accuweather2/20.png");
        iconResources.put(21, "/accuweather2/21.png");
        iconResources.put(22, "/accuweather2/22.png");
        iconResources.put(23, "/accuweather2/23.png");
        iconResources.put(24, "/accuweather2/24.png");
        iconResources.put(25, "/accuweather2/25.png");
        iconResources.put(26, "/accuweather2/26.png");
        iconResources.put(29, "/accuweather2/29.png");
        iconResources.put(30, "/accuweather2/30.png");
        iconResources.put(31, "/accuweather2/31.png");
        iconResources.put(32, "/accuweather2/32.png");
        iconResources.put(33, "/accuweather2/33.png");
        iconResources.put(34, "/accuweather2/34.png");
        iconResources.put(35, "/accuweather2/35.png");
        iconResources.put(36, "/accuweather2/36.png");
        iconResources.put(37, "/accuweather2/37.png");
        iconResources.put(38, "/accuweather2/38.png");
        iconResources.put(39, "/accuweather2/39.png");
        iconResources.put(40, "/accuweather2/40.png");
        iconResources.put(41, "/accuweather2/41.png");
        iconResources.put(42, "/accuweather2/42.png");
        iconResources.put(43, "/accuweather2/43.png");
        iconResources.put(44, "/accuweather2/44.png");
    }

    static Image loadIcon(int index, int defaultIndex) {
        String iconResource;
        if (iconResources.containsKey(index)) {
            iconResource = iconResources.get(index);
        } else {
            iconResource = iconResources.get(defaultIndex);
        }
        return Utils.readResource(iconResource, ImageIO::read);
    }

    private ForecastIsons() {
    }
}
