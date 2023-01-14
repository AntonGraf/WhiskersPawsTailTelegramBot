package pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler;

import static pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText.CANCEL_TEXT;
import static pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText.DESCRIPTION_SEND_REPORT_TEXT;
import static pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText.YOU_HAVE_NO_ADOPTED_PETS_TEXT;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;

@Slf4j
public class FormMessageAndKeyboardIdYourPets {


  public InlineKeyboardMarkup form(long chatId, String allPetByChatId) {

    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());

    if (allPetByChatId == null) {
      return null;
    }
    List<String> buttonsPets = new ArrayList<>(
        List.of(allPetByChatId.split(AllText.DELIMITER_FOR_PARSER_PETS)));
    buttonsPets.add(CANCEL_TEXT);

    ConfigKeyboard configKeyboard = new ConfigKeyboard();
    int numberPerLine = 1;
    InlineKeyboardMarkup inlineKeyboardMarkup = configKeyboard.formReplyKeyboardAnyRowInline(
        numberPerLine, buttonsPets);

    return inlineKeyboardMarkup;
  }

}
