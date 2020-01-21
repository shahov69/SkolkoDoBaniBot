package ru.xander.telebot.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Alexander Shakhov
 */
public class UtilsTest {
    @Test
    public void getSplitPhrase() {
        final String s = "Временами снег и порывистый ветер";
        Assert.assertEquals(Arrays.asList("Временами снег и", "порывистый ветер"), Utils.getSplitPhrase(s, 20));
    }
}