package pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.handlers.CallbackQueryHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.StandardReplyHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers.StateAdoptiveParentHandlerImpl;
import pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers.StateCommonHandlerImpl;
import pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers.StateReportHandlerImpl;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent;

@Slf4j
@Component("MainHandler")
public class MainHandlerImpl implements MainHandler {

  private final StandardReplyHandler standardReplyHandler;
  private final CallbackQueryHandler callbackQueryHandler;

  private final StateCommonHandlerImpl stateCommonHandler;


  public MainHandlerImpl(StandardReplyHandler standardReplyHandler,
      CallbackQueryHandler callbackQueryHandler,
      StateAdoptiveParentHandlerImpl stateAdoptiveParentHandler,
      StateReportHandlerImpl stateReportHandler, StateCommonHandlerImpl stateCommonHandler) {
    this.standardReplyHandler = standardReplyHandler;
    this.callbackQueryHandler = callbackQueryHandler;
    this.stateCommonHandler = stateCommonHandler;
  }

  /**
   * Обрабатываем Update делим его по типу сообщения
   */
  @Override
  public SendMessage process(Update update) {

    if(processingStates(update)){




    }

    return processingIncomingMessages(update);


  }

  /**
   * Обработка входящих сообщений из update
   */
  private SendMessage processingIncomingMessages(Update update) {
    GetBaseInfoFromUpdate getBaseInfoFromUpdate = new GetBaseInfoFromUpdate(update);
    String chatId = getBaseInfoFromUpdate.getChatId();
    SendMessage sendMessage = new SendMessage(chatId, AllText.ERROR_REPLY_TEXT);

    try {
      if (getBaseInfoFromUpdate.isCallbackQuery()) {
        CallbackQuery callbackQuery = getBaseInfoFromUpdate.getCallbackQuery();
        sendMessage = callbackQueryHandler.handler(callbackQuery);
      } else {
        Message message = getBaseInfoFromUpdate.getMessage();
        sendMessage = standardReplyHandler.startHandler(message);
      }

    } catch (Exception e) {
      return sendMessage;
    }
    return sendMessage;
  }

  private boolean processingStates(Update update) {

    long id = 0;
    try {
      StateAdoptiveParent stateAdoptiveParent = stateCommonHandler.getStateAdoptiveParentByChatId(
          id);
//      StateReport stateReport = stateCommonHandler.getStateReportByPetId(id);





    } catch (Exception e) {
      return false;
    }
    return true;
  }


  private void sendReplyMessage(SendMessage replyMessage) {

  }


}
