package pro.sky.whiskerspawstailtelegrambot.mainHandler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;

/**
 * обработка репорт на будующее
 */
@Component
public class ReportAddHandler implements MainHandler {

  private final FormReplyMessages formReplyMessages;

  public ReportAddHandler(FormReplyMessages formReplyMessages) {
    this.formReplyMessages = formReplyMessages;
  }
  @Override
  public SendMessage handler(Update update) {
    return null;
  }
  public SendMessage handlerReport(
      Message message, String textReplyMessage, ReplyKeyboardMarkup keyboardMarkup) {
    SendMessage sendMessage = formReplyMessages.replyMessage(message, textReplyMessage,
        keyboardMarkup);
    return sendMessage;
  }


}
