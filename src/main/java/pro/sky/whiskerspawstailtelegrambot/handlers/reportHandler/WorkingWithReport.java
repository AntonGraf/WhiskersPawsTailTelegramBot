package pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler;

import static pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText.*;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.service.MediaService;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;
import pro.sky.whiskerspawstailtelegrambot.service.StateService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.service.enums.StateAdoptiveParent;

@Slf4j
public class WorkingWithReport {


  private ReportService reportService;
  private String chatId;
  private long chatIdL;
  private SendMessage sendMessage;
  private FormReplyMessages formReplyMessages;
  ConfigKeyboard configKeyboard;
  private final StateService stateService;
  Message message;
  CheckingReport checkingReport;
  GetBaseInfoFromUpdate baseInfo;
  private MediaService mediaService;

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
    message = baseInfo.getMessage();
    checkingReport = new CheckingReport();
    formReplyMessages = new FormReplyMessages();
    configKeyboard = new ConfigKeyboard();
  }


  public SendMessage work(ReportRecord reportRecord,
      StateAdoptiveParent stateAdoptiveParent) {

    UpdateReport updateReport = new UpdateReport(
        reportRecord, reportService, baseInfo, mediaService);

    SendMessage sendMessage = null;

    switch (stateAdoptiveParent) {

      case CANCEL_CREATE_REPORT:
        sendMessage = cancelCreateReport();
        break;

      case START_0:
        sendMessage = startReport0();
        break;

      case CHECK_ID_WAIT_PHOTO_REPORT_1:
        reportRecord = updateReport.checkAndUpdateReportId();
        sendMessage = checkIdWaitPhotoReport1(reportRecord);
        break;

      case CHECK_PHOTO_WAIT_DIET_REPORT_2:
        reportRecord = updateReport.checkAndUpdatePhotoOrDoc();
        sendMessage = checkPhotoWaitDietReport2(reportRecord);
        break;

      case CHECK_DIET_WAIT_FEELINGS_REPORT_3:
        reportRecord = updateReport.checkAndUpdateEnterDiet();
        sendMessage = checkDietWaitFeelingsReport3(reportRecord);
        break;

      case CHECK_FEELINGS_WAIT_HABITS_REPORT_4:
        reportRecord = updateReport.checkAndUpdateEnterFeelings();
        sendMessage = checkFeelingsWaitHabitsReport4(reportRecord);
        break;

      case CHECK_HABITS_WAIT_FINISHED_REPORT_5:
        reportRecord = updateReport.checkAndUpdateEnterHabits();
        sendMessage = checkHabitsWaitFinishedReport5(reportRecord);
        break;

      case FREE:
        sendMessage = resetState(null, StateAdoptiveParent.FREE);
        break;

    }
    return sendMessage;
  }

  private SendMessage cancelCreateReport() {
    reportService.removeAllBlankReportByChatId(baseInfo.getChatIdL());
    sendMessage = formReplyMessages.replyMessage(chatId, CANCEL_RETURN_MAIN_MENU_TEXT,
        configKeyboard.initKeyboardOnClickStart());
    stateService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.FREE);
    return sendMessage;
  }

  private SendMessage startReport0() {
    String allPetByChatId = reportService.showAllAdoptedPetsByChatId(baseInfo.getChatIdL());
    if (allPetByChatId == null) {
      stateService.updateStateAdoptiveParentByChatId(chatIdL,
          StateAdoptiveParent.FREE);
      return formReplyMessages.replyMessage(chatId, YOU_HAVE_NO_ADOPTED_PETS_TEXT,
          configKeyboard.initKeyboardOnClickStart());
    }
    reportService.addNewBlankReportWithChatId(baseInfo.getChatIdL());

    FormMessageAndKeyboardIdYourPets formMessageAndKeyboardIdYourPets = new FormMessageAndKeyboardIdYourPets();
    sendMessage = formMessageAndKeyboardIdYourPets.get(baseInfo, reportService);

    stateService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.CHECK_ID_WAIT_PHOTO_REPORT_1);
    return sendMessage;
  }

  private SendMessage checkIdWaitPhotoReport1(ReportRecord reportRecord) {
    if (reportRecord != null) {
      reportService.updateReport(reportRecord);
      sendMessage = formReplyMessages.replyMessage(chatId, PHOTO_SEND_REPORT_TEXT,
          configKeyboard.formReplyKeyboardInOneRow(CANCEL_CREATE_REPORT_TEXT));
      stateService.updateStateAdoptiveParentByChatId(chatIdL,
          StateAdoptiveParent.CHECK_PHOTO_WAIT_DIET_REPORT_2);
      return sendMessage;
    }
    return resetState(null, StateAdoptiveParent.CHECK_ID_WAIT_PHOTO_REPORT_1);
  }

  private SendMessage checkPhotoWaitDietReport2(ReportRecord reportRecord) {
    if (reportRecord != null) {
      reportService.updateReport(reportRecord);
      sendMessage = formReplyMessages.replyMessage(chatId, DIET_SEND_REPORT_TEXT,
          configKeyboard.formReplyKeyboardInOneRow(CANCEL_CREATE_REPORT_TEXT));
      stateService.updateStateAdoptiveParentByChatId(chatIdL,
          StateAdoptiveParent.CHECK_DIET_WAIT_FEELINGS_REPORT_3);
      return sendMessage;
    }
    return resetState(null, StateAdoptiveParent.CHECK_PHOTO_WAIT_DIET_REPORT_2);

  }

  private SendMessage checkDietWaitFeelingsReport3(ReportRecord reportRecord) {
    if (reportRecord != null) {
      reportService.updateReport(reportRecord);
      sendMessage = formReplyMessages.replyMessage(chatId, FEELINGS_SEND_REPORT_TEXT,
          configKeyboard.formReplyKeyboardInOneRow(CANCEL_CREATE_REPORT_TEXT));
      stateService.updateStateAdoptiveParentByChatId(chatIdL,
          StateAdoptiveParent.CHECK_FEELINGS_WAIT_HABITS_REPORT_4);
      return sendMessage;
    }
    return resetState(null, StateAdoptiveParent.CHECK_DIET_WAIT_FEELINGS_REPORT_3);
  }

  private SendMessage checkFeelingsWaitHabitsReport4(ReportRecord reportRecord) {
    if (reportRecord != null) {
      reportService.updateReport(reportRecord);
      sendMessage = formReplyMessages.replyMessage(chatId, HABITS_SEND_REPORT_TEXT,
          configKeyboard.formReplyKeyboardInOneRow(CANCEL_CREATE_REPORT_TEXT));
      stateService.updateStateAdoptiveParentByChatId(chatIdL,
          StateAdoptiveParent.CHECK_HABITS_WAIT_FINISHED_REPORT_5);
      return sendMessage;
    }
    return resetState(null, StateAdoptiveParent.CHECK_FEELINGS_WAIT_HABITS_REPORT_4);
  }

  private SendMessage checkHabitsWaitFinishedReport5(ReportRecord reportRecord) {
    if (reportRecord != null) {
      reportService.updateReport(reportRecord);
      sendMessage = formReplyMessages.replyMessage(chatId, FINISHED_MESSAGE_SEND_REPORT_TEXT,
          configKeyboard.initKeyboardOnClickStart());
      stateService.updateStateAdoptiveParentByChatId(chatIdL,
          StateAdoptiveParent.FREE);
      return sendMessage;
    }
    return resetState(null, StateAdoptiveParent.CHECK_HABITS_WAIT_FINISHED_REPORT_5);
  }


  /**
   * Сброс состояния, при ошибке ввода
   * на любом этапе попросит повторить или скинет состояние на Free
   * @param textForReply тест для ответа об ошибке, если null то стандартный
   * @param stateAdoptiveParent состояние пользователя
   */
  private SendMessage resetState(String textForReply, StateAdoptiveParent stateAdoptiveParent) {

    if (textForReply == null || textForReply.isEmpty()) {
      textForReply = ENTER_ERROR_DATA_TEXT;
    }
    sendMessage = formReplyMessages.replyMessage(chatId, textForReply,
        configKeyboard.formReplyKeyboardInOneRow(CANCEL_CREATE_REPORT_TEXT));
    stateService.updateStateAdoptiveParentByChatId(chatIdL, stateAdoptiveParent);
    return sendMessage;
  }

}


