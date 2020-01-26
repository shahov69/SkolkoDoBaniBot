package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.search.GoogleSearchResult;
import ru.xander.telebot.service.SearchService;
import ru.xander.telebot.util.Sender;

/**
 * @author Alexander Shakhov
 */
@Component
public class DobroAction implements Action {

    private static final String STUB_GIF = "CgADBAAD_wEAAq3fFFB3ds8waXyWyRYE";

    private final SearchService searchService;

    @Autowired
    public DobroAction(SearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        GoogleSearchResult searchResult = searchService.searchDobro();
        if (searchResult == null) {
            sender.sendVideo(request.getChatId(), STUB_GIF);
        } else {
            String dobroName = "dobro_" + System.currentTimeMillis();
            switch (searchResult.getContentType()) {
                case GIF:
                case MP4:
                    sender.sendVideo(request.getChatId(), dobroName, searchResult.getContent());
                    break;
                default:
                    sender.sendPicture(request.getChatId(), dobroName, searchResult.getContent());
                    break;
            }
        }
    }
}
