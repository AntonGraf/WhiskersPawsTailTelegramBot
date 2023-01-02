package pro.sky.whiskerspawstailtelegrambot.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.TelegramBotUpdatesListener;

@RestController
@Getter
@Setter
@Slf4j
public class BotController {
    final TelegramBotUpdatesListener telegramBotUpdatesListener;

    public BotController(TelegramBotUpdatesListener telegramBotUpdatesListener) {
        this.telegramBotUpdatesListener = telegramBotUpdatesListener;
    }

    /**
     * Метод принимает update и возвращает ответ
     * @param update уведомление от пользвателя
     * @return ответ пользователю
     */
    @ApiResponse
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramBotUpdatesListener.onWebhookUpdateReceived(update);
    }
}
