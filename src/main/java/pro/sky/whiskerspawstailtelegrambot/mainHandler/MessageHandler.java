package pro.sky.whiskerspawstailtelegrambot.mainHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.configuration.ConfigButton;
import pro.sky.whiskerspawstailtelegrambot.textAndButtons.AllText;


/**
 * обработка текстового сообщения
 */
@Slf4j
@Component("MessageHandler")
public class MessageHandler implements MainHandler {

  final ConfigButton configButton;

  public MessageHandler(ConfigButton configButton) {
    this.configButton = configButton;
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
      String textMessage = update.getMessage().getText();
      //здесь инжект текст кнопок, любой текст крч
      switch (textMessage) {

        case (AllText.START_TEXT):
          sendMessage = new SendMessage(chatId, AllText.WELCOME_MESSAGE_TEXT);
          configButton.initButton(sendMessage);
          break;

        case (AllText.CALL_TO_VOLUNTEER_TEXT):
          //цепляем сервисом бд волонтера
          break;

        case (AllText.SEND_PET_REPORT_TEXT):
          // реализация логики отправить отчет
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


