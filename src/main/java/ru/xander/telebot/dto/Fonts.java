package ru.xander.telebot.dto;

import ru.xander.telebot.util.Utils;

import java.awt.*;

/**
 * @author Alexander Shakhov
 */
public enum Fonts {

    CAVEAT_REGULAR("/fonts/caveat_regular.ttf", 52.0f),
    DIMBO_ITALIC("/fonts/dimbo_italic.ttf", 40.0f),
    EFFLORESCE_BOLD_ITALIC("/fonts/effloresce_bold_iItalic.otf", 40.0f),
//    FREEHAND_575_BT("/fonts/freehand_575_bt.ttf", 60.0f),
//    HAVANA("/fonts/havana.ttf", 36.0f),
    NEW_STANDARD_TT("/fonts/new_standard_tt.ttf", 40.0f),
    NEWS_CYCLE("/fonts/news_cycle.ttf", 38.0f),
    POE_SANS_NEW("/fonts/poe_sans_new.otf", 38.0f),
//    RETROVILLE_NC("/fonts/retroville_nc.ttf", 36.0f),
    RETURN_TO_CLASSIC("/fonts/return_to_classic.otf", 36.0f),
    SNYDERSPEEDBRUSHCYR_REGULAR("/fonts/snyderspeedbrushcyr_regular.ttf", 36.0f),
    SOURCE_SANS_PRO("/fonts/source_sans_pro.ttf", 38.0f),
    TT2020BASE("/fonts/tt2020base.otf", 38.0f);

    private static final Fonts[] fonts = values();
    private final Font font;
    private final float defaultSize;

    Fonts(String fontResource, float defaultSize) {
        this.font = Utils.readResource(fontResource, in -> Font.createFont(Font.TRUETYPE_FONT, in));
        this.defaultSize = defaultSize;
    }

    public Font getFont() {
        return font.deriveFont(defaultSize);
    }

    public static Fonts getRandom() {
        return Utils.randomArray(fonts);
    }
}
