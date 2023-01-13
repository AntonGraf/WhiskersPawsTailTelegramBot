package pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler.mainHandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.handlers.CallbackQueryHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.StandardReplyHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers.StateAdoptiveParentHandlerImpl;
import pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers.StateReportHandlerImpl;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.util.StateReport;

@Slf4j
@Component("MainHandler")
public class MainHandlerImpl implements MainHandler {

  private final StandardReplyHandler standardReplyHandler;
  private final CallbackQueryHandler callbackQueryHandler;
  private  final StateAdoptiveParentHandlerImpl stateAdoptiveParentHandler;
  private  final StateReportHandlerImpl stateReportHandler;



  public MainHandlerImpl(StandardReplyHandler standardReplyHandler,
      CallbackQueryHandler callbackQueryHandler,
      StateAdoptiveParentHandlerImpl stateAdoptiveParentHandler,
      StateReportHandlerImpl stateReportHandler) {
    this.standardReplyHandler = standardReplyHandler;
    this.callbackQueryHandler = callbackQueryHandler;
    this.stateAdoptiveParentHandler = stateAdoptiveParentHandler;
    this.stateReportHandler = stateReportHandler;
  }

  /**
   * Обрабатываем Update делим его по типу сообщения
   */
  @Override
  public SendMessage process(Update update) {

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

  private SendMessage processingStates(Message message) {

    try {
      StateAdoptiveParent stateAdoptiveParent = stateAdoptiveParentHandler.startHandler(message);
      StateReport stateReport = stateReportHandler.getStateReportByPetId(message);

    } catch (Exception e) {
      return sendMessage;
    }
    return sendMessage;
  }

  private void sendReplyMessage(SendMessage replyMessage) {

  }


}
