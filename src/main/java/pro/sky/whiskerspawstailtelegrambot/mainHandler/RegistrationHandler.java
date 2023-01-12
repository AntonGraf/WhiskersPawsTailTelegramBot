package pro.sky.whiskerspawstailtelegrambot.mainHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.service.AdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent;

/**
 * Обработка сообщений при регистрации
 */

@Slf4j
@Component
public class RegistrationHandler {

  private final AdoptiveParentService adoptiveParentService;
  private final FormReplyMessages formReplyMessages;
  private final ConfigKeyboard configKeyboard;

  public RegistrationHandler(
      AdoptiveParentService adoptiveParentService, FormReplyMessages formReplyMessages,
      ConfigKeyboard configKeyboard) {
    this.adoptiveParentService = adoptiveParentService;
    this.formReplyMessages = formReplyMessages;
    this.configKeyboard = configKeyboard;
  }

  public SendMessage handlerWithStatusTheFirstState(Message message,AdoptiveParentRecord adoptiveParent,
      String text, String chatId) {
    if (text.length() > 6 && !text.equals(AllText.REGISTRATION_CANCEL)) {
      //обновление имени и статуса
      updateName(adoptiveParent, text);
      return newMessage(chatId, AllText.REG_PHONE);
    } else if (text.equals(AllText.REGISTRATION_CANCEL)) {
      adoptiveParentService.deleteAdoptiveParentByID(adoptiveParent.getId());
      return formReplyMessages.replyMessage(message, AllText.REGISTRATION_INIT,
          configKeyboard.formReplyKeyboardInOneRow("Регистрация"));
    } else {
      return newMessage(chatId, "Введите правильное имя, длина больше 6 символов.");
    }
  }

  public SendMessage handlerWithStatusOnlyName(Message message, AdoptiveParentRecord adoptiveParent,
      String text, String chatId) {
    if (text.length() > 6 && !text.equals(AllText.REGISTRATION_CANCEL)) {
      AdoptiveParentRecord adoptiveParentOld = adoptiveParentService
          .getAdoptiveParentByChatId(Long.parseLong(chatId));
      updatePhone(adoptiveParentOld, text);
      updateState(adoptiveParent);
      return formReplyMessages.replyMessage(message,
          AllText.REGISTRATION_SUCCESS + adoptiveParentOld.getId(),
          configKeyboard.initKeyboardOnClickStart());

    } else if (text.equals(AllText.REGISTRATION_CANCEL)) {
      adoptiveParentService.deleteAdoptiveParentByID(adoptiveParent.getId());
      return formReplyMessages.replyMessage(message, AllText.REGISTRATION_INIT,
          configKeyboard.formReplyKeyboardInOneRow("Регистрация"));
    } else {
      return newMessage(chatId, "Введите правильный телефон, длина больше 6 символов.");
    }
  }


  private SendMessage newMessage(String chatId, String textMessage) {
    return new SendMessage(chatId, textMessage);
  }

  //добавляет нового пользвователя в таблиц при нажатии кнопки регистрация. Меняет статус и ракладку
  //отправляет новое сообщение
  SendMessage addToTable(Message message, String chatId) {
    AdoptiveParentRecord adoptiveParentRecord = new AdoptiveParentRecord();
    adoptiveParentRecord.setFullName("newParent");
    adoptiveParentRecord.setPhone("somePhone");
    adoptiveParentRecord.setState(StateAdoptiveParent.THE_FIRST_STATE.name());
    adoptiveParentRecord.setChatId(Long.parseLong(chatId));
    adoptiveParentService.addAdoptiveParent(adoptiveParentRecord);
    return formReplyMessages.replyMessage(message, AllText.REG_FULL_NAME,
        configKeyboard.initKeyboardOnClickRegistration());
  }


  private void updateName(AdoptiveParentRecord adoptiveParent, String name) {
    AdoptiveParentRecord newAdoptiveParent = new AdoptiveParentRecord();
    newAdoptiveParent.setFullName(name);
    newAdoptiveParent.setPhone("somePhone");
    newAdoptiveParent.setState(StateAdoptiveParent.ONLY_NAME.name());
    newAdoptiveParent.setChatId(adoptiveParent.getChatId());
    adoptiveParentService.updateAdoptiveParent(adoptiveParent.getId(),
        newAdoptiveParent);
  }

  private void updatePhone(AdoptiveParentRecord adoptiveParent, String phone) {
    AdoptiveParentRecord adoptiveParentRecord = new AdoptiveParentRecord();
    adoptiveParentRecord.setFullName(adoptiveParent.getFullName());
    adoptiveParentRecord.setPhone(phone);
    adoptiveParentRecord.setState(StateAdoptiveParent.SUCCESS_REG.name());
    adoptiveParentRecord.setChatId(adoptiveParent.getChatId());
    adoptiveParentService.updateAdoptiveParent(adoptiveParent.getId(),
        adoptiveParentRecord);
  }

  private void updateState(AdoptiveParentRecord adoptiveParent) {
    AdoptiveParentRecord adoptiveParentRecord = new AdoptiveParentRecord();
    adoptiveParentRecord.setFullName(adoptiveParent.getFullName());
    adoptiveParentRecord.setPhone(adoptiveParent.getPhone());
    adoptiveParentRecord.setState(StateAdoptiveParent.FREE.name());
    adoptiveParentRecord.setChatId(adoptiveParent.getChatId());
    adoptiveParentService.updateAdoptiveParent(adoptiveParent.getId(),
        adoptiveParentRecord);
  }


}
