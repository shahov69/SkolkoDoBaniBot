package ru.xander.telebot.dto;

import ru.xander.telebot.util.Utils;

import java.awt.*;

/**
 * @author Alexander Shakhov
 */
public enum Fonts {

    CAVEAT_REGULAR("/fonts/caveat_regular.ttf", 52.0f, 60.0f, 72.0f, false),
    DIMBO_ITALIC("/fonts/dimbo_italic.ttf", 40.0f, 48.0f, 60.0f, false),
    EFFLORESCE_BOLD_ITALIC("/fonts/effloresce_bold_iItalic.otf", 40.0f, 48.0f, 60.0f, false),
//    FREEHAND_575_BT("/fonts/freehand_575_bt.ttf", 60.0f, 60.0f, 60.0f, false),
//    HAVANA("/fonts/havana.ttf", 36.0f, 36.0f, 36.0f, false),
    NEW_STANDARD_TT("/fonts/new_standard_tt.ttf", 40.0f, 48.0f, 60.0f, false),
    NEWS_CYCLE("/fonts/news_cycle.ttf", 38.0f, 44.0f, 54.0f, false),
    POE_SANS_NEW("/fonts/poe_sans_new.otf", 38.0f, 44.0f, 54.0f, false),
//    RETROVILLE_NC("/fonts/retroville_nc.ttf", 36.0f, 36.0f, 36.0f, false),
    RETURN_TO_CLASSIC("/fonts/return_to_classic.otf", 36.0f, 42.0f, 50.0f, false),
    SNYDERSPEEDBRUSHCYR_REGULAR("/fonts/snyderspeedbrushcyr_regular.ttf", 36.0f, 42.0f, 50.0f, false),
    SOURCE_SANS_PRO("/fonts/source_sans_pro.ttf", 38.0f, 44.0f, 54.0f, false),
    TT2020BASE("/fonts/tt2020base.otf", 42.0f, 46.0f, 58.0f, true);

    private static final Fonts[] fonts = values();
    private final Font font;
    private final float smallSize;
    private final float mediumSize;
    private final float largeSize;
    private final boolean bold;

    Fonts(String fontResource, float smallSize, float mediumSize, float largeSize, boolean bold) {
        this.font = Utils.readResource(fontResource, in -> Font.createFont(Font.TRUETYPE_FONT, in));
        this.smallSize = smallSize;
        this.mediumSize = mediumSize;
        this.largeSize = largeSize;
        this.bold = bold;
    }

    public Font getSmallFont() {
        return getFont(smallSize);
    }

    public Font getMediumFont() {
        return getFont(mediumSize);
    }

    public Font getLargeFont() {
        return getFont(largeSize);
    }

    public Font getFont(float size) {
        if (bold) {
            return font.deriveFont(Font.BOLD, size);
        } else {
            return font.deriveFont(size);
        }
    }

    public static Fonts getRandom() {
        return Utils.randomArray(fonts);
    }
}
