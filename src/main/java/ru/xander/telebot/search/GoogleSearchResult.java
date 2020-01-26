package ru.xander.telebot.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;

/**
 * @author Alexander Shakhov
 */
@Getter
@AllArgsConstructor
public class GoogleSearchResult {
    private final ContentType contentType;
    private final InputStream content;

    public enum ContentType {
        JPG,
        GIF,
        PNG,
        BMP,
        MP4
    }
}
