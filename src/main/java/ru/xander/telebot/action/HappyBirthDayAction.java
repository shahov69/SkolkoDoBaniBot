package ru.xander.telebot.action;

import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.util.Sender;
import ru.xander.telebot.util.Utils;

/**
 * @author Alexander Shakhov
 */
@Component
public class HappyBirthDayAction implements Action {

    private static final String[] stickers = {
            "CAADBQADwQMAAukKyAOn6YoTwfotbgI", //dancing nigga
            "CAADBQADogMAAukKyAPJE2BitRNU8gI", //clubber
            "CAADBQADbwMAAukKyAOvzr7ZArpddAI", //di caprio
            "CAADBAADIQADmDVxAkNQwGGebphEAg", //bender
            "CAADAgADvQMAAjq5FQIVDZaqyP2llgI", //putin kiss
            "CAADAgADuwMAAjq5FQLpT_o8u1zbKgI", //putin eyes
            "CAADAgADOAEAAhZ8aAP0b0MaIxsr8QI", //cat
            "CAADAgADWgEAAsuhZwjwREFn7Wj9yQI", //pyos
            "CAADAgADGAAD5NdGDj8TYTfHnZ7gAg", //hohot show
            "CAADBAADZwEAAnscSQABTwgDTjWPSboC" //cereal
    };

    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat() && "/happybyozday_test".equals(request.getActionName())) {
            sendStickers(request, sender);
        } else if (Utils.isHappyBirthDay()) {
            sendThanks(request, sender);
        } else if (Utils.isHappyBirthNextDay()) {
            sender.sendSticker(request.getChatId(), "CAADBQADoAMAAukKyAOf32c1-IxVvAI");
        } else {
            sendHappyBirthDayPicture(request, sender);
        }
    }

    private void sendStickers(Request request, Sender sender) {
        for (String sticker : stickers) {
            sender.sendSticker(request.getBotChatId(), sticker);
        }
    }

    private void sendThanks(Request request, Sender sender) {
        String mention = Utils.randomUserMention(request);
        switch (Utils.randomInt(5)) {
            // спасибо и стикер
            case 0:
            case 1:
            case 2:
                sender.sendText(request.getChatId(), "Спасибо, " + mention + "!", request.getMessageId());
                sender.sendSticker(request.getChatId(), Utils.randomArray(stickers));
                break;
            // только спасибо
            case 3:
                sender.sendText(request.getChatId(), "Спасибо, " + mention + "!", request.getMessageId());
                break;
            // только стикер
            default:
                sender.sendSticker(request.getChatId(), Utils.randomArray(stickers), request.getMessageId());
                break;
        }
    }

    private void sendHappyBirthDayPicture(Request request, Sender sender) {
        Utils.tryWithResource("/media/happy_birthday.png", picture -> {
            Integer replyTo = Utils.randomBoolean() ? request.getMessageId() : null;
            sender.sendPicture(
                    request.getChatId(),
                    "День рождения бота",
                    picture,
                    "ДР бота 16 февраля. Успей поздравить первым!!",
                    replyTo);
        });
    }
}
