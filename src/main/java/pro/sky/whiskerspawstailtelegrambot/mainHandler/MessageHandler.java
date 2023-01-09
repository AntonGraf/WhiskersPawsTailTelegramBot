package pro.sky.whiskerspawstailtelegrambot.mainHandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.service.ShelterService;
import pro.sky.whiskerspawstailtelegrambot.service.VolunteerService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.util.ParserToBot;


/**
 * обработка текстового сообщения
 */
@Slf4j
@Component("MessageHandler")
public class MessageHandler implements MainHandler {

  final ConfigKeyboard configKeyboard;
  private final ReportAddHandler reportAddHandler;
  private final FormReplyMessages formReplyMessages;

  private final VolunteerService volunteerService;

  private final ParserToBot parserToBot;
  private final ShelterService shelterService;


  public MessageHandler(ConfigKeyboard configKeyboard, ReportAddHandler reportAddHandler,
                        FormReplyMessages formReplyMessages, VolunteerService volunteerService, ParserToBot parserToBot, ShelterService shelterService) {
    this.configKeyboard = configKeyboard;
    this.reportAddHandler = reportAddHandler;
    this.formReplyMessages = formReplyMessages;
    this.volunteerService = volunteerService;
    this.parserToBot = parserToBot;
    this.shelterService = shelterService;
  }

  /**
   * Метод, который отвечает на входящее сообщение, либо на выбор в меню
   *
   * @param update адейт от пользователя в виде текста
   * @return отправляем ответ
   */
  @Override
  public SendMessage handler(Update update) {
    SendMessage sendMessage = null;
    String chatId = String.valueOf(update.getMessage().getChatId());
    boolean checkUpdate = !update.getMessage().hasText();
    if (!checkUpdate) {
      log.debug("Обработка сообщения в виде текста");
      Message message = update.getMessage();
      String textMessage = message.getText();
      //здесь инжект текст кнопок, любой текст крч
      switch (textMessage) {

        case (AllText.START_TEXT):
          sendMessage = formReplyMessages.replyMessage(message, AllText.WELCOME_MESSAGE_TEXT,
              configKeyboard.initKeyboardOnClickStart());
          break;

        case (AllText.CALL_TO_VOLUNTEER_TEXT): //ответ на позвать волонтера, просто инфа про волонтеров
          sendMessage = formReplyMessages.replyMessage(message, parserToBot.parserVolunteer(volunteerService.getAllVolunteers()),
                  configKeyboard.initKeyboardOnClickStart());
          break;

        case (AllText.SEND_PET_REPORT_TEXT):     // реализация логики отправить отчет
          sendMessage = reportAddHandler.handlerReport(message, AllText.MENU_SEND_PET_REPORT_TEXT,
              configKeyboard.initKeyboardOnClickSendPetReport());
          break;
        case (AllText.HOW_TAKE_DOG):
          sendMessage = formReplyMessages.replyMessage(message, AllText.HOW_TAKE_DOG_SHELTER,
                  configKeyboard.initKeyboardOnClickStart());
          break;
        case (AllText.INFO_SHELTER_TEXT):
                 sendMessage = formReplyMessages.replyMessage(message, shelterService.getOfShelterMessage(1L),
                  configKeyboard.initKeyboardOnClickStart());
          break;

        default:
          sendMessage = new SendMessage(chatId, AllText.UNKNOWN_COMMAND_TEXT);
          break;
      }
    }
    return sendMessage;
  }

  //для удобства
  private String readUpdate(Update update) {
    return update.getMessage().getText();
  }


}


