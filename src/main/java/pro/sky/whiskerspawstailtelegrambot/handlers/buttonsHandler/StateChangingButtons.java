package pro.sky.whiskerspawstailtelegrambot.handlers.buttonsHandler;

import static pro.sky.whiskerspawstailtelegrambot.service.enums.StateAdoptiveParent.*;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler.ReportHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers.StateHandler;
import pro.sky.whiskerspawstailtelegrambot.loger.FormLogInfo;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.service.StateService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.service.enums.StateAdoptiveParent;

/**
 * Тут кнопки меняющие состояния
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StateChangingButtons {

  StateHandler stateHandler;
  final StateService stateService;
  final ReportHandler reportHandler;

  String chatId;
  long chatIdL;
  FormReplyMessages formReplyMessages;
  ConfigKeyboard configKeyboard;
  final GetBaseInfoFromUpdate baseInfo;


  public StateChangingButtons(StateHandler stateHandler, GetBaseInfoFromUpdate baseInfo,
      ReportHandler reportHandler,
      StateService stateService) {
    this.stateHandler = stateHandler;
    this.stateService = stateService;
    this.reportHandler = reportHandler;
    this.baseInfo = baseInfo;
    init();
  }

  private void init() {
    formReplyMessages = new FormReplyMessages();
    configKeyboard = new ConfigKeyboard();
    chatIdL = baseInfo.getChatIdL();
    chatId = baseInfo.getChatId();
  }

  /**
   * выбо кнопок меняющих состояние
   */
  public SendMessage handleClick(String textMessage) {
    log.info(FormLogInfo.getInfo());
    textMessage = textMessage != null ? textMessage : "";//костыль от nullPointer при отправке фото без текста
    SendMessage sendMessage = null;
    switch (textMessage) {

      case AllText.CANCEL_TEXT:
        sendMessage = clickCancel();
        break;
      case AllText.CANCEL_CREATE_REPORT_TEXT:
        sendMessage = clickCancelSendReport();
        break;
      case AllText.REGISTRATION_BUTTON:
        sendMessage = clickRegistration();
        break;
      case AllText.SEND_PET_REPORT_TEXT:
        sendMessage = clickSendReport();
        break;
      case AllText.START_TEXT:
        sendMessage = clickStartText();
        break;
    }
    return sendMessage;
  }

  /**
   * реакция на кнопку отмена - возврат в главное меню, изменение любого статуса пользователя на
   * FREE
   */
  private SendMessage clickCancel() {

    stateService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.FREE);

    return formReplyMessages.replyMessage(chatId,
        AllText.CANCEL_RETURN_MAIN_MENU_TEXT,
        configKeyboard.initKeyboardOnClickStart());
  }

  /**
   * реакция на кнопку отмена создания отчета смена состояния на CANCEL_CREATE_REPORT
   */
  private SendMessage clickCancelSendReport() {
    StateAdoptiveParent stateAdoptiveParent = stateService.updateStateAdoptiveParentByChatId(
        chatIdL, CANCEL_CREATE_REPORT);
    if (stateAdoptiveParent == NULL) {
      formReplyMessages.replyMessage(chatId,
          AllText.ERROR_REPLY_TEXT, configKeyboard.initKeyboardOnClickStart());
    }
    return reportHandler.workingState(baseInfo,
        StateAdoptiveParent.CANCEL_CREATE_REPORT);
  }

  /**
   * реакция на кнопку регистрация, выскакивает инлайн клавиатура с регистрацией
   */
  private SendMessage clickRegistration() {
    StateAdoptiveParent state = stateService.getStateAdoptiveParentByChatId(chatIdL);
    if (state != NULL) {
      return new SendMessage(chatId, AllText.ALREADY_REGISTERED);
    }
//    if(state == null){
//      state = NULL;
//    }
    return stateHandler.processByState(baseInfo,state);

  }

  /**
   * реакция на кнопку отправить отчет, отправня точка для обработки отчета
   */
  private SendMessage clickSendReport() {
    StateAdoptiveParent state = START_0;
    StateAdoptiveParent stateAdoptiveParent = stateService
        .updateStateAdoptiveParentByChatId(chatIdL, state);
    if (stateAdoptiveParent == NULL) {
      return formReplyMessages.replyMessage(chatId,
          AllText.REGISTRATION_INIT, configKeyboard.formReplyKeyboardInOneRow(AllText.START_TEXT));
    }
    return stateHandler.processByState(baseInfo, state);
  }

  /**
   * рекация на /start
   */
  private SendMessage clickStartText() {
    if (stateService.getStateAdoptiveParentByChatId(chatIdL)
        != NULL) {
      //приветсвенное сообщение, вылетает только после регистрации
      return formReplyMessages.replyMessage(chatId,
          AllText.WELCOME_MESSAGE_TEXT,
          configKeyboard.initKeyboardOnClickStart());
    }
    return formReplyMessages.replyMessage(chatId, AllText.REGISTRATION_INIT,
        configKeyboard.formReplyKeyboardInOneRowInline(AllText.REGISTRATION_BUTTON));
  }


}
