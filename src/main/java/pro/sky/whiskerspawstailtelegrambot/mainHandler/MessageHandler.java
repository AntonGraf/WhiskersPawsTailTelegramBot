package pro.sky.whiskerspawstailtelegrambot.mainHandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;


/**
 * обработка текстового сообщения
 */
@Slf4j
@Component("MessageHandler")
public class MessageHandler implements MainHandler {

  final ConfigKeyboard configKeyboard;
  private final ReportAddHandler reportAddHandler;
  private final FormReplyMessages formReplyMessages;

  public MessageHandler(ConfigKeyboard configKeyboard, ReportAddHandler reportAddHandler,
      FormReplyMessages formReplyMessages) {
    this.configKeyboard = configKeyboard;
    this.reportAddHandler = reportAddHandler;
    this.formReplyMessages = formReplyMessages;
  }

  /**
   * Метод, который отвечает на входящее сообщение, либо на выбор в меню
   *
   * @param update адейт от пользователя в виде текста
   * @return отправляем ответ
   */
  @Override
  public SendMessage handler(Update update) {
    SendMessage sendMessage = null;
    String chatId = String.valueOf(update.getMessage().getChatId());
    boolean checkUpdate = !update.getMessage().hasText();
    if (!checkUpdate) {
      log.debug("Обработка сообщения в виде текста");
      Message message = update.getMessage();
      String textMessage = message.getText();
      //здесь инжект текст кнопок, любой текст крч
      switch (textMessage) {

        case (AllText.START_TEXT):
          sendMessage = formReplyMessages.replyMessage(message, AllText.WELCOME_MESSAGE_TEXT,
              configKeyboard.initKeyboardOnClickStart());
          break;

        case (AllText.CALL_TO_VOLUNTEER_TEXT):
          //цепляем сервисом бд волонтера
          break;

        case (AllText.SEND_PET_REPORT_TEXT):     // реализация логики отправить отчет
          sendMessage = reportAddHandler.handlerReport(message, AllText.MENU_SEND_PET_REPORT_TEXT,
              configKeyboard.initKeyboardOnClickSendPetReport());
          break;

        default:
          sendMessage = new SendMessage(chatId, AllText.UNKNOWN_COMMAND_TEXT);
          break;
      }
    }
    return sendMessage;
  }

  //для удобства
  private String readUpdate(Update update) {
    return update.getMessage().getText();
  }


}


