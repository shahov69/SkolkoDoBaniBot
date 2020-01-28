package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.MessageMode;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.entity.Omen;
import ru.xander.telebot.repository.OmenRepo;
import ru.xander.telebot.sender.Sender;
import ru.xander.telebot.service.ForecastService;
import ru.xander.telebot.util.Utils;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Alexander Shakhov
 */
@Component
public class WeatherAction implements Action {
    private final ForecastService forecastService;
    private final OmenRepo omenRepo;

    @Autowired
    public WeatherAction(ForecastService forecastService, OmenRepo omenRepo) {
        this.forecastService = forecastService;
        this.omenRepo = omenRepo;
    }

    @Override
    public void execute(Request request, Sender sender) {
        InputStream forecastRender = forecastService.getForecastRender();
        String omen = prepareOmen();
        sender.sendPicture(request.getChatId(), "forecast", forecastRender, omen, MessageMode.HTML);
    }

    @SuppressWarnings("unchecked")
    private String prepareOmen() {
        LocalDate today = LocalDate.now(Utils.ZONE_ID_MOSCOW);
        int dayId = Utils.getDayId(today);
        Optional<Omen> omen = omenRepo.findById(dayId);
        if (omen.isPresent()) {
            List<String> omenList = Utils.parseJson(omen.get().getOmens(), List.class);
            String randomOmen = Utils.randomList(omenList);
            return String.format("<b>%s - %s.</b>\n%s",
                    Utils.formatSlavDay(today.getMonthValue(), today.getDayOfMonth()),
                    omen.get().getTitle(),
                    randomOmen);
        } else {
            return String.format("<b>%s.</b>",
                    Utils.formatSlavDay(today.getMonthValue(),
                            today.getDayOfMonth()));
        }
    }
}
