package pro.sky.whiskerspawstailtelegrambot.mainHandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
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

    String chatId = update.getMessage() != null ? String.valueOf(update.getMessage().getChatId())
        : update.getCallbackQuery().getMessage().getChatId().toString();
    SendMessage sendMessage = new SendMessage(chatId, AllText.ERROR_REPLY_TEXT);
    if (update.getCallbackQuery() != null) {
      return sendMessage = callbackQueryHandler(update.getCallbackQuery());
    }
//    String chatId = String.valueOf(update.getMessage().getChatId());

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
            sendMessage = reportAddHandler.getSendMessageReport(message,
                AllText.MENU_SEND_PET_REPORT_TEXT,
                configKeyboard.formReplyKeyboardInlineInOneRow(AllText.SHOW_ALL_YOUR_PET_TEXT,
                    AllText.SEND_REPORT_TEXT, AllText.CANCEL_TEXT));
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

  public SendMessage callbackQueryHandler(CallbackQuery callbackQuery) {

    SendMessage sendMessage = null;
    String textMessage = callbackQuery.getData();
    Message message = callbackQuery.getMessage();
    switch (textMessage) {

      case (AllText.CANCEL_TEXT)://реакция на кнопку отмена - возврат в главное меню
        sendMessage = formReplyMessages.replyMessage(message,
            AllText.CANCEL_RETURN_MAIN_MENU_TEXT,
            configKeyboard.initKeyboardOnClickStart());
        break;

      case (AllText.SHOW_ALL_YOUR_PET_TEXT):     // нажатие кнопки показать всех взятых животных
        sendMessage = reportAddHandler.clickButton_SHOW_ALL_YOUR_PET(message);
        break;

      case (AllText.SEND_REPORT_TEXT):     // нажатие кнопки отправить отчет
        sendMessage = reportAddHandler.clickButton_SEND_REPORT(message);
        break;
    }
    return sendMessage;
  }

}







