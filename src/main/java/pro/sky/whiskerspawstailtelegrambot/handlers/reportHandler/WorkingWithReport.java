package pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler;

import static pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText.*;

import liquibase.pro.packaged.R;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFoundChecked;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.loger.FormLogInfo;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.service.MediaService;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;
import pro.sky.whiskerspawstailtelegrambot.service.StateService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.service.enums.StateAdoptiveParent;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkingWithReport {


  final ReportService reportService;
  private final StateService stateService;
  final MediaService mediaService;
  final String chatId;
  long chatIdL;
  FormReplyMessages formReplyMessages;
  ConfigKeyboard configKeyboard;
  final GetBaseInfoFromUpdate baseInfo;
  UpdateReport updateReport;
  SendMessage sendMessage = null;
  StateAdoptiveParent stateAdoptiveParent;

  public WorkingWithReport(GetBaseInfoFromUpdate baseInfo, ReportService reportService,
      StateService stateService, MediaService mediaService) {
    this.reportService = reportService;
    this.stateService = stateService;
    this.chatId = baseInfo.getChatId();
    this.baseInfo = baseInfo;
    this.mediaService = mediaService;
    init();
  }

  public void init() {
    chatIdL = baseInfo.getChatIdL();
    formReplyMessages = new FormReplyMessages();
    configKeyboard = new ConfigKeyboard();
    updateReport = new UpdateReport(
        reportService, baseInfo, mediaService);
  }


  public SendMessage work(
      StateAdoptiveParent stateAdoptiveParent) {
    log.info(FormLogInfo.getInfo());
    ReportRecord reportRecord = null;
    try {
      reportRecord = reportService.getReportByChatIdAndIsReportCompletedFalse(chatIdL);
    } catch (Exception e) {
      log.info(FormLogInfo.getCatch());
      reportService.deleteAllByChatIdAndIsReportCompletedFalse(chatIdL);
      reportRecord = new ReportRecord();
    }
    try {

      switch (stateAdoptiveParent) {

        case CANCEL_CREATE_REPORT:
          sendMessage = cancelCreateReport();
          break;

        case START_0:
          reportRecord = getReportOrCreateNew();
          sendMessage = startReport0();
          break;

        case CHECK_ID_WAIT_PHOTO_REPORT_1:
          reportRecord = updateReport.checkAndUpdateReportId(reportRecord);
          sendMessage = checkIdWaitPhotoReport1(reportRecord);
          break;

        case CHECK_PHOTO_WAIT_DIET_REPORT_2:
          reportRecord = updateReport.checkAndUpdatePhotoOrDoc(reportRecord);
          sendMessage = checkPhotoWaitDietReport2(reportRecord);
          break;

        case CHECK_DIET_WAIT_FEELINGS_REPORT_3:
          reportRecord = updateReport.checkAndUpdateEnterDiet(reportRecord);
          sendMessage = checkDietWaitFeelingsReport3(reportRecord);
          break;

        case CHECK_FEELINGS_WAIT_HABITS_REPORT_4:
          reportRecord = updateReport.checkAndUpdateEnterFeelings(reportRecord);
          sendMessage = checkFeelingsWaitHabitsReport4(reportRecord);
          break;

        case CHECK_HABITS_WAIT_FINISHED_REPORT_5:
          reportRecord = updateReport.checkAndUpdateEnterHabits(reportRecord);
          sendMessage = checkHabitsWaitFinishedReport5(reportRecord);
          break;

        case FREE:
          sendMessage = resetState(null, StateAdoptiveParent.FREE);
          break;
      }
    } catch (IllegalArgumentException e) {
      sendMessage = resetState(e.getMessage(), stateAdoptiveParent);
    }
    return sendMessage;
  }

  private SendMessage cancelCreateReport() {

    reportService.deleteAllByChatIdAndIsReportCompletedFalse(baseInfo.getChatIdL());

    sendMessage = formReplyMessages.replyMessage(chatId, CANCEL_RETURN_MAIN_MENU_TEXT,
        configKeyboard.initKeyboardOnClickStart());
    stateAdoptiveParent = stateService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.FREE);
    return sendMessage;
  }

  private SendMessage startReport0() {

    String allPetByChatId = null;
    try {
      allPetByChatId = reportService.showAllAdoptedPetsByChatId(baseInfo.getChatIdL());
    } catch (Exception e) {
      stateAdoptiveParent = stateService.updateStateAdoptiveParentByChatId(chatIdL,
          StateAdoptiveParent.FREE);
      return formReplyMessages.replyMessage(chatId, YOU_HAVE_NO_ADOPTED_PETS_TEXT,
          configKeyboard.initKeyboardOnClickStart());
    }
    reportService.addNewBlankReportWithChatId(baseInfo.getChatIdL());

    FormMessageAndKeyboardIdYourPets formMessageAndKeyboardIdYourPets = new FormMessageAndKeyboardIdYourPets();
    sendMessage = formMessageAndKeyboardIdYourPets.get(baseInfo, allPetByChatId);

    stateAdoptiveParent = stateService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.CHECK_ID_WAIT_PHOTO_REPORT_1);
    return sendMessage;
  }

  private SendMessage checkIdWaitPhotoReport1(ReportRecord reportRecord) {
    reportService.updateReport(reportRecord);
    sendMessage = formReplyMessages.replyMessage(chatId, PHOTO_SEND_REPORT_TEXT,
        configKeyboard.formReplyKeyboardInOneRow(CANCEL_CREATE_REPORT_TEXT));
    stateAdoptiveParent = stateService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.CHECK_PHOTO_WAIT_DIET_REPORT_2);
    return sendMessage;

  }

  private SendMessage checkPhotoWaitDietReport2(ReportRecord reportRecord) {
    reportService.updateReport(reportRecord);
    sendMessage = formReplyMessages.replyMessage(chatId, DIET_SEND_REPORT_TEXT,
        configKeyboard.formReplyKeyboardInOneRow(CANCEL_CREATE_REPORT_TEXT));
    stateAdoptiveParent = stateService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.CHECK_DIET_WAIT_FEELINGS_REPORT_3);
    return sendMessage;
  }

  private SendMessage checkDietWaitFeelingsReport3(ReportRecord reportRecord) {
    reportService.updateReport(reportRecord);
    sendMessage = formReplyMessages.replyMessage(chatId, FEELINGS_SEND_REPORT_TEXT,
        configKeyboard.formReplyKeyboardInOneRow(CANCEL_CREATE_REPORT_TEXT));
    stateAdoptiveParent =  stateService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.CHECK_FEELINGS_WAIT_HABITS_REPORT_4);
    return sendMessage;

  }

  private SendMessage checkFeelingsWaitHabitsReport4(ReportRecord reportRecord) {
    reportService.updateReport(reportRecord);
    sendMessage = formReplyMessages.replyMessage(chatId, HABITS_SEND_REPORT_TEXT,
        configKeyboard.formReplyKeyboardInOneRow(CANCEL_CREATE_REPORT_TEXT));
    stateAdoptiveParent =  stateService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.CHECK_HABITS_WAIT_FINISHED_REPORT_5);
    return sendMessage;

  }

  private SendMessage checkHabitsWaitFinishedReport5(ReportRecord reportRecord) {
    reportService.updateReport(reportRecord);
    sendMessage = formReplyMessages.replyMessage(chatId, FINISHED_MESSAGE_SEND_REPORT_TEXT,
        configKeyboard.initKeyboardOnClickStart());
    stateAdoptiveParent =  stateService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.FREE);
    return sendMessage;
  }

  /**
   * получить имеющийся или создать новый отчет, новый создается при нажатии кнопки отправить отчет
   */
  private ReportRecord getReportOrCreateNew() {
    try {
      return reportService.getReportByChatIdAndIsReportCompletedFalse(chatIdL);
    } catch (Exception e) {
      log.info(FormLogInfo.getCatch());
      reportService.deleteAllByChatIdAndIsReportCompletedFalse(chatIdL);
      return new ReportRecord();
    }
  }

  /**
   * Сброс состояния, при ошибке ввода на любом этапе попросит повторить или скинет состояние на
   * Free
   *
   * @param textForReply        тест для ответа об ошибке, если null то стандартный
   * @param stateAdoptiveParent состояние пользователя
   */
  private SendMessage resetState(String textForReply, StateAdoptiveParent stateAdoptiveParent) {

    if (textForReply == null || textForReply.isEmpty()) {
      textForReply = ENTER_ERROR_DATA_TEXT;
    }
    sendMessage = formReplyMessages.replyMessage(chatId, textForReply,
        configKeyboard.formReplyKeyboardInOneRow(CANCEL_CREATE_REPORT_TEXT));
    stateAdoptiveParent = stateService.updateStateAdoptiveParentByChatId(chatIdL, stateAdoptiveParent);
    return sendMessage;
  }

}


