package pro.sky.whiskerspawstailtelegrambot.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.whiskerspawstailtelegrambot.TelegramBotUpdatesListener;

import java.util.ArrayList;
import java.util.List;
import pro.sky.whiskerspawstailtelegrambot.textAndButtons.AllText;

/**
 * Класс, где создается кнопки в боте
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Configuration
@Slf4j
public class ConfigButton {
    public void initButton(SendMessage message){

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(AllText.START_TEXT);
        // row.add("/mydata");
        keyboardRows.add(row);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);
    }
}
