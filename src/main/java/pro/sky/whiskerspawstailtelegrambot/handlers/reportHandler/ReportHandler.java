package pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.service.MediaService;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;
import pro.sky.whiskerspawstailtelegrambot.service.StateService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.service.enums.StateAdoptiveParent;

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
  private final StateService stateService;
  private final MediaService mediaService;


  public ReportHandler(FormReplyMessages formReplyMessages, ReportService reportService,
      ConfigKeyboard configKeyboard, StateService stateService, MediaService mediaService) {
    this.formReplyMessages = formReplyMessages;
    this.reportService = reportService;
    this.configKeyboard = configKeyboard;
    this.stateService = stateService;
    this.mediaService = mediaService;
  }

  public SendMessage workingState(GetBaseInfoFromUpdate baseInfo,
      StateAdoptiveParent stateAdoptiveParent) {

    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());

    ReportRecord reportRecord = reportService.getReportByChatIdAndIsReportCompletedFalse(
        baseInfo.getChatIdL());

    WorkingWithReport workingWithReport = new WorkingWithReport(baseInfo, reportService,
        stateService,
        mediaService);
    sendMessage = workingWithReport.work(reportRecord, stateAdoptiveParent);

    return sendMessage;
  }


}
