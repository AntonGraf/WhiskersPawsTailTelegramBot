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
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent;

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

  private final StateHandler stateCommonHandler;


  public CallbackQueryHandler(FormReplyMessages formReplyMessages, ConfigKeyboard configKeyboard,
      ReportHandler reportHandler,
      RegistrationHandler registrationHandler, AdoptiveParentService adoptiveParentService,
      StateHandler stateCommonHandler) {
    this.formReplyMessages = formReplyMessages;
    this.configKeyboard = configKeyboard;
    this.reportHandler = reportHandler;
    this.registrationHandler = registrationHandler;
    this.adoptiveParentService = adoptiveParentService;
    this.stateCommonHandler = stateCommonHandler;
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

    StateAdoptiveParent stateAdoptiveParent = stateCommonHandler
        .getStateAdoptiveParentByChatId(baseInfo.getChatIdL());
    if (stateAdoptiveParent != null && stateAdoptiveParent != StateAdoptiveParent.FREE) {
      return stateCommonHandler.processByState(baseInfo, stateAdoptiveParent);
    }
//todo обработка inline клавиатуры
    switch (textMessage) {

//      case :
//        break;
    }
    return sendMessage;
  }


}
