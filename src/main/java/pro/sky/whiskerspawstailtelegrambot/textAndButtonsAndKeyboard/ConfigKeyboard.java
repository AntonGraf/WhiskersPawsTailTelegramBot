package pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, где создается кнопки в боте
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Configuration
@Slf4j
public class ConfigKeyboard extends Button {
    /**
     * Метод установки клавиатуры на нажатие start
     */
    public ReplyKeyboardMarkup initKeyboardOnClickStart(){

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> KEYBOARD_BUTTONS_ROW_INFO_REPORT = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(INFO_SHELTER_BUTTON);
        row.add(SEND_PET_REPORT_BUTTON);
        KEYBOARD_BUTTONS_ROW_INFO_REPORT.add(row);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(KEYBOARD_BUTTONS_ROW_INFO_REPORT);
        return keyboardMarkup;
    }
    /**
     * Метод установки клавиатуры на нажатие отправить отчет
     */
    public ReplyKeyboardMarkup initKeyboardOnClickSendPetReport(){

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> KEYBOARD_BUTTONS_ROW_INFO_REPORT = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(SHOW_ALL_YOUR_PET_BUTTON);
        row.add(SEND_REPORT_BUTTON);
        row.add(CANCEL_BUTTON);
        KEYBOARD_BUTTONS_ROW_INFO_REPORT.add(row);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(KEYBOARD_BUTTONS_ROW_INFO_REPORT);
        return keyboardMarkup;
    }
}
