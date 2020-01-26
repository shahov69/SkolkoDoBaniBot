package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.service.UserService;
import ru.xander.telebot.util.Sender;

/**
 * @author Alexander Shakhov
 */
@Component
public class AdminSetUserCityAction implements Action {

    private final UserService userService;

    @Autowired
    public AdminSetUserCityAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            String[] params = request.getActionName().split("_");
            if (params.length < 3) {
                sender.sendText(request.getBotChatId(), "не хватает параметров /setusercity_{userId}_{cityId}");
                return;
            }
            int userId = Integer.parseInt(params[1]);
            int cityId = Integer.parseInt(params[2]);
            userService.updateCityId(userId, cityId);
        }
    }
}
