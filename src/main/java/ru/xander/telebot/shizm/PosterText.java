package ru.xander.telebot.shizm;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor
class PosterText {
    private final int totalWidth;
    private final int totalHeight;
    private final int lineHeight;
    private final Collection<String> lines;
}
