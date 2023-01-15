package pro.sky.whiskerspawstailtelegrambot.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler.ReportHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers.StateHandler;
import pro.sky.whiskerspawstailtelegrambot.service.AdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;

/**
 * Обработка сообщений от inline клавиатуры
 */
@Slf4j
@Component
public class CallbackQueryHandler {

  private final FormReplyMessages formReplyMessages;
  private final ConfigKeyboard configKeyboard;
  private final ReportHandler reportHandler;

  private final RegistrationHandler registrationHandler;
  private final AdoptiveParentService adoptiveParentService;

  private final StateHandler stateHandler;


  public CallbackQueryHandler(FormReplyMessages formReplyMessages, ConfigKeyboard configKeyboard,
      ReportHandler reportHandler,
      RegistrationHandler registrationHandler, AdoptiveParentService adoptiveParentService,
      StateHandler stateHandler) {
    this.formReplyMessages = formReplyMessages;
    this.configKeyboard = configKeyboard;
    this.reportHandler = reportHandler;
    this.registrationHandler = registrationHandler;
    this.adoptiveParentService = adoptiveParentService;
    this.stateHandler = stateHandler;
  }

  /**
   * обработка CallbackQuery ответа от пользователя
   *
   * @param baseInfo базовая информация из update
   * @return SendMessage
   */
  public SendMessage handler(GetBaseInfoFromUpdate baseInfo) {

    log.debug("Вызов метода handler класса" + this.getClass().getName());

    CallbackQuery callbackQuery = baseInfo.getCallbackQuery();

    SendMessage sendMessage = null;
    String textMessage = callbackQuery.getData();
    Message message = callbackQuery.getMessage();
    String chatId = message.getChatId().toString();

//todo обработка inline клавиатуры
    switch (textMessage) {

//      case :
//        break;
    }
    return sendMessage;
  }


}
