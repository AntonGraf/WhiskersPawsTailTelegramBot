package pro.sky.whiskerspawstailtelegrambot.textAndButtons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

/**
 * Класс в котором содежатся кнопки для взаимодействия пользователя с ботом
 */
public class Button extends AllText{

  // private static final KeyboardButton[] KEYBOARD_BUTTONS_ROW_SET_DELETE = new KeyboardButton[]{new KeyboardButton(setTaskText), new KeyboardButton(showAllText)}; // пример клавиатуры с кнопками в линию

  private static final KeyboardButton INFO_SHELTER_BUTTON = new KeyboardButton(INFO_SHELTER_TEXT);

  private static final KeyboardButton SEND_PET_REPORT_BUTTON = new KeyboardButton(SEND_PET_REPORT_TEXT);

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
