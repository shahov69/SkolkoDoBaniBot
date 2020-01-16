package ru.xander.telebot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.entity.Banya;
import ru.xander.telebot.repository.BanyaRepo;

/**
 * @author Alexander Shakhov
 */
@Service
public class BanyaService {

    private final BanyaRepo banyaRepo;
    private final SettingService settingService;

    @Autowired
    public BanyaService(BanyaRepo banyaRepo, SettingService settingService) {
        this.banyaRepo = banyaRepo;
        this.settingService = settingService;
    }

    public Banya save(Banya banya) {
        return banyaRepo.save(banya);
    }

    public Banya getBanya(Request request) {
        return banyaRepo.findByChatId(request.getChatId());
    }

    public Banya getBanyaForActiveChat(Request request) {
        Long chatId = request.isBotChat()
                ? settingService.getActiveChatId()
                : request.getChatId();
        return banyaRepo.findByChatId(chatId);
    }
}
