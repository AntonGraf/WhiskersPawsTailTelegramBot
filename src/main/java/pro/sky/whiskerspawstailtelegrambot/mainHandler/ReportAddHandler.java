package pro.sky.whiskerspawstailtelegrambot.mainHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
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
public class ReportAddHandler  {

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



  public SendMessage getSendMessageReport(
      Message message, String textReplyMessage, ReplyKeyboardMarkup keyboardMarkup) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());
    SendMessage sendMessage = formReplyMessages.replyMessage(message, textReplyMessage,
        keyboardMarkup);
    return sendMessage;
  }

  /**
   * Метод вызывается из других методов, получает параметры формирует и возвращает SendMessage,
   * нужен для сокращения кода
   *
   * @param message              сообщение из update
   * @param textReplyMessage     текс сообщения для вывода на экранпользователю, в ответ на его
   *                             действие
   * @param inlineKeyboardMarkup инлайн клавиатура, котора выводится пользователю   *
   */
  public SendMessage getSendMessageReport(
      Message message, String textReplyMessage, InlineKeyboardMarkup inlineKeyboardMarkup) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());
    return formReplyMessages.replyMessage(message, textReplyMessage,
        inlineKeyboardMarkup);
  }
  /**
   * Перегрузка. Метод вызывается из других методов, получает параметры формирует и возвращает SendMessage
   * или SendMessage с сообщение об ошибке,нужен для сокращения кода
   *
   * @param message              сообщение из update
   * @param textReplyMessage     текс сообщения для вывода на экранпользователю, в ответ на его
   *                             действие
   * @param inlineKeyboardMarkup инлайн клавиатура, котора выводится пользователю   *
   * @param isSuccessful если false, то будет отправлено сообщение об ошибке и возврат в главное меню   *
   */
  public SendMessage getSendMessageReport(
      Message message, String textReplyMessage, InlineKeyboardMarkup inlineKeyboardMarkup,
      boolean isSuccessful) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());
    if (!isSuccessful) {
      return formReplyMessages.replyMessageError(message, AllText.ERROR_REPLY_TEXT,
          configKeyboard.formReplyKeyboardInOneRow(AllText.INFO_SHELTER_TEXT, AllText.SEND_REPORT_TEXT));
    }
    return formReplyMessages.replyMessage(message, textReplyMessage,
        inlineKeyboardMarkup);
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

    return sendMessage = getSendMessageReport(message,
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

    boolean isChangeState = reportService.changeStateAdoptiveParent(message,
        StateAdoptiveParent.WAIT_SEND_REPORT);

    return sendMessage = getSendMessageReport(message, AllText.DESCRIPTION_SEND_REPORT_TEXT,
        inlineKeyboardMarkup, isChangeState);
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
    InlineKeyboardMarkup inlineKeyboardMarkup = configKeyboard.formReplyKeyboardInOneRowInline(
        AllText.CANCEL_TEXT);

    String textMessage = message.getText();
    checkTextInSentReport(textMessage);

    boolean isChangeState = reportService.changeStateAdoptiveParent(message, StateAdoptiveParent.FREE);

    return sendMessage = getSendMessageReport(message, AllText.DESCRIPTION_SEND_REPORT_TEXT,
        inlineKeyboardMarkup, isChangeState);
  }

  public  void checkTextInSentReport(String textMessage){

    String text = "Егор Алла Александр";
    Pattern pattern = Pattern.compile("(([\\w]{2})([\\s]{2}))");
    Matcher matcher = pattern.matcher(text);


  }
  public  void checkPhotoInSentReport(){



  }


}
