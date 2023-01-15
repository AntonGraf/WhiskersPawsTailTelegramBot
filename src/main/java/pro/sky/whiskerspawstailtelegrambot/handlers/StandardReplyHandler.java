package pro.sky.whiskerspawstailtelegrambot.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler.ReportHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers.StateHandler;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.service.AdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.service.ShelterService;
import pro.sky.whiskerspawstailtelegrambot.service.StateService;
import pro.sky.whiskerspawstailtelegrambot.service.VolunteerService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.util.ParserToBot;
import pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent;

import static org.glassfish.grizzly.http.util.Ascii.isDigit;

/**
 * Обработчик стандартных сообщений от пользователя, в том числе и из обычной клавиатуры
 */
@Slf4j
@Component
public class StandardReplyHandler {

  private final FormReplyMessages formReplyMessages;
  private final ConfigKeyboard configKeyboard;
  private final VolunteerService volunteerService;
  private final ReportHandler reportHandler;
  private final ParserToBot parserToBot;
  private final ShelterService shelterService;

  private final AdoptiveParentService adoptiveParentService;

  private final RegistrationHandler registrationHandler;
  private final StateHandler stateCommonHandler;

  private final StateService stateService;

  public StandardReplyHandler(FormReplyMessages formReplyMessages, ConfigKeyboard configKeyboard,
      VolunteerService volunteerService, ReportHandler reportHandler, ParserToBot parserToBot,
      ShelterService shelterService, AdoptiveParentService adoptiveParentService,
      RegistrationHandler registrationHandler, StateHandler stateCommonHandler,
      StateService stateService) {
    this.formReplyMessages = formReplyMessages;
    this.configKeyboard = configKeyboard;
    this.volunteerService = volunteerService;
    this.reportHandler = reportHandler;
    this.parserToBot = parserToBot;
    this.shelterService = shelterService;
    this.adoptiveParentService = adoptiveParentService;
    this.registrationHandler = registrationHandler;
    this.stateCommonHandler = stateCommonHandler;
    this.stateService = stateService;
  }

  /**
   * обработка текста отправленого пользователем
   *
   * @param message сообщение из Update
   * @return SendMessage
   */
  public SendMessage startHandler(GetBaseInfoFromUpdate baseInfo, Message message) {

    SendMessage sendMessage = null;

    log.debug("Вызов метода handler класса" + this.getClass().getName());
    String chatId = message.getChatId().toString();
    String textMessage = message.getText();
    if (isDigit(textMessage.charAt(0))) {
      menuInfo(message);
    } // Проверка команды на цифру и передача в цифровой метод

    StateAdoptiveParent stateAdoptiveParent = stateCommonHandler
        .getStateAdoptiveParentByChatId(baseInfo.getChatIdL());
    if (stateAdoptiveParent != null && stateAdoptiveParent != StateAdoptiveParent.FREE) {
      return stateCommonHandler.processByState(baseInfo, stateAdoptiveParent);
    }

    //здесь инжект текст кнопок, любой текст крч
    switch (textMessage) {

      case (AllText.CALL_TO_VOLUNTEER_TEXT): //ответ на позвать волонтера, просто инфа про волонтеров
        return new SendMessage(chatId,
            parserToBot.parserVolunteer(volunteerService.getAllVolunteers()));

      case (AllText.HOW_TAKE_DOG):
        return sendMessage = formReplyMessages.replyMessage(message, AllText.HOW_TAKE_DOG_SHELTER,
            configKeyboard.initKeyboardOnClickStart());

      case (AllText.INFO_SHELTER_TEXT):
        return sendMessage = formReplyMessages.replyMessage(message,
            AllText.INFO,
            configKeyboard.initKeyboardOnClickStart());

      case (AllText.SHOW_ME_ID):
        AdoptiveParentRecord adoptiveParentRecord =
            adoptiveParentService.getAdoptiveParentByChatId(Long.parseLong(chatId));
        if (adoptiveParentRecord != null) {
          //проверяем если есть в бд, то просто id
          return new SendMessage(chatId, AllText.SHOW_ID_OK + adoptiveParentRecord.getId());
        }
        return new SendMessage(chatId, AllText.SHOW_ID_FAILED);

      default:
        return sendMessage = new SendMessage(chatId, AllText.UNKNOWN_COMMAND_TEXT);
    }

  }

  private SendMessage menuInfo(Message message) {
    String number = message.getText();
    SendMessage sendMessage = null;
    switch (number) {
      case ("1"): // Выбор информации из списка меню информации
        sendMessage = formReplyMessages.replyMessage(message,
            shelterService.getOfShelterMessage((byte) 1),
            configKeyboard.initKeyboardOnClickStart());
        break;
      case ("2"):
        sendMessage = formReplyMessages.replyMessage(message,
            shelterService.getOfShelterMessage((byte) 2),
            configKeyboard.initKeyboardOnClickStart());
        break;
      case ("3"):
        sendMessage = formReplyMessages.replyMessage(message,
            shelterService.getOfShelterMessage((byte) 3),
            configKeyboard.initKeyboardOnClickStart());
        break;
      case ("4"):
        sendMessage = formReplyMessages.replyMessage(message,
            shelterService.getOfShelterMessage((byte) 4),
            configKeyboard.initKeyboardOnClickStart());
        break;
      case ("5"):
        sendMessage = formReplyMessages.replyMessage(message,
            shelterService.getOfShelterMessage((byte) 5),
            configKeyboard.initKeyboardOnClickStart());
        break;
      case ("6"):
        sendMessage = formReplyMessages.replyMessage(message,
            shelterService.getOfShelterMessage((byte) 6),
            configKeyboard.initKeyboardOnClickStart());
        break;
      case ("7"):
        sendMessage = formReplyMessages.replyMessage(message,
            shelterService.getOfShelterMessage((byte) 7),
            configKeyboard.initKeyboardOnClickStart());
        break;
    }
    return sendMessage;

  }

}
