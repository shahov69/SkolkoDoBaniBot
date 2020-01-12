package ru.xander.telebot.action;

import org.springframework.stereotype.Component;
import ru.xander.telebot.util.Request;

/**
 * @author Alexander Shakhov
 */
@Component
public class FirstTimeAction implements AdminAction {
    @Override
    public void execute(Request request) {
        System.out.println("First Time Action");
    }
}
