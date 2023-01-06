package pro.sky.whiskerspawstailtelegrambot.textAndButtons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Button extends AllText{

 // private static final KeyboardButton[] KEYBOARD_BUTTONS_ROW_SET_DELETE = new KeyboardButton[]{new KeyboardButton(setTaskText), new KeyboardButton(showAllText)}; // пример клавиатуры с кнопками в линию

private static final KeyboardButton INFO_SHELTER_BUTTON = new KeyboardButton(INFO_SHELTER_TEXT);

// пример создания в два ряда
/*    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    List<KeyboardRow> keyboardRows = new ArrayList<>();
    KeyboardRow row = new KeyboardRow();
        row.add("/start");
        row.add("/mydata");
        keyboardRows.add(row);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);*/

}
