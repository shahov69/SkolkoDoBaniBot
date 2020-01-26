package ru.xander.telebot.shizm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.xander.telebot.util.Utils;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alexander Shakhov
 */
@Getter
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class Poster {

    private static final List<Poster> ilyaPosters;
    static final Poster defaultIlyaPoster;
    static final Poster defaultKiryaPoster;

    static {
        ilyaPosters = loadIlyaPosters();
        defaultIlyaPoster = createDefaultIlyaPoster();
        defaultKiryaPoster = createDefaultKiryaPoster();
    }

    private int bubbleTop;
    private int bubbleLeft;
    private int bubbleWidth;
    private int bubbleHeight;
    private int[] bubbleColor;
    private HorizontalAlign bubbleHAlign;
    private VerticalAlign bubbleVAlign;
    private int textPadding;
    private int[] textColor;
    private HorizontalAlign textAlign;
    private String resource;
    private boolean enabled;

    Poster bubbleTop(int bubbleTop) {
        Poster poster = copy();
        poster.bubbleTop = bubbleTop;
        return poster;
    }

    Poster bubbleLeft(int bubbleLeft) {
        Poster poster = copy();
        poster.bubbleLeft = bubbleLeft;
        return poster;
    }

    Poster bubbleWidth(int bubbleWidth) {
        Poster poster = copy();
        poster.bubbleWidth = bubbleWidth;
        return poster;
    }

    Poster bubbleHeight(int bubbleHeight) {
        Poster poster = copy();
        poster.bubbleHeight = bubbleHeight;
        return poster;
    }

    Poster bubbleColor(int[] bubbleColor) {
        Poster poster = copy();
        poster.bubbleColor = bubbleColor;
        return poster;
    }

    Poster bubbleHAlign(HorizontalAlign bubbleHAlign) {
        Poster poster = copy();
        poster.bubbleHAlign = bubbleHAlign;
        return poster;
    }

    Poster bubbleVAlign(VerticalAlign bubbleVAlign) {
        Poster poster = copy();
        poster.bubbleVAlign = bubbleVAlign;
        return poster;
    }

    Poster textPadding(int textPadding) {
        Poster poster = copy();
        poster.textPadding = textPadding;
        return poster;
    }

    Poster textAlign(HorizontalAlign textAlign) {
        Poster poster = copy();
        poster.textAlign = textAlign;
        return poster;
    }

    Poster textColor(int[] textColor) {
        Poster poster = copy();
        poster.textColor = textColor;
        return poster;
    }

    Poster resource(String resource) {
        Poster poster = copy();
        poster.resource = resource;
        return poster;
    }

    private Poster copy() {
        Poster poster = new Poster();
        poster.bubbleLeft = this.bubbleLeft;
        poster.bubbleTop = this.bubbleTop;
        poster.bubbleWidth = this.bubbleWidth;
        poster.bubbleHeight = this.bubbleHeight;
        poster.bubbleColor = this.bubbleColor;
        poster.bubbleHAlign = this.bubbleHAlign;
        poster.bubbleVAlign = this.bubbleVAlign;
        poster.textPadding = this.textPadding;
        poster.textColor = this.textColor;
        poster.textAlign = this.textAlign;
        poster.resource = this.resource;
        poster.enabled = this.enabled;
        return poster;
    }

    public static Poster getIlyaPoster(int index) {
        if ((index < 0) || (index >= ilyaPosters.size())) {
            log.warn("Index {} out of range", index);
            return defaultIlyaPoster;
        }
        return ilyaPosters.get(index);
    }

    public static Poster getRandomIlyaPoster() {
        return Utils.randomList(ilyaPosters);
    }

    public static Poster getKiryaPoster() {
        return defaultKiryaPoster;
    }

    private static List<Poster> loadIlyaPosters() {
        try (InputStream ilayPostersResource = Poster.class.getResourceAsStream("/media/ilya_posters.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(ilayPostersResource, new TypeReference<List<Poster>>() {
            }).stream().filter(Poster::isEnabled).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.singletonList(defaultIlyaPoster);
        }
    }

    private static Poster createDefaultIlyaPoster() {
        Poster poster = new Poster();
        poster.bubbleLeft = 30;
        poster.bubbleTop = 30;
        poster.bubbleWidth = 590;
        poster.bubbleHeight = 270;
        poster.bubbleColor = new int[]{255, 255, 255, 25};
        poster.bubbleHAlign = HorizontalAlign.CENTER;
        poster.bubbleVAlign = VerticalAlign.MIDDLE;
        poster.textPadding = 20;
        poster.textColor = new int[]{255, 255, 255};
        poster.textAlign = HorizontalAlign.CENTER;
        poster.resource = "/media/ilya_01.jpg";
        poster.enabled = true;
        return poster;
    }

    private static Poster createDefaultKiryaPoster() {
        Poster poster = new Poster();
        poster.bubbleLeft = 20;
        poster.bubbleTop = 430;
        poster.bubbleWidth = 410;
        poster.bubbleHeight = 190;
        poster.bubbleColor = new int[]{0, 0, 0, 100};
        poster.bubbleHAlign = HorizontalAlign.CENTER;
        poster.bubbleVAlign = VerticalAlign.MIDDLE;
        poster.textPadding = 20;
        poster.textColor = new int[]{255, 255, 255};
        poster.textAlign = HorizontalAlign.CENTER;
        poster.resource = "/media/kirya_01.jpg";
        poster.enabled = true;
        return poster;
    }
}
