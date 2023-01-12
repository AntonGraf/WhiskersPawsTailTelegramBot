package pro.sky.whiskerspawstailtelegrambot.mainHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.mainHandler.reportHandler.ReportAddHandler;
import pro.sky.whiskerspawstailtelegrambot.service.AdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent;

/**
 *  Обработка сообщений от inline клавиатуры
 */
@Slf4j
@Component
public class CallbackQueryHandler {

  private final FormReplyMessages formReplyMessages;
  private final ConfigKeyboard configKeyboard;
  private final ReportAddHandler reportAddHandler;

  private final RegistrationHandler registrationHandler;
  private final AdoptiveParentService adoptiveParentService;

  public CallbackQueryHandler(FormReplyMessages formReplyMessages, ConfigKeyboard configKeyboard, ReportAddHandler reportAddHandler,
      RegistrationHandler registrationHandler, AdoptiveParentService adoptiveParentService) {
    this.formReplyMessages = formReplyMessages;
    this.configKeyboard = configKeyboard;
    this.reportAddHandler = reportAddHandler;
    this.registrationHandler = registrationHandler;
    this.adoptiveParentService = adoptiveParentService;
  }

  /** обработка CallbackQuery ответа от пользователя
   * @param callbackQuery информация о нажатой пользователем кнопке кнопке
   * @return SendMessage
   */
  public SendMessage handler(CallbackQuery callbackQuery) {

    log.debug("Вызов метода handler класса" + this.getClass().getName());

    SendMessage sendMessage = null;
    String textMessage = callbackQuery.getData();
    Message message = callbackQuery.getMessage();
    String chatId = message.getChatId().toString();
    switch (textMessage) {

      case (AllText.CANCEL_TEXT)://реакция на кнопку отмена - возврат в главное меню, изменение всех статусов на FREE
        adoptiveParentService.updateStateAdoptiveParentByChatId(Long.parseLong(chatId),
            StateAdoptiveParent.FREE);
        return sendMessage = formReplyMessages.replyMessage(message,
            AllText.CANCEL_RETURN_MAIN_MENU_TEXT,
            configKeyboard.initKeyboardOnClickStart());

      case (AllText.SHOW_ALL_YOUR_PET_TEXT):     // нажатие кнопки показать всех взятых животных
        sendMessage = reportAddHandler.clickButton_SHOW_ALL_YOUR_PET(message);
        break;

      case (AllText.SEND_REPORT_TEXT):     // нажатие кнопки отправить отчет
        sendMessage = reportAddHandler.clickButton_SEND_REPORT(message);
        break;

      case (AllText.REGISTRATION_BUTTON):     // нажатие кнопки регистрация
        sendMessage = registrationHandler.addToTable(message,String.valueOf(message.getChatId()));
        break;
    }
    return sendMessage;
  }

}
