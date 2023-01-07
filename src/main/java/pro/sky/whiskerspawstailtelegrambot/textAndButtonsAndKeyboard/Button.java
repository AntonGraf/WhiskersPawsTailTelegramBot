package pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

/**
 * Класс в котором содежатся кнопки для взаимодействия пользователя с ботом
 */
public class Button extends AllText {

  protected static final KeyboardButton INFO_SHELTER_BUTTON = new KeyboardButton(INFO_SHELTER_TEXT);
  protected static final KeyboardButton SHOW_ALL_YOUR_PET_BUTTON = new KeyboardButton(
      SHOW_ALL_YOUR_PET_TEXT);
  protected static final KeyboardButton SEND_PET_REPORT_BUTTON = new KeyboardButton(
      SEND_PET_REPORT_TEXT);

  protected static final KeyboardButton SEND_REPORT_BUTTON = new KeyboardButton(SEND_REPORT_TEXT);
  protected static final KeyboardButton CANCEL_BUTTON = new KeyboardButton(CANCEL_TEXT);

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
