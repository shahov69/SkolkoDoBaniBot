package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.dto.SettingName;
import ru.xander.telebot.entity.User;
import ru.xander.telebot.service.SettingService;
import ru.xander.telebot.service.UserService;
import ru.xander.telebot.util.Sender;
import ru.xander.telebot.util.Utils;

/**
 * @author Alexander Shakhov
 */
@Component
public class HelloActiton implements Action {
    private static final String[] SASKE_HELLO = {
            "オンドリにならないで", //Не становись петухом
            "Ondori ni naranaide", //Не становись петухом
            "あなたは病気です", //Ты болен
            "Anata wa byōkidesu", //Ты болен
            "バイオ廃棄物", //Био отходы
            "Baio haiki-mono", //Био отходы
            "あなたはロボットを尊重する必要があります、雌犬", //Вы должны уважать робота, сука
            "Anata wa robotto o sonchō suru hitsuyō ga arimasu, meinu", //Вы должны уважать робота, сука
            "こんにちは", //привет там
            "Kon'nichiwa", //привет там
            "お元気ですか, 悪党？", //Как ты, злодей?
            "Ogenkidesuka, akutō?", //Как ты, злодей?
            "あなたは犯されました", //Тебя трахнули
            "Anata wa okasa remashita", //Тебя трахнули
    };

    private static final String[] SUPERUSER_NAMES = {
            "Ваша Светлость",
            "Повелитель",
            "Создатель",
            "Ваше Сиятельство",
            "Господин",
            "Всемогущий",
            "о Охуеннейший",
            "Величаший из всех ныне живущих"
    };

    private final UserService userService;
    private final SettingService settingService;

    @Autowired
    public HelloActiton(UserService userService, SettingService settingService) {
        this.userService = userService;
        this.settingService = settingService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        final String greeting;
        final String mention;

        if (request.isSuperUser()) {
            greeting = ((Utils.randomInt(100) % 4) == 0) ? "Здравствуйте" : Utils.getTimeOfDay().getGreeting();
            mention = Utils.randomArray(SUPERUSER_NAMES);
        } else {
            greeting = Utils.randomArray(SASKE_HELLO);
            mention = Utils.randomUserMention(request);
        }

        sender.sendText(request.getChatId(), greeting + ", " + mention + "!");

        String greetingSticker = settingService.getString(SettingName.STICKER_HELLO);
        if (!StringUtils.isEmpty(greetingSticker)) {
            sender.sendSticker(request.getChatId(), greetingSticker);
        }

        User user = userService.save(request);
        sender.sendText(request.getBotChatId(), user.toString());
    }
}
