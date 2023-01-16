package pro.sky.whiskerspawstailtelegrambot.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.whiskerspawstailtelegrambot.TelegramBotUpdatesListener;

/**
 * Контроллер для бота в телеге
 */
@RestController
@Getter
@Setter
@Slf4j
//@Hidden
public class BotController {
    final TelegramBotUpdatesListener telegramBotUpdatesListener;

    public BotController(TelegramBotUpdatesListener telegramBotUpdatesListener) {
        this.telegramBotUpdatesListener = telegramBotUpdatesListener;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public PartialBotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramBotUpdatesListener.onWebhookUpdateReceived(update);
    }
}
