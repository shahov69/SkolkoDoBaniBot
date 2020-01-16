package ru.xander.telebot.util;

import ru.xander.telebot.dto.Request;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Random;

/**
 * @author Alexander Shakhov
 */
public abstract class Utils {

    public static final ZoneId ZONE_ID_MOSCOW = ZoneId.of("Europe/Moscow");
    private static final Random random = new Random(Long.MAX_VALUE);

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean randomBoolean() {
        return random.nextInt(100) % 2 == 0;
    }

    public static int randomInt(int bound) {
        return random.nextInt(bound);
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

    public static Instant now() {
        return Instant.now(Clock.system(ZONE_ID_MOSCOW));
    }

    public static String formatBanyaTime(String template, long nanos) {
        long totalSeconds = nanos / 1_000_000_000;
        long seconds = totalSeconds % 60;
        long minutes = totalSeconds / 60 % 60;
        long hours = totalSeconds / 3600 % 24;
        long days = nanos / 1_000_000_000 / 3600 / 24;

        double inMicros = nanos / 1_000d;
        double inMillis = nanos / 1_000_000d;
        double inSeconds = nanos / 1_000_000_000d;
        double inMunites = nanos / 1_000_000_000d / 60d;
        double inHours = nanos / 1_000_000_000d / 3600d;
        double inDays = nanos / 1_000_000_000d / 3600d / 24d;

        return template
                .replace("${TIME}", String.format("%d:%02d:%02d:%02d", days, hours, minutes, seconds))
                .replace("${NANOS}", String.valueOf(nanos))
                .replace("${MICROS}", String.format("%.3f", inMicros))
                .replace("${MILLIS}", String.format("%.3f", inMillis))
                .replace("${SECONDS}", String.format("%.3f", inSeconds))
                .replace("${MINUTES}", String.format("%.2f", inMunites))
                .replace("${HOURS}", String.format("%.2f", inHours))
                .replace("${DAYS}", String.format("%.2f", inDays));
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
