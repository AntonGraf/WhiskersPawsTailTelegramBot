package pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.handlers.CallbackQueryHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.StandardReplyHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.buttonsHandler.WorkingButton;
import pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler.ReportHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers.StateAdoptiveParentHandlerImpl;
import pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers.StateCommonHandlerImpl;
import pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers.StateReportHandlerImpl;
import pro.sky.whiskerspawstailtelegrambot.service.StateChangeAdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent;

@Slf4j
@Component("MainHandler")
public class MainHandlerImpl implements MainHandler {

  private final StandardReplyHandler standardReplyHandler;
  private final CallbackQueryHandler callbackQueryHandler;
  private final StateCommonHandlerImpl stateCommonHandler;
  private final ReportHandler reportHandler;
  private final StateChangeAdoptiveParentService stateChangeAdoptiveParentService;


  String chatId;
  long chatIdL;
  SendMessage sendMessage;
  GetBaseInfoFromUpdate getBaseInfoFromUpdate;
  private final StateAdoptiveParentHandlerImpl stateAdoptiveParentHandler;

  public MainHandlerImpl(StandardReplyHandler standardReplyHandler,
      CallbackQueryHandler callbackQueryHandler,
      StateAdoptiveParentHandlerImpl stateAdoptiveParentHandler,
      StateReportHandlerImpl stateReportHandler, StateCommonHandlerImpl stateCommonHandler,
      ReportHandler reportHandler,
      StateChangeAdoptiveParentService stateChangeAdoptiveParentService,
      StateAdoptiveParentHandlerImpl stateAdoptiveParentHandler1) {
    this.standardReplyHandler = standardReplyHandler;
    this.callbackQueryHandler = callbackQueryHandler;
    this.stateCommonHandler = stateCommonHandler;
    this.reportHandler = reportHandler;
    this.stateChangeAdoptiveParentService = stateChangeAdoptiveParentService;
    this.stateAdoptiveParentHandler = stateAdoptiveParentHandler1;
  }

  /**
   * Обрабатываем Update делим его по типу сообщения
   */
  @Override
  public SendMessage process(Update update) {

    getBaseInfoFromUpdate = new GetBaseInfoFromUpdate(update);
    chatId = getBaseInfoFromUpdate.getChatId();
    chatIdL = Long.parseLong(chatId);
    sendMessage = new SendMessage(chatId, AllText.ERROR_REPLY_TEXT);

    StateAdoptiveParent stateAdoptiveParent = checkingState(update);
    if (stateAdoptiveParent != StateAdoptiveParent.FREE) {
      return processingStates(stateAdoptiveParent);
    }
    return processingIncomingMessages(update);

  }

  /**
   * Обработка входящих сообщений из update
   */
  private SendMessage processingIncomingMessages(Update update) {

    String textMessage = getBaseInfoFromUpdate.getTextMessage();
    WorkingButton workingButton = new WorkingButton(chatId, reportHandler,
        stateChangeAdoptiveParentService);

    try {

      sendMessage = workingButton.handleClick(textMessage);
      if (sendMessage != null) {
        return sendMessage;
      }

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

  private SendMessage processingStates(StateAdoptiveParent stateAdoptiveParent) {

    if (getBaseInfoFromUpdate.isCallbackQuery()) {
      CallbackQuery callbackQuery = getBaseInfoFromUpdate.getCallbackQuery();
      sendMessage = stateCommonHandler.workingState(chatIdL, stateAdoptiveParent);
    } else {
      Message message = getBaseInfoFromUpdate.getMessage();
      sendMessage = stateCommonHandler.workingState(chatIdL, stateAdoptiveParent);
    }

    return sendMessage;

  }


  private StateAdoptiveParent checkingState(Update update) {

    StateAdoptiveParent stateAdoptiveParent = stateCommonHandler.getStateAdoptiveParentByChatId(
        chatIdL);
    return stateAdoptiveParent;
  }


}
