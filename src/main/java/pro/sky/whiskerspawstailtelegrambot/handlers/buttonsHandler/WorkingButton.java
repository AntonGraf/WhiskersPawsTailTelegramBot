package pro.sky.whiskerspawstailtelegrambot.handlers.buttonsHandler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler.ReportHandler;
import pro.sky.whiskerspawstailtelegrambot.service.StateChangeAdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent;


public class WorkingButton {

  private StateChangeAdoptiveParentService stateChangeAdoptiveParentService;


  private ReportHandler handler;
  String chatId;
  long chatIdL;
  FormReplyMessages formReplyMessages;
  ConfigKeyboard configKeyboard;

  public WorkingButton(String chatId, ReportHandler handler,
      StateChangeAdoptiveParentService stateChangeAdoptiveParentService) {
    this.stateChangeAdoptiveParentService = stateChangeAdoptiveParentService;
    this.handler = handler;
    formReplyMessages = new FormReplyMessages();
    configKeyboard = new ConfigKeyboard();
    this.chatId = chatId;
    chatIdL = Long.parseLong(chatId);
  }

  public SendMessage handleClick(String textMessage) {

    SendMessage sendMessage = null;

    switch (textMessage) {

      case AllText.CANCEL_TEXT:
        sendMessage = clickCancel();
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
   *
   * @return
   */
  private SendMessage clickCancel() {

    stateChangeAdoptiveParentService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.FREE);

    return formReplyMessages.replyMessage(chatId,
        AllText.CANCEL_RETURN_MAIN_MENU_TEXT,
        configKeyboard.initKeyboardOnClickStart());
  }

  /**
   * реакция на кнопку отмена - возврат в главное меню, изменение любого статуса пользователя на
   * FREE
   *
   * @return
   */
  private SendMessage clickRegistration() {

    SendMessage sendMessage = new SendMessage();
    FormReplyMessages formReplyMessages = new FormReplyMessages();
    ConfigKeyboard configKeyboard = new ConfigKeyboard();

//    adoptiveParentService.updateStateAdoptiveParentByChatId(Long.parseLong(chatId),
//        StateAdoptiveParent.FREE);

    return formReplyMessages.replyMessage(chatId,
        AllText.REG_FULL_NAME,
        configKeyboard.initKeyboardOnClickRegistration());
  }

  /**
   * реакция на кнопку отмена - возврат в главное меню, изменение любого статуса пользователя на
   * FREE
   */
  private SendMessage clickSendReport() {

    SendMessage sendMessage = handler.clickButton_SEND_REPORT(chatId);
    stateChangeAdoptiveParentService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.START_REPORT_1);

    return sendMessage;
  }

  private SendMessage clickStartText() {
    if (stateChangeAdoptiveParentService.getStateAdoptiveParentByChatId(chatIdL)
        != null) {
      //приветсвенное сообщение, вылетает только после регистрации
      return formReplyMessages.replyMessage(chatId,
          AllText.WELCOME_MESSAGE_TEXT,
          configKeyboard.initKeyboardOnClickStart());
    }
    return formReplyMessages.replyMessage(chatId, AllText.REGISTRATION_INIT,
        configKeyboard.formReplyKeyboardInOneRowInline(AllText.REGISTRATION_BUTTON));
  }


}
