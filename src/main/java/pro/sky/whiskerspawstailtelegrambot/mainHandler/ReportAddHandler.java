package pro.sky.whiskerspawstailtelegrambot.mainHandler;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent;

/**
 * обработка репорт на будующее
 */
@Slf4j
@Component
public class ReportAddHandler {

  private final FormReplyMessages formReplyMessages;
  private final ReportService reportService;
  private final ConfigKeyboard configKeyboard;
  private SendMessage sendMessage = null;

  public ReportAddHandler(FormReplyMessages formReplyMessages, ReportService reportService,
      ConfigKeyboard configKeyboard) {
    this.formReplyMessages = formReplyMessages;
    this.reportService = reportService;
    this.configKeyboard = configKeyboard;
  }

  public SendMessage handler(Message message) {

    SendMessage sendMessage = null;
    String textMessage = message.getText();
    String chatId = message.getChatId().toString();
    StateAdoptiveParent state = reportService.getStateAdoptiveParentByChatId(
        Long.parseLong(chatId));

    switch (textMessage) {

      case (AllText.CANCEL_TEXT)://реакция на кнопку отмена - возврат в главное меню, изменение всех статусов на FREE
        return sendMessage = reportService.changeStateAdoptiveParent(message,
            AllText.CANCEL_RETURN_MAIN_MENU_TEXT, StateAdoptiveParent.FREE);
    }

      switch (state) {

        case START_SEND_REPORT:
          return sendMessage = reportService.changeStateAdoptiveParent(message,
              AllText.CANCEL_RETURN_MAIN_MENU_TEXT, StateAdoptiveParent.FREE);

        case WAIT_PHOTO_REPORT:
          return sendMessage = reportService.changeStateAdoptiveParent(message,
              AllText.CANCEL_RETURN_MAIN_MENU_TEXT, StateAdoptiveParent.FREE);

        case WAIT_HEALTH_REPORT:
          return sendMessage = reportService.changeStateAdoptiveParent(message,
              AllText.CANCEL_RETURN_MAIN_MENU_TEXT, StateAdoptiveParent.FREE);

        case WAIT_DIET_REPORT:
          return sendMessage = reportService.changeStateAdoptiveParent(message,
              AllText.CANCEL_RETURN_MAIN_MENU_TEXT, StateAdoptiveParent.FREE);

        case WAIT_HABITS_REPORT:
          return sendMessage = reportService.changeStateAdoptiveParent(message,
              AllText.CANCEL_RETURN_MAIN_MENU_TEXT, StateAdoptiveParent.FREE);
      }

    return sendMessage;
  }

  //region clickButton

  /**
   * Метод обрабатывает нажатие на кнопку показать всех ваших животных
   *
   * @param message сообщение из update
   */
  public SendMessage clickButton_SHOW_ALL_YOUR_PET(Message message) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());
    String allPetByChatId = reportService.showAllAdoptedPets(message);
    InlineKeyboardMarkup inlineKeyboardMarkup = configKeyboard.formReplyKeyboardInOneRowInline(
        AllText.SHOW_ALL_YOUR_PET_TEXT,
        AllText.SEND_REPORT_TEXT, AllText.CANCEL_TEXT);

    return sendMessage = formReplyMessages.replyMessage(message,
        allPetByChatId, inlineKeyboardMarkup);
  }

  /**
   * Метод обрабатывает нажатие на кнопку показать всех отправить отчет и изменят статус
   * пользователя на ожидание отправки отчета
   *
   * @param message сообщение из update
   */
  public SendMessage clickButton_SEND_REPORT(Message message) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());
    InlineKeyboardMarkup inlineKeyboardMarkup = configKeyboard.formReplyKeyboardInOneRowInline(
        AllText.CANCEL_TEXT);
    ReplyKeyboardMarkup replyKeyboardMarkup = configKeyboard.formReplyKeyboardInOneRow(
        AllText.CANCEL_TEXT);

    return sendMessage = reportService.changeStateAdoptiveParent(message,
        AllText.DESCRIPTION_SEND_REPORT_TEXT, StateAdoptiveParent.WAIT_SEND_REPORT);
  }

  //endregion

  /**
   * Метод пытается сохранить отчет о животном в БД
   *
   * @param
   */
  public SendMessage sendReport(Message message) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());

    ReplyKeyboardMarkup replyKeyboardMarkup = configKeyboard.formReplyKeyboardInOneRow(
        AllText.CANCEL_TEXT);

    String textMessage = message.getText();
    List<PhotoSize> photoSizes = message.getPhoto();
    Document messageDocument = message.getDocument();

    boolean isTextOk = checkTextInSentReport(textMessage);
    boolean isPhotoOk = true;

    if (!isTextOk) {
      return sendMessage = formReplyMessages.replyMessage(message,
          AllText.NO_TEXT_SEND_REPORT_TEXT,
          configKeyboard.initKeyboardOnClickStart());
    }
    if (!isPhotoOk) {
      return sendMessage = formReplyMessages.replyMessage(message,
          AllText.NO_PHOTO_SEND_REPORT_TEXT,
          configKeyboard.initKeyboardOnClickStart());
    }

    return sendMessage = saveReportInDb(message);
  }

  public boolean checkTextInSentReport(String textMessage) {

    Pattern pattern = Pattern.compile("^(\\d+)(?:\\s+\\w+|\\s+[а-яА-Я]+){15,}");
    Matcher matcher = pattern.matcher(textMessage);

    if (matcher.matches()) {
      return true;
    }
    return true;//true для теста

  }

  public void checkPhotoInSentReport() {

  }

  public SendMessage saveReportInDb(Message message) {

    reportService.addReport();

    return sendMessage = reportService.changeStateAdoptiveParent(message,
        AllText.SUCCESSFUL_MESSAGE_SEND_REPORT_TEXT, StateAdoptiveParent.FREE);

  }


}
