package pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler;

import static pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText.CANCEL_RETURN_MAIN_MENU_TEXT;
import static pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText.CANCEL_TEXT;
import static pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText.DESCRIPTION_SEND_REPORT_TEXT;
import static pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText.NO_PHOTO_SEND_REPORT_TEXT;
import static pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText.NO_TEXT_SEND_REPORT_TEXT;
import static pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText.YOU_HAVE_NO_ADOPTED_PETS_TEXT;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers.StateCommonHandlerImpl;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.service.AdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;
import pro.sky.whiskerspawstailtelegrambot.service.StateChangeAdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent;

/**
 * обработка репорт на будующее
 */
@Slf4j
@Component
public class ReportHandler {

  private final FormReplyMessages formReplyMessages;
  private final ReportService reportService;
  private final ConfigKeyboard configKeyboard;
  private SendMessage sendMessage = null;
  private final AdoptiveParentService adoptiveParentService;
  private final StateCommonHandlerImpl stateCommonHandler;
  StateChangeAdoptiveParentService stateChangeAdoptiveParentService;


  public ReportHandler(FormReplyMessages formReplyMessages, ReportService reportService,
      ConfigKeyboard configKeyboard, AdoptiveParentService adoptiveParentService,
      StateCommonHandlerImpl stateCommonHandler) {
    this.formReplyMessages = formReplyMessages;
    this.reportService = reportService;
    this.configKeyboard = configKeyboard;
    this.adoptiveParentService = adoptiveParentService;
    this.stateCommonHandler = stateCommonHandler;
  }

  public SendMessage workingState(Message message, StateAdoptiveParent stateAdoptiveParent) {

    long chatIdL = message.getChatId();
    String chatId = message.getChatId().toString();
    ReportRecord reportRecord = getAndUpdateReportRecord(chatIdL, message);

    WorkingState workingState = new WorkingState(message, reportService,
        stateChangeAdoptiveParentService);

    sendMessage = workingState.work(reportRecord, stateAdoptiveParent);


  }

  //region clickButton

  public ReportRecord getAndUpdateReportRecord(long chatId, Message message) {

    ReportRecord reportRecord = reportService.getReportInStartStateByChatId(chatId);

    if (message.hasPhoto() || message.hasDocument()) {

      return reportRecord;
    }

    String textMessage = message.getText();


  }

  public ReportRecord updateReport(ReportRecord reportRecord) {

    reportRecord.setPhotoPet(newReportRecord.getPhotoPet());
    reportRecord.setDiet(newReportRecord.getDiet());
    reportRecord.setReportAboutFeelings(newReportRecord.getReportAboutFeelings());
    reportRecord.setReportAboutHabits(newReportRecord.getReportAboutHabits());
    reportRecord.setStateReport(newReportRecord.getStateReport());

    return reportMapper.toRecord(reportRepository.save(reportMapper.toEntity(oldReportRecord)));
  }

  /**
   * Метод обрабатывает нажатие на кнопку показать всех отправить отчет и изменят статус
   * пользователя на ожидание отправки отчета
   */
  public SendMessage clickButton_SEND_REPORT(String chatId) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());

    SendMessage sendMessage = clickButton_SHOW_ID_YOUR_PET(chatId);
    reportService.addNewBlankReportInDbForPetByPetId();
    return sendMessage;

  }

  //endregion

  /**
   * Метод пытается сохранить отчет о животном в БД
   *
   * @param message сообщение из update
   */
  public SendMessage sendReport(Message message) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());

    ReplyKeyboardMarkup replyKeyboardMarkup = configKeyboard.formReplyKeyboardInOneRow(
        CANCEL_TEXT);

    String textMessage = message.getText();
    List<PhotoSize> photoSizes = message.getPhoto();
    Document messageDocument = message.getDocument();

    boolean isTextOk = checkTextInSentReport(textMessage);
    boolean isPhotoOk = true;

    if (!isTextOk) {
      return sendMessage = formReplyMessages.replyMessage(message,
          NO_TEXT_SEND_REPORT_TEXT,
          configKeyboard.initKeyboardOnClickStart());
    }
    if (!isPhotoOk) {
      return sendMessage = formReplyMessages.replyMessage(message,
          NO_PHOTO_SEND_REPORT_TEXT,
          configKeyboard.initKeyboardOnClickStart());
    }

//    return sendMessage = saveReportInDb(message);
    return null;
  }

  public boolean checkTextInSentReport(String textMessage) {

    Pattern pattern = Pattern.compile("^(\\d+)(?:\\s+\\w+|\\s+[а-яА-Я]+){15,}");
    Matcher matcher = pattern.matcher(textMessage);

    if (matcher.matches()) {
      return true;
    }
    return true;//true для теста

  }


  /**
   * метод сохраняет отчет в БД
   *
   * @param message сообщение из update
   */
  public SendMessage saveReportInDb(Message message, ReportRecord reportRecord) {

    long chatId = message.getChatId();

    SendMessage sendMessage = formReplyMessages.replyMessage(message,
        CANCEL_RETURN_MAIN_MENU_TEXT, configKeyboard.initKeyboardOnClickStart());
    return sendMessage;
  }

  /**
   * Метод формирует список id питомцев которые зарегистрироаны за AdoptiveParent и inline
   * клавиатуру
   *
   * @param message сообщение из update
   */
  public SendMessage clickButton_SHOW_ID_YOUR_PET(String chatId) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());

    String allPetByChatId = reportService.showAllAdoptedPets(message.getChatId());
    if (allPetByChatId == null) {
      formReplyMessages.setSuccessful(false);
      return sendMessage = formReplyMessages.replyMessage(message,
          YOU_HAVE_NO_ADOPTED_PETS_TEXT, configKeyboard.initKeyboardOnClickStart());
    }

    List<String> buttonsPets = new ArrayList<>(
        List.of(allPetByChatId.split(AllText.DELIMITER_FOR_PARSER_PETS)));
    buttonsPets.add(CANCEL_TEXT);

    int numberPerLine = 1;
    InlineKeyboardMarkup inlineKeyboardMarkup = configKeyboard.formReplyKeyboardAnyRowInline(
        numberPerLine, buttonsPets);

    return sendMessage = formReplyMessages.replyMessage(message,
        DESCRIPTION_SEND_REPORT_TEXT, inlineKeyboardMarkup);
  }


}
