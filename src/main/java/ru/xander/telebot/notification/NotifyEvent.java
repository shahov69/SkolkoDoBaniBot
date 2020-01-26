package ru.xander.telebot.notification;

import ru.xander.telebot.util.Utils;

/**
 * @author Alexander Shakhov
 */
public enum NotifyEvent {
    YEAR(Units.YEAR, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "Баня будет через год.");
        sender.sendSticker(data.getChatId(), "CAADBAADZwEAAnscSQABTwgDTjWPSboC");
    }),
    DAY_50(50 * Units.DAY, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "50 дней");
        sender.sendSticker(data.getChatId(), "CAADAgAD8AADNmLjBQABSvspyMTwMxYE");
    }),
    HOUR_1000(1000 * Units.HOUR, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "Тысяча часов до баньки, пчаны! ТЫ-СЯ-ЧА!!!");
        sender.sendSticker(data.getChatId(), "CAADBQADlwMAAukKyAOTUOZzvxltnQI"); // николас кейдж
    }),
    MINUTE_50_000(50_000 * Units.MINUTE, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "50 тыщ минут");
        sender.sendSticker(data.getChatId(), "CAADBQADswMAAukKyAMctVqggO35_QI"); // мунк
    }),
    WEEK_4(4 * Units.WEEK, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "Ещё 4 недели до бани");
        sender.sendSticker(data.getChatId(), "CAADAgADDAEAAhZ8aAPEXuoz0922FwI"); // плачущий кот
    }),
    WEEK_3(3 * Units.WEEK, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "Три недели, блджад!");
        sender.sendSticker(data.getChatId(), "CAADAgADwwUAAvoLtgivOiF-yz3CUgI"); // скелет
    }),
    HOUR_500(500 * Units.HOUR, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "Пицот часов!!!1");
        sender.sendSticker(data.getChatId(), "CAADAgADOAEAAhZ8aAP0b0MaIxsr8QI"); // супер-кот
    }),
    MILLIS_123(1234567890L, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendSticker(data.getChatId(), "CAADAgADSwEAAsuhZwi_txh7AAH9okkC");
    }),
    WEEK_2(2 * Units.WEEK, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "Ровно две недели!");
        sender.sendSticker(data.getChatId(), "CAADAgADHgEAAuL27QYuS8EVQWsMNgI"); // невский
    }),
    SECOND_1_000_000(1_000_000 * Units.SECOND, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "Ахулиард наносекунд до баньки!");
        sender.sendSticker(data.getChatId(), "CAADBQADsQMAAukKyAN92NbWrspYCgI"); // макконахи
    }),
    DAY_10(10 * Units.DAY, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "До бани 10 дней!!");
        sender.sendSticker(data.getChatId(), "CAADAgADGAAD5NdGDj8TYTfHnZ7gAg"); // супер хохот шоу
    }),
    SECONDS_777_777(777_777 * Units.SECOND, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "777 777 секунд до баньки.");
        sender.sendSticker(data.getChatId(), "CAADBAADIQADmDVxAkNQwGGebphEAg"); // бендер с сигой
    }),
    HOURS_200(200 * Units.HOUR, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "До баньки осталось 200 часиков");
        sender.sendSticker(data.getChatId(), "CAADAgADFwQAAjq5FQIQg635iAVIZwI"); // путин с пальцем
    }),
    WEEK_1(7 * Units.DAY, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "Банька уже через неделю! Пора готовить труселя.");
        sender.sendSticker(data.getChatId(), "CAADBQADewMAAukKyANR7TNPzLj7awI");  // чувак с пальцем
    }),
    MINUTE_10_000(10_000 * Units.MINUTE, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "Десять тыщ минут и ты в баньке!");
        sender.sendSticker(data.getChatId(), "CAADBQADwQMAAukKyAOn6YoTwfotbgI");  // танцующий нигга
    }),
    SECOND_500_000(500_000 * Units.SECOND, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "Полмиллиона секунд, Инша-ала!");
        sender.sendSticker(data.getChatId(), "CAADAgADRQAD50u_DaYlwxBLDRaSAg");  // инша-ала
    }),
    HOUR_100(100 * Units.HOUR, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "Всего лишь сто часов...");
        sender.sendSticker(data.getChatId(), "CAADAgADzAIAAmd36UrClOzb5bAj7QI"); // лоза
    }),
    MINUTE_5000(5_000 * Units.MINUTE, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendSticker(data.getChatId(), "CAADAgADRwEAAsuhZwgaSRjemEAmJgI"); // смищной чувак
    }),
    //    DAY_3(3 * Units.DAY, "ゲイバスの3日前", "CAADAgADexkAAuX3UgOO6S7Tt8vLhAI"), // онимэ - 3 дня до гей-автобуса
    DAY_3(3 * Units.DAY, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "Ровно три дня до бани, работяги!");
        sender.sendSticker(data.getChatId(), "CAADAgADaAEAAsuhZwiKgVEiA4mwRwI"); // руки
    }),
    //    HOUR_50(50 * 3600 * 1000L, "", "CAADBQAD0wMAAukKyAMsRH9b_BLzvQI"), // узбагойзя
    HOUR_50(50 * Units.HOUR, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "私のかかとを吸う!");
        sender.sendSticker(data.getChatId(), "CAADAgADpQADlhcTCsbShpKqho4YAg"); // онимэ - Соси мой каблук
    }),
    //    DAY_2(2 * Units.DAY, "あと2日、オナニストメンバー", "CAADAgADNxkAAuX3UgM9_oYuM8udiwI"), // онимэ - Еще два дня, член онаниста
    DAY_2(2 * Units.DAY, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "Два денька!");
        sender.sendSticker(data.getChatId(), "CAADBQADwAMAAukKyAOjljyyS-dv2AI"); // дедон
    }),
    //    SECOND_100_000(100_000 * 1000L, "Ещё сто тыщ секунд и банька!", "CAADBQADqAMAAukKyAPqMRdkk-uf6QI"), // вилл смит
    SECOND_100_000(100_000 * 1000L, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "赤い太陽が来る10万秒");
        sender.sendSticker(data.getChatId(), "CAADBAADhQQAAjZHEwABcKEKwQYi374C"); // онимэ - 100 000 секунд до появления красного солнца
    }),
    //    DAY_1(24 * 3600 * 1000L, "Остался один денёк, бро!!!", STICKER_OBNIMASHKI),
    DAY_1(Units.DAY, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "明日私たちはあなたと一緒に泳ぎます");
        sender.sendSticker(data.getChatId(), "CAADAgADKgAD8Lt_GlwViE1lnNbrAg"); // онимэ - Завтра мы будем плавать с тобой
    }),
    //    MINUTE_1000(1_000 * Units.MINUTE, "", "CAADAgADMxkAAuX3UgNM3Vo3LU_gLwI"), // онимэ
    MINUTE_1000(1_000 * Units.MINUTE, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "Ещё тысяча минут, пчаны!");
        sender.sendSticker(data.getChatId(),  "CAADBAADSQEAAnscSQABMpg-lIY8pZEC"); // fuck yeah
    }),
    //    HOUR_10(10 * 3600 * 1000L, "Десять часов до баньки! Ещё десять часиков.", "CAADBQADpwMAAukKyANvpK-R8IlQUgI"), // девочка - 10 пальцев вверх
    HOUR_10(10 * Units.HOUR, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "10時間、嫌悪");
        sender.sendSticker(data.getChatId(), "CAADAgADpwADlhcTCqY8BMFQvmzQAg"); // онимэ - 10 часов, отвращение
    }),
    //    HOUR_3(3 * 3600 * 1000L, "Три часа!!!!", "CAADAgAD8QIAAlwCZQM8MTwQw7YdEQI"), // кразу
    HOUR_3(3 * Units.HOUR, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "San (三) !!!");
        sender.sendSticker(data.getChatId(), "CAADAgADLgAD8Lt_GoJdPKwTc9i3Ag"); // онимэ - три
    }),
    //    HOUR_2(2 * Units.HOUR, "Ni (二) !!!!", "CAADAgADhhkAAuX3UgNO_9jnWnr9SAI"), // онимэ - два
    HOUR_2(2 * Units.HOUR, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "ДВВАААААА ЧАСА ЁБАНАРОТ!!!!");
        sender.sendSticker(data.getChatId(), "CAADBAADTwEAAnscSQABJ3-u0ZN38BoC"); // кразу
    }),
    //    HOY_1(24 * 3600 * 1000L, "Остался один денёк, бро!!!", STICKER_OBNIMASHKI),
    HOUR_1(Units.HOUR, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), data.getNanos()));
        }
        sender.sendText(data.getChatId(), "Ichi (一) !!!");
        sender.sendSticker(data.getChatId(), "CAADAgADLRkAAuX3UgNJAAEcY5BYzqAC"); // онимэ - один
    }),
    START(0L, (sender, data) -> {
        if (data.isShowHowMuch()) {
            sender.sendText(data.getChatId(), Utils.formatBanyaTime(data.getHowMuchTemplate(), 0));
        }
        sender.sendDocument(data.getChatId(), "CgADAgADRAQAAm3rSEpUrjvDd9P9wQI");
    });

    private static final NotifyEvent[] allEvent = values();
    private final long lag;
    private final NotifyHandler handler;

    NotifyEvent(long lag, NotifyHandler handler) {
        this.lag = lag;
        this.handler = handler;
    }

    public long getLag() {
        return lag;
    }

    public NotifyHandler getHandler() {
        return handler;
    }

    public static NotifyEvent[] getAllEvent() {
        return allEvent;
    }

    interface Units {
        long SECOND = 1000L;
        long MINUTE = 60 * SECOND;
        long HOUR = 60 * MINUTE;
        long DAY = 24 * HOUR;
        long WEEK = 7 * DAY;
        long YEAR = 365 * DAY;
    }
}
