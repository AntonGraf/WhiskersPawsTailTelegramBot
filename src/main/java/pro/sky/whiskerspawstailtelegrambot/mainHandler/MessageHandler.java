package pro.sky.whiskerspawstailtelegrambot.mainHandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;
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
  private final ReportService reportService;


  public MessageHandler(ConfigKeyboard configKeyboard, ReportAddHandler reportAddHandler,
      FormReplyMessages formReplyMessages, VolunteerService volunteerService,
      ParserToBot parserToBot, ShelterService shelterService, ReportService reportService) {
    this.configKeyboard = configKeyboard;
    this.reportAddHandler = reportAddHandler;
    this.formReplyMessages = formReplyMessages;
    this.volunteerService = volunteerService;
    this.parserToBot = parserToBot;
    this.shelterService = shelterService;
    this.reportService = reportService;
  }

  /**
   * Метод, который отвечает на входящее сообщение, либо на выбор в меню
   *
   * @param update адейт от пользователя в виде текста
   * @return отправляем ответ
   */
  @Override
  public SendMessage handler(Update update) {

    String chatId = String.valueOf(update.getMessage().getChatId());
    SendMessage sendMessage = new SendMessage(chatId, AllText.ERROR_REPLY_TEXT);
    try {
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

          case (AllText.CANCEL_TEXT)://реакция на кнопку отмена - возврат в главное меню
            sendMessage = formReplyMessages.replyMessage(message,
                AllText.CANCEL_RETURN_MAIN_MENU_TEXT,
                configKeyboard.initKeyboardOnClickStart());
            break;

          case (AllText.CALL_TO_VOLUNTEER_TEXT): //ответ на позвать волонтера, просто инфа про волонтеров
            sendMessage = formReplyMessages.replyMessage(message,
                parserToBot.parserVolunteer(volunteerService.getAllVolunteers()),
                configKeyboard.initKeyboardOnClickStart());
            break;

          //region реализация логики Отправить отчет о питомце
          case (AllText.SEND_PET_REPORT_TEXT):     // нажатие кнопки Отправить отчет о питомце
            sendMessage = reportAddHandler.getSendMessageReport(message, AllText.MENU_SEND_PET_REPORT_TEXT,
                configKeyboard.formReplyKeyboardInOneRow(AllText.SHOW_ALL_YOUR_PET_TEXT, AllText.SEND_REPORT_TEXT, AllText.CANCEL_TEXT));
            break;

          case (AllText.SHOW_ALL_YOUR_PET_TEXT):     // нажатие кнопки показать всех взятых животных
            sendMessage = reportAddHandler.getSendMessageReport(message,
                reportService.showAllAdoptedPets(message),
                configKeyboard.formReplyKeyboardInOneRow(AllText.SHOW_ALL_YOUR_PET_TEXT, AllText.SEND_REPORT_TEXT, AllText.CANCEL_TEXT));
            break;

          case (AllText.SEND_REPORT_TEXT):     // нажатие кнопки отправить отчет
            sendMessage = reportAddHandler.sendReport(message, AllText.DESCRIPTION_SEND_REPORT_TEXT,
                configKeyboard.formReplyKeyboardInOneRow(AllText.SEND_TEXT, AllText.CANCEL_TEXT));
            break;
          //endregion

          case (AllText.HOW_TAKE_DOG):
            sendMessage = formReplyMessages.replyMessage(message, AllText.HOW_TAKE_DOG_SHELTER,
                configKeyboard.initKeyboardOnClickStart());
            break;
          case (AllText.INFO_SHELTER_TEXT):
            sendMessage = formReplyMessages.replyMessage(message,
                shelterService.getOfShelterMessage(1L),
                configKeyboard.initKeyboardOnClickStart());
            break;

          default:
            sendMessage = new SendMessage(chatId, AllText.UNKNOWN_COMMAND_TEXT);
            break;
        }
      }
    } finally {
      return sendMessage;
    }
  }

    //добавляет нового пользвователя в таблиц при нажатии кнопки регистрация. Меняет статус и ракладку
    //отправляет новое сообщение
    private SendMessage addToTable(Message message, String chatId) {
        AdoptiveParentRecord adoptiveParentRecord = new AdoptiveParentRecord();
        adoptiveParentRecord.setFullName("newParent");
        adoptiveParentRecord.setPhone("somePhone");
        adoptiveParentRecord.setState(StatusRegistration.THE_FIRST_STATE.name());
        adoptiveParentRecord.setChatId(Long.parseLong(chatId));
        adoptiveParentService.addAdoptiveParent(adoptiveParentRecord);
        return formReplyMessages.replyMessage(message, AllText.REG_FULL_NAME, configKeyboard.initKeyboardOnClickRegistration());
    }

    //проверяет есть ли усыновитель в таблице по чат айди
    private AdoptiveParentRecord checkExistAdoptiveParent(String chatId) {
        return adoptiveParentService
                .findAdoptiveParentByChatId(Long.parseLong(chatId));
    }
}

    //проверяет статус усыновителя в таблице по чат айди
    private String checkStatusAdoptiveParent(String chatId) {
        String status = null;
        AdoptiveParentRecord adoptiveParentRecord = adoptiveParentService
                .findAdoptiveParentByChatId(Long.parseLong(chatId));
        if (adoptiveParentRecord == null || adoptiveParentRecord.getState() == null) status = "NOSTATUS";
        else status = adoptiveParentRecord.getState();
        return status;
    }

    private SendMessage newMessage(String chatId, String textMessage) {
        return new SendMessage(chatId, textMessage);
    }

    private void updateName(AdoptiveParentRecord adoptiveParent, String name) {
        AdoptiveParentRecord newAdoptiveParent = new AdoptiveParentRecord();
        newAdoptiveParent.setFullName(name);
        newAdoptiveParent.setPhone("somePhone");
        newAdoptiveParent.setState(StatusRegistration.ONLY_NAME.name());
        newAdoptiveParent.setChatId(adoptiveParent.getChatId());
        adoptiveParentService.updateAdoptiveParent(adoptiveParent.getId(),
                newAdoptiveParent);
    }

    private void updatePhone(AdoptiveParentRecord adoptiveParent, String phone) {
        AdoptiveParentRecord adoptiveParentRecord = new AdoptiveParentRecord();
        adoptiveParentRecord.setFullName(adoptiveParent.getFullName());
        adoptiveParentRecord.setPhone(phone);
        adoptiveParentRecord.setState(StatusRegistration.SUCCESS.name());
        adoptiveParentRecord.setChatId(adoptiveParent.getChatId());
        adoptiveParentService.updateAdoptiveParent(adoptiveParent.getId(),
                adoptiveParentRecord);
    }






