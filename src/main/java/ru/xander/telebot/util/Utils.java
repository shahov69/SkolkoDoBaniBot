package ru.xander.telebot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.dto.TimeOfDay;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Consumer;

/**
 * @author Alexander Shakhov
 */
public abstract class Utils {

    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final ZoneId ZONE_ID_MOSCOW = ZoneId.of("Europe/Moscow");
    private static final Locale LOCALE_RU = Locale.forLanguageTag("RU");
    private static final Random random = new Random(Long.MAX_VALUE);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String[] slavMonths = { "",
            "січня", "лютого", "березня",
            "квітня", "травня", "червня",
            "липня", "серпня", "вересня",
            "жовтня", "листопада", "грудня"
    };

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

    public static <T> T randomList(List<T> list) {
        int size = list.size();
        int index = random.nextInt(size);
        return list.get(index);
    }

    public static String randomUserMention(Request request) {
        Integer userId = request.getUserId();
        String userName = (request.getUserName() != null) && randomBoolean() ? request.getUserName() : request.getUserFullName();
        return String.format("[%s](tg://user?id=%d)", userName, userId);
    }

    public static int max(int first, int... others) {
        int max = first;
        for (int i = 0; i < others.length; i++) {
            max = Math.max(max, others[i]);
        }
        return max;
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

    public static Instant parseDate(String date, String format) {
        return Instant.from(DateTimeFormatter.ofPattern(format).withZone(ZONE_ID_MOSCOW).parse(date));
    }

    public static int getDayId() {
        return getDayId(LocalDate.now(ZONE_ID_MOSCOW));
    }

    public static int getDayId(LocalDate localDate) {
        return localDate.getMonthValue() * 100 + localDate.getDayOfMonth();
    }

    public static String formatDate(TemporalAccessor date, String format) {
        return DateTimeFormatter
                .ofPattern(format)
                .withLocale(LOCALE_RU)
                .withZone(ZONE_ID_MOSCOW)
                .format(date);
    }

    public static String formatSlavDay(int month, int day) {
        return day + "-е " + slavMonths[month];
    }

    public static <T> T parseJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static TimeOfDay getTimeOfDay() {
        return getTimeOfDay(now());
    }

    public static TimeOfDay getTimeOfDay(Instant instant) {
        int hour = LocalDateTime.ofInstant(instant, ZONE_ID_MOSCOW).getHour();
        if (hour < 6) {
            return TimeOfDay.NIGHT;
        } else if (hour < 12) {
            return TimeOfDay.MORNING;
        } else if (hour < 18) {
            return TimeOfDay.AFTERNOON;
        } else {
            return TimeOfDay.EVENING;
        }
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

    public static List<String> getSplitPhrase(String phrase, int maxLength) {
        List<String> result = new LinkedList<>();
        int space = phrase.indexOf(' ');
        int last = 0;
        StringBuilder sub = new StringBuilder();
        while (space > -1) {
            int len = sub.length();
            if ((len > 0) && ((len + space - last + 1) > maxLength)) {
                result.add(sub.toString());
                sub.setLength(0);
            }
            if (sub.length() > 0) {
                sub.append(' ');
            }
            sub.append(phrase, last, space);
            last = space + 1;
            space = phrase.indexOf(' ', last);
        }
        if (sub.length() > 0) {
            sub.append(' ');
        }
        sub.append(phrase, last, phrase.length());
        result.add(sub.toString());
        return result;
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

    public static void tryWithResource(String resourceName, Consumer<InputStream> resourceConsumer) {
        try (InputStream resource = Utils.class.getResourceAsStream(resourceName)) {
            resourceConsumer.accept(resource);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> T readResource(String resourceName, ResourceReader<T> resourceReader) {
        try (InputStream resource = Utils.class.getResourceAsStream(resourceName)) {
            return resourceReader.apply(resource);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public interface ResourceReader<T> {
        T apply(InputStream resource) throws Exception;
    }
}
