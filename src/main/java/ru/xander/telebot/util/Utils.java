package ru.xander.telebot.util;

import ru.xander.telebot.dto.Request;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Random;

/**
 * @author Alexander Shakhov
 */
public abstract class Utils {

    private static final ZoneId ZONE_ID_MOSCOW = ZoneId.of("Europe/Moscow");
    private static final Random random = new Random(Long.MAX_VALUE);

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean randomBoolean() {
        return random.nextInt(100) % 2 == 0;
    }

    public static <T> T randomArray(T[] array) {
        int size = array.length;
        int index = random.nextInt(size);
        return array[index];
    }

    public static String randomUserMention(Request request) {
        Integer userId = request.getUserId();
        String userName = (request.getUserName() != null) && randomBoolean() ? request.getUserName() : request.getUserFullName();
        return String.format("[%s](tg://user?id=%d)", userName, userId);
    }

    public static boolean isHappyBirthDay() {
        LocalDate localDate = Instant.now().atZone(ZONE_ID_MOSCOW).toLocalDate();
        return (localDate.getMonth() == Month.FEBRUARY) && (localDate.getDayOfMonth() == 16);
    }

    public static boolean isHappyBirthNextDay() {
        LocalDate localDate = Instant.now().atZone(ZONE_ID_MOSCOW).toLocalDate();
        return (localDate.getMonth() == Month.FEBRUARY) && (localDate.getDayOfMonth() == 17);
    }

    public static String stackTraceToString(Throwable throwable) {
        try (
                StringWriter stringWriter = new StringWriter();
                PrintWriter writer = new PrintWriter(stringWriter)
        ) {
            throwable.printStackTrace(writer);
            return stringWriter.toString();
        } catch (IOException e) {
            return "getStackTrace exception: " + e.getMessage();
        }
    }
}
