package pro.sky.whiskerspawstailtelegrambot.handlers.buttonsHandler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler.ReportHandler;
import pro.sky.whiskerspawstailtelegrambot.service.StateChangeAdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent;


public class WorkingButton {

  private StateChangeAdoptiveParentService stateChangeAdoptiveParentService;

  private Message message;
  private ReportHandler handler;
  long chatId;
  FormReplyMessages formReplyMessages;
  ConfigKeyboard configKeyboard;
  public WorkingButton(Message message, ReportHandler handler,
      StateChangeAdoptiveParentService stateChangeAdoptiveParentService) {
    this.stateChangeAdoptiveParentService = stateChangeAdoptiveParentService;
    this.message = message;
    this.handler = handler;
    chatId = message.getChatId();
    formReplyMessages = new FormReplyMessages();
    configKeyboard = new ConfigKeyboard();
  }

  public void handleClick(Message message, String textMessage, ReportHandler reportHandler) {

    switch (textMessage) {

      case AllText.CANCEL_TEXT:
        clickCancel();
        break;
      case AllText.REGISTRATION_BUTTON:
        clickRegistration();
        break;
      case AllText.SEND_PET_REPORT_TEXT:
        clickSendReport();
        break;
      case AllText.START_TEXT:
        clickStartText();
        break;

    }


  }

  /**
   * реакция на кнопку отмена - возврат в главное меню, изменение любого статуса пользователя на
   * FREE
   *
   * @return
   */
  public SendMessage clickCancel() {

    stateChangeAdoptiveParentService.updateStateAdoptiveParentByChatId(chatId,
        StateAdoptiveParent.FREE);

    return formReplyMessages.replyMessage(message,
        AllText.CANCEL_RETURN_MAIN_MENU_TEXT,
        configKeyboard.initKeyboardOnClickStart());

  }

  /**
   * реакция на кнопку отмена - возврат в главное меню, изменение любого статуса пользователя на
   * FREE
   *
   * @return
   */
  public SendMessage clickRegistration() {

    SendMessage sendMessage = new SendMessage();
    FormReplyMessages formReplyMessages = new FormReplyMessages();
    ConfigKeyboard configKeyboard = new ConfigKeyboard();

//    adoptiveParentService.updateStateAdoptiveParentByChatId(Long.parseLong(chatId),
//        StateAdoptiveParent.FREE);

    return formReplyMessages.replyMessage(message,
        AllText.REG_FULL_NAME,
        configKeyboard.initKeyboardOnClickRegistration());
  }

  /**
   * реакция на кнопку отмена - возврат в главное меню, изменение любого статуса пользователя на
   * FREE
   */
  public SendMessage clickSendReport() {

    SendMessage sendMessage = handler.clickButton_SEND_REPORT(message);
    stateChangeAdoptiveParentService.updateStateAdoptiveParentByChatId(chatId,
        StateAdoptiveParent.START_SEND_REPORT);

    return sendMessage;
  }

  public SendMessage clickStartText() {
    if (stateChangeAdoptiveParentService.getStateAdoptiveParentByChatId(chatId)
        != null) {
      //приветсвенное сообщение, вылетает только после регистрации
      return formReplyMessages.replyMessage(message,
          AllText.WELCOME_MESSAGE_TEXT,
          configKeyboard.initKeyboardOnClickStart());
    }
    return formReplyMessages.replyMessage(message,AllText.REGISTRATION_INIT,
        configKeyboard.formReplyKeyboardInOneRowInline(AllText.REGISTRATION_BUTTON));
  }




}
