package pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.handlers.CallbackQueryHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.StandardReplyHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.buttonsHandler.StateChangingButtons;
import pro.sky.whiskerspawstailtelegrambot.handlers.mediaContentHandler.MediaHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler.ReportHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers.StateHandler;
import pro.sky.whiskerspawstailtelegrambot.service.StateService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.util.enums.StateAdoptiveParent;

@Slf4j
@Component("MainHandler")
public class MainHandlerImpl implements MainHandler {

  private final StandardReplyHandler standardReplyHandler;
  private final CallbackQueryHandler callbackQueryHandler;
  private final MediaHandler mediaHandler;
  private final StateHandler stateHandler;
  private final ReportHandler reportHandler;
  private final StateService stateService;


  SendMessage sendMessage;

  public MainHandlerImpl(StandardReplyHandler standardReplyHandler,
      CallbackQueryHandler callbackQueryHandler, MediaHandler mediaHandler,
      StateHandler stateHandler,
      ReportHandler reportHandler,
      StateService stateService) {
    this.standardReplyHandler = standardReplyHandler;
    this.callbackQueryHandler = callbackQueryHandler;
    this.mediaHandler = mediaHandler;
    this.stateHandler = stateHandler;
    this.reportHandler = reportHandler;
    this.stateService = stateService;
  }

  /**
   * Обрабатываем Update делим его по типу сообщения
   */
  @Override
  public SendMessage process(Update update) {

    GetBaseInfoFromUpdate baseInfo = new GetBaseInfoFromUpdate(update);
    sendMessage = new SendMessage(baseInfo.getChatId(), AllText.ERROR_REPLY_TEXT);

    return processingIncomingMessages(baseInfo);
  }

  /**
   * Обработка входящих сообщений из update
   */
  private SendMessage processingIncomingMessages(GetBaseInfoFromUpdate baseInfo) {

    String textMessage = baseInfo.getTextMessage();
    StateChangingButtons stateChangingButtons = new StateChangingButtons(stateHandler, baseInfo,
        reportHandler,
        stateService);

    try {

      sendMessage = stateChangingButtons.handleClick(
          textMessage);//вначале обрабатываем кнопки которые меняют статус
      if (sendMessage != null) {
        return sendMessage;
      }

      StateAdoptiveParent stateAdoptiveParent = stateService// затем если статус не FREE обрабатываем все запросы в соответсвующих Handler
          .getStateAdoptiveParentByChatId(baseInfo.getChatIdL());
      if (stateAdoptiveParent != null && stateAdoptiveParent != StateAdoptiveParent.FREE) {
        return stateHandler.processByState(baseInfo, stateAdoptiveParent);
      }

      if (baseInfo.isCallbackQuery()) {//обрабатываем CallbackQuery если стаус FREE
        return callbackQueryHandler.handler(baseInfo);
      } else if (baseInfo.getMessage().hasPhoto() || baseInfo.getMessage()
          .hasDocument()) {//обрабатываем медиа если стаус FREE
        return mediaHandler.workingState(baseInfo);
      } else {
        Message message = baseInfo.getMessage();//обрабатываем стандартную клавиатуру если стаус FREE
        return sendMessage = standardReplyHandler.startHandler(baseInfo, message);
      }


    } catch (Exception e) {
      return sendMessage = new SendMessage(baseInfo.getChatId(), e.getMessage());
    }
  }


}
