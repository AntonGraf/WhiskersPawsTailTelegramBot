package pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler;

import static pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText.*;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;
import pro.sky.whiskerspawstailtelegrambot.service.StateChangeAdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent;

public class WorkingState {


  private ReportService reportService;
  private String chatId;
  private long chatIdL;
  private SendMessage sendMessage;
  private FormReplyMessages formReplyMessages;
  ConfigKeyboard configKeyboard;
  private final StateChangeAdoptiveParentService stateChangeAdoptiveParentService;
  private Message message;


  public WorkingState(Message message, ReportService reportService,
      StateChangeAdoptiveParentService stateChangeAdoptiveParentService) {
    this.message = message;
    this.reportService = reportService;
    this.stateChangeAdoptiveParentService = stateChangeAdoptiveParentService;
    formReplyMessages = new FormReplyMessages();
    configKeyboard = new ConfigKeyboard();
    this.chatId = message.getChatId().toString();
    chatIdL = Long.parseLong(chatId);
  }

  public SendMessage work(ReportRecord reportRecord,
      StateAdoptiveParent stateAdoptiveParent) {

    UpdateReportRecordFromState updateReportRecordFromState = new UpdateReportRecordFromState(
        reportRecord, message);
    SendMessage sendMessage = null;
    String textMessage = message.getText();
    PhotoSize size = message.getPhoto().get(0);
    byte[] photo = new byte[3];

    switch (stateAdoptiveParent) {

      case START_REPORT_1:
        sendMessage = startReport1();
        break;

      case ENTER_ID_PET_WAIT_PHOTO_REPORT_2:
        reportRecord = updateReportRecordFromState.updateEnterIdPetWaitPhotoReport2();
        sendMessage = enterIdPetWaitPhotoReport2(reportRecord);
        break;

      case ENTER_PHOTO_WAIT_DIET_REPORT_3:
        sendMessage = enterPhotoWaitDietReport3(reportRecord);
        break;

      case ENTER_DIET_WAIT_FEELINGS_REPORT_4:
        sendMessage = enterDietWaitFeelingsReport4(reportRecord);
        break;

      case ENTER_FEELINGS_WAIT_HABITS_REPORT_5:
        sendMessage = enterFeelingsWaitHabitsReport5(reportRecord);
        break;

      case ENTER_HABITS_WAIT_FINISHED_REPORT_6:
        sendMessage = enterHabitsFinishedReportSetStateFree_6(reportRecord);
        break;

      case FINISHED_REPORT_7:
        sendMessage = finishedReport7();
        break;
    }
    return sendMessage;
  }

  private SendMessage startReport1() {
    reportService.addNewBlankReportInDbForPetByPetId();
    sendMessage = formReplyMessages.replyMessage(chatId, DESCRIPTION_SEND_REPORT_TEXT,
        configKeyboard.formReplyKeyboardInOneRowInline(SHOW_ALL_YOUR_PET_TEXT,
            CANCEL_TEXT));
    stateChangeAdoptiveParentService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.START_REPORT_1);
    return sendMessage;

  }

  private SendMessage enterIdPetWaitPhotoReport2(ReportRecord reportRecord) {
    reportService.updateReport(reportRecord);
    sendMessage = formReplyMessages.replyMessage(chatId, PHOTO_SEND_REPORT_TEXT,
        configKeyboard.formReplyKeyboardInOneRow(SHOW_ALL_YOUR_PET_TEXT,
            CANCEL_TEXT));
    stateChangeAdoptiveParentService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.ENTER_ID_PET_WAIT_PHOTO_REPORT_2);
    return sendMessage;

  }

  private SendMessage enterPhotoWaitDietReport3(ReportRecord reportRecord) {
    reportService.updateReport(reportRecord);
    sendMessage = formReplyMessages.replyMessage(chatId, DIET_SEND_REPORT_TEXT,
        configKeyboard.formReplyKeyboardInOneRow(SHOW_ALL_YOUR_PET_TEXT,
            CANCEL_TEXT));
    stateChangeAdoptiveParentService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.ENTER_PHOTO_WAIT_DIET_REPORT_3);
    return sendMessage;
  }

  private SendMessage enterDietWaitFeelingsReport4(ReportRecord reportRecord) {
    reportService.updateReport(reportRecord);
    sendMessage = formReplyMessages.replyMessage(chatId, FEELINGS_SEND_REPORT_TEXT,
        configKeyboard.formReplyKeyboardInOneRow(SHOW_ALL_YOUR_PET_TEXT,
            CANCEL_TEXT));
    stateChangeAdoptiveParentService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.ENTER_DIET_WAIT_FEELINGS_REPORT_4);
    return sendMessage;
  }

  private SendMessage enterFeelingsWaitHabitsReport5(ReportRecord reportRecord) {
    reportService.updateReport(reportRecord);
    sendMessage = formReplyMessages.replyMessage(chatId, HABITS_SEND_REPORT_TEXT,
        configKeyboard.formReplyKeyboardInOneRow(SHOW_ALL_YOUR_PET_TEXT,
            CANCEL_TEXT));
    stateChangeAdoptiveParentService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.ENTER_FEELINGS_WAIT_HABITS_REPORT_5);
    return sendMessage;
  }

  private SendMessage enterHabitsFinishedReportSetStateFree_6(ReportRecord reportRecord) {
    reportService.updateReport(reportRecord);
    sendMessage = formReplyMessages.replyMessage(chatId, FINISHED_MESSAGE_SEND_REPORT_TEXT,
        configKeyboard.initKeyboardOnClickStart());
    stateChangeAdoptiveParentService.updateStateAdoptiveParentByChatId(chatIdL,
        StateAdoptiveParent.FREE);
    return sendMessage;
  }

  private SendMessage finishedReport7() {
    return null;
  }


}


