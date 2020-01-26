package ru.xander.telebot.action;

import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.MessageMode;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.util.Sender;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Alexander Shakhov
 */
@Component
public class AdminSystemAction implements Action {
    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            @SuppressWarnings("StringBufferReplaceableByString") final StringBuilder text = new StringBuilder()

                    .append("<b>Default params:</b>\n\n")
                    .append("<code>now instant</code> = ").append(Instant.now()).append("\n")
                    .append("<code>now localdatetime</code> = ").append(LocalDateTime.now()).append("\n")
                    .append("<code>now localdate</code> = ").append(LocalDate.now()).append("\n")
                    .append("<code>now localtime</code> = ").append(LocalTime.now()).append("\n")
                    .append("<code>now date</code> = ").append(new Date(System.currentTimeMillis())).append("\n")
                    .append("<code>now timestamp</code> = ").append(new Timestamp(System.currentTimeMillis())).append("\n")
                    .append("<code>charset</code> = ").append(Charset.defaultCharset()).append("\n")
                    .append("<code>locale</code> = ").append(Locale.getDefault().getDisplayName()).append("\n")
                    .append("<code>timezone</code> = ").append(TimeZone.getDefault().getDisplayName()).append("\n")
                    .append("<code>telegram api version</code> = 4.1.2\n")
                    .append("<code>java version</code> = ").append(System.getProperty("java.version")).append("\n")
                    //TODO
//                    .append("<code>ilushizm count</code> = ").append(getIlushizmCount()).append("\n")
//                    .append("<code>kirushizm count</code> = ").appendgetKirushizmCount()).append("\n")

                    .append("\n<b>System action:</b>\n\n")
                    .append("/sys - вывод системной информации\n")
                    .append("/sysparam - текущие значения всех параметров\n")
                    .append("/sysdefault - значения по умолчанию для всех параметров\n")
                    .append("/setparam {name} {value} - установить значение для параметра\n")
                    .append("/setparam {name} null - установить пустое значение для параметра\n")
                    .append("/test_notify - тестировать нотификации от текущего момента\n")
                    .append("/test_notify_all - тестировать все нотифицкации\n")
                    .append("/userinfo - информация о пользователях\n")
                    .append("/chatinfo - информация о банном чате\n")
                    .append("/happybyozday_test - тестировать стикеры для дня рождения бота\n")
                    .append("----\n")
                    .append("/ilya_acc_{id} - акцептировать илюшизм\n")
                    .append("/ilya_del_{id} - удалить илюшизм\n")
                    .append("/ilya_test_{id} - тестировать илюшизм\n")
                    .append("/ilya_test_{id}_{poster} - тестировать илюшизм с указанным постером\n")
                    .append("/ilya_send_{id} - отправить илюшизм в банный чат\n")
                    .append("/ilya_send_{id}_{poster} - отправить илюшзим в банный чат с указанным постером\n")
                    .append("/ilya_unaccepted - вывести список не акцептированных илюшизмов\n")
                    .append("/ilya_filter {pattern} - фильтровать илюшизмы по тексту (можно использовать %)\n")
                    .append("----\n")
                    .append("/kirya_add_{id} - добавить кирюшизм\n")
                    .append("/kirya_del_{id} - удалить кирюшизм\n")
                    .append("/kirya_test_{id} - тестировать кирюшизм\n")
                    .append("/kirya_send_{id} - отправить кирюшизм в банный чат\n")
                    .append("/kirya_filter {pattern} - фильтровать кирюшизмы по тексту (можно использовать %)\n")
                    .append("/kirya_news {text} - отправить новость в виде кирюшизма (без персистенции)\n")
                    .append("----\n")
                    .append("/st {text} - отправить текст в банный чат\n")
                    .append("/st_html {text} - отправить текст в банный чат в HTML-разметке\n")
                    .append("/st_mark {text} - отправить текст в банный чат в MARKDOWN-разметке\n")
                    .append("/ss_{stickerId} - отправить стикер в банный чат\n")
                    .append("/sp_{photoId} - отправить фото в банный чат\n")
                    .append("/sv_{videoId} - отправить видео в банный чат\n")
                    .append("/sd_{documentId} - отправить документ в банный чат\n")
                    .append("----\n")
                    .append("/omen - посмотреть приметы на сегодня\n")
                    .append("/omen {dayId} - посмотреть приметы на указанный день\n");

            sender.sendText(request.getBotChatId(), text.toString(), MessageMode.HTML);
        }
    }
}
