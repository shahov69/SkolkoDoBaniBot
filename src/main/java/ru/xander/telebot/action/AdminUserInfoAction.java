package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.service.UserService;
import ru.xander.telebot.util.Sender;

import java.util.stream.Collectors;

/**
 * @author Alexander Shakhov
 */
@Component
public class AdminUserInfoAction implements Action {

    private final UserService userService;

    @Autowired
    public AdminUserInfoAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            String usersInfo = userService.getAll()
                    .stream()
                    .map(user -> user.getId()
                            + " - f: " + user.getFirstName()
                            + ", l: " + user.getLastName()
                            + ", u: " + user.getUserName()
                            + ", isbot: " + user.getIsBot()
                            + ", lang: " + user.getLangCode()
                            + ", city: " + user.getCityId() + "\n"
                            + "/setparam ADMIN_ID " + user.getId() + "\n"
                            + "/setusercity_" + user.getId() + "_" + user.getCityId()
                    )
                    .collect(Collectors.joining("\n\n"));
            sender.sendText(request.getBotChatId(), usersInfo);
        }
    }
}
