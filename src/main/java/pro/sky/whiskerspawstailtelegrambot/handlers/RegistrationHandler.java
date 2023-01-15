package pro.sky.whiskerspawstailtelegrambot.handlers;

import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.service.AdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent;

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


  public SendMessage workingState(GetBaseInfoFromUpdate baseInfo,
      StateAdoptiveParent stateAdoptiveParent) {

    SendMessage sendMessage = null;
    AdoptiveParentRecord adoptiveParentRecord = adoptiveParentService.getAdoptiveParentByChatId(
        baseInfo.getChatIdL());

    if (stateAdoptiveParent == NULL) {
     return addToTable(baseInfo);
    }
    if (stateAdoptiveParent == THE_FIRST_STATE) {
      return  handlerWithStatusTheFirstState(baseInfo, adoptiveParentRecord);
    }
    if (stateAdoptiveParent == ONLY_NAME) {
      return handlerWithStatusOnlyName(baseInfo, adoptiveParentRecord);
    }
    return sendMessage;

  }

  /**
   * Метод в который мы попадаем при заполнении имени Здесь мы апдейтим поле имя в бд и ставим
   * следующий статус
   *
   * @param baseInfo
   * @param adoptiveParent
   * @return сообщение о некорректном вводе, либо продвижение дальше, то есть ввод телефона
   */
  public SendMessage handlerWithStatusTheFirstState(GetBaseInfoFromUpdate baseInfo,
      AdoptiveParentRecord adoptiveParent) {
    String text = baseInfo.getTextMessage();
    if (text.length() > 6 && !text.equals(AllText.REGISTRATION_CANCEL)) {
      //обновление имени и статуса
      updateName(adoptiveParent, text);
      return newMessage(baseInfo.getChatId(), AllText.REG_PHONE);
    } else if (text.equals(AllText.REGISTRATION_CANCEL)) {
      adoptiveParentService.deleteAdoptiveParentByID(adoptiveParent.getId());
      return formReplyMessages.replyMessage(baseInfo.getChatId(), AllText.REGISTRATION_INIT,
          configKeyboard.formReplyKeyboardInOneRow("Регистрация"));
    } else {
      return newMessage(baseInfo.getChatId(), "Введите правильное имя, длина больше 6 символов.");
    }
  }

  /**
   * Метод, где мы вводим телефон и в который мы попадаем, когда вводим корректное имя Здесь мы
   * апдейтим телефон и ставим статус FREE, если все ок
   *
   * @param baseInfo
   * @param adoptiveParent
   * @return сообщение о некорректном вводе, либо продвижение дальше, то есть открытие меню
   */
  public SendMessage handlerWithStatusOnlyName(GetBaseInfoFromUpdate baseInfo,
      AdoptiveParentRecord adoptiveParent) {
    String text = baseInfo.getTextMessage();
    if (text.length() > 6 && !text.equals(AllText.REGISTRATION_CANCEL)) {
      AdoptiveParentRecord adoptiveParentOld = adoptiveParentService
          .getAdoptiveParentByChatId(Long.parseLong(baseInfo.getChatId()));
      updatePhone(adoptiveParentOld, text);
      updateState(adoptiveParentOld);
      return formReplyMessages.replyMessage(baseInfo.getChatId(),
          AllText.REGISTRATION_SUCCESS + adoptiveParentOld.getId(),
          configKeyboard.initKeyboardOnClickStart());
    } else if (text.equals(AllText.REGISTRATION_CANCEL)) {
      adoptiveParentService.deleteAdoptiveParentByID(adoptiveParent.getId());
      return formReplyMessages.replyMessage(baseInfo.getChatId(), AllText.REGISTRATION_INIT,
          configKeyboard.formReplyKeyboardInOneRow("Регистрация"));
    } else {
      return newMessage(baseInfo.getChatId(),
          "Введите правильный телефон, длина больше 6 символов.");
    }
  }


  private SendMessage newMessage(String chatId, String textMessage) {
    return new SendMessage(chatId, textMessage);
  }

  //добавляет нового пользователя в таблиц при нажатии кнопки регистрация. Меняет статус и ракладку
  //отправляет новое сообщение
  public SendMessage addToTable(GetBaseInfoFromUpdate baseInfo) {
    AdoptiveParentRecord adoptiveParentRecord = new AdoptiveParentRecord();
    adoptiveParentRecord.setFullName("newParent");
    adoptiveParentRecord.setPhone("somePhone");
    adoptiveParentRecord.setState(THE_FIRST_STATE.name());
    adoptiveParentRecord.setChatId(Long.parseLong(baseInfo.getChatId()));
    adoptiveParentService.addAdoptiveParent(adoptiveParentRecord);
    return formReplyMessages.replyMessage(baseInfo.getChatId(), AllText.REG_FULL_NAME,
        configKeyboard.initKeyboardOnClickRegistration());
  }


  private void updateName(AdoptiveParentRecord adoptiveParent, String name) {
    adoptiveParent.setFullName(name);
    adoptiveParent.setState(StateAdoptiveParent.ONLY_NAME.name());
    adoptiveParentService.updateAdoptiveParent(adoptiveParent.getId(), adoptiveParent);
  }

  private void updatePhone(AdoptiveParentRecord adoptiveParent, String phone) {
    adoptiveParent.setState(StateAdoptiveParent.SUCCESS_REG.name());
    adoptiveParent.setPhone(phone);
    adoptiveParentService.updateAdoptiveParent(adoptiveParent.getId(), adoptiveParent);
  }

  private void updateState(AdoptiveParentRecord adoptiveParent) {
    adoptiveParent.setState(StateAdoptiveParent.FREE.name());
    adoptiveParentService.updateAdoptiveParent(adoptiveParent.getId(),
        adoptiveParent);
  }


}
