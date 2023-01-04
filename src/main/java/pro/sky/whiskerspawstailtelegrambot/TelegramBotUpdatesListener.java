package pro.sky.whiskerspawstailtelegrambot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.util.Objects;

/**
 * наш бот
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class TelegramBotUpdatesListener extends SpringWebhookBot {

    String webHookPath;
    String botUserName;
    String botToken;

    public TelegramBotUpdatesListener(SetWebhook webhook) {
        super(webhook);

    }

    /**
     * Сейчас метод просто отвечает на любое сообщение
     * @param update уведомление от пользвателя
     * @return ответ пользователю
     */
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        long chat_id = update.getMessage().getChatId();
        /*if (update.getMessage() != null && update.getMessage().hasText()) {

            try {
                execute(new SendMessage(String.valueOf(chat_id), "Hi " + update.getMessage().getText()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }*/
        if(Objects.equals(update.getMessage().getText(), "start")){
            try {
                execute(new SendMessage(String.valueOf(chat_id), "Start " + update.getMessage().getText()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}