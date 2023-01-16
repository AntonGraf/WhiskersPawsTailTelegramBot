package pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler;

import static pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText.*;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;

@Slf4j
public class FormMessageAndKeyboardIdYourPets {

  public SendMessage get(
      GetBaseInfoFromUpdate baseInfo, ReportService reportService) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());

    SendMessage sendMessage = null;
    FormReplyMessages formReplyMessages = new FormReplyMessages();

    String allPetByChatId = reportService.showAllAdoptedPetsByChatId(baseInfo.getChatIdL());
    if (allPetByChatId == null) {
      return null;
    }

    InlineKeyboardMarkup inlineKeyboardMarkup = form(
        allPetByChatId);

    return formReplyMessages.replyMessage(baseInfo.getChatId(),
        DESCRIPTION_SEND_REPORT_TEXT, inlineKeyboardMarkup);
  }

  private InlineKeyboardMarkup form(String allPetByChatId) {

    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());

    if (allPetByChatId == null) {
      return null;
    }
    List<String> buttonsPets = new ArrayList<>(
        List.of(allPetByChatId.split(AllText.DELIMITER_FOR_PARSER_PETS)));
    buttonsPets.add(CANCEL_CREATE_REPORT_TEXT);

    ConfigKeyboard configKeyboard = new ConfigKeyboard();
    int numberPerLine = 1;
    InlineKeyboardMarkup inlineKeyboardMarkup = configKeyboard.formReplyKeyboardAnyRowInline(
        numberPerLine, buttonsPets);

    return inlineKeyboardMarkup;
  }

}
