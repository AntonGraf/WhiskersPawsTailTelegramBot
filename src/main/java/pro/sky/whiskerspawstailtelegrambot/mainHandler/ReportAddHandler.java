package pro.sky.whiskerspawstailtelegrambot.mainHandler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;

/**
 * обработка репорт на будующее
 */
@Component
public class ReportAddHandler implements MainHandler {

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

  @Override
  public SendMessage handler(Update update) {
    return null;
  }

  public SendMessage getSendMessageReport(
      Message message, String textReplyMessage, ReplyKeyboardMarkup keyboardMarkup) {
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

    String allPetByChatId = reportService.showAllAdoptedPets(message);
    InlineKeyboardMarkup inlineKeyboardMarkup = configKeyboard.formReplyKeyboardInlineInOneRow(
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

    InlineKeyboardMarkup inlineKeyboardMarkup = configKeyboard.formReplyKeyboardInlineInOneRow(
        AllText.CANCEL_TEXT);

    reportService.changeStateAdoptiveParent(message);

    return sendMessage = getSendMessageReport(message, AllText.DESCRIPTION_SEND_REPORT_TEXT,
        inlineKeyboardMarkup);
  }

  //endregion

  /**
   * Метод пытается сохранить отчет о животном в БД
   *
   * @param
   */
  public void sendReport() {

  }


}
