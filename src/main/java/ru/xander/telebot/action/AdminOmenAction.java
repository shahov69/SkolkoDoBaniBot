package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.MessageMode;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.entity.Omen;
import ru.xander.telebot.repository.OmenRepo;
import ru.xander.telebot.sender.Sender;
import ru.xander.telebot.util.Utils;

import java.util.Optional;

/**
 * @author Alexander Shakhov
 */
@Component
public class AdminOmenAction implements Action {
    private final OmenRepo omenRepo;

    @Autowired
    public AdminOmenAction(OmenRepo omenRepo) {
        this.omenRepo = omenRepo;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            String[] args = request.getArgs();
            int dayId = args.length > 0 ? Integer.parseInt(args[0]) : Utils.getDayId();
            Optional<Omen> omenHolder = omenRepo.findById(dayId);
            if (omenHolder.isPresent()) {
                Omen omen = omenHolder.get();
                String text = "<b>Приметы для " + dayId + "</b>\n" +
                        "<code>Title:</code> " + omen.getTitle() + "\n" +
                        "<code>All titles:</code>\n" + omen.getAllTitles() + "\n" +
                        "<code>Description:</code>\n" + omen.getDescription() + "\n" +
                        "<code>Omens:</code>\n" + omen.getOmens() + "\n" +
                        "<code>Names:</code>\n" + omen.getNames() + "\n" +
                        "<code>Dreams:</code>\n" + omen.getDreams() + "\n" +
                        "<code>Talismans:</code>\n" + omen.getTalismans() + "\n";
                sender.sendText(request.getBotChatId(), text, MessageMode.HTML);
            } else {
                sender.sendText(request.getBotChatId(), "Приметы для dayId = " + dayId + " не найдены");
            }
        }
    }
}
