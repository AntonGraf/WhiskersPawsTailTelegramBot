package pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers;


import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.FINISHED_REPORT_7;
import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.NOT_STARTED;
import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.ENTER_DIET_WAIT_FEELINGS_REPORT_4;
import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.ENTER_FEELINGS_WAIT_HABITS_REPORT_5;
import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.ENTER_HABITS_WAIT_FINISHED_REPORT_6;
import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.ENTER_ID_PET_WAIT_PHOTO_REPORT_2;
import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.ENTER_PHOTO_WAIT_DIET_REPORT_3;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.handlers.InputMessageHandler;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;
import pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.util.StateReport;

@Component
public class StateReportHandlerImpl implements InputMessageHandler {

  private final ReportService reportService;

  public StateReportHandlerImpl(ReportService reportService) {
    this.reportService = reportService;
  }

  @Override
  public SendMessage startHandler(Message message) {

    return null;
  }

  public StateReport getStateReportByPetId(long chatId) {
    StateReport stateReport;

    return reportService.getStateReportByPetId(chatId);
  }

  public StateAdoptiveParent selectStateAdoptiveParentReportFilling(
      StateAdoptiveParent stateAdoptiveParent) {

    switch (stateAdoptiveParent) {

      case NOT_STARTED:
        return NOT_STARTED;

      case ENTER_ID_PET_WAIT_PHOTO_REPORT_2:
        return ENTER_ID_PET_WAIT_PHOTO_REPORT_2;

      case ENTER_PHOTO_WAIT_DIET_REPORT_3:
        return ENTER_PHOTO_WAIT_DIET_REPORT_3;

      case ENTER_DIET_WAIT_FEELINGS_REPORT_4:
        return ENTER_DIET_WAIT_FEELINGS_REPORT_4;

      case ENTER_FEELINGS_WAIT_HABITS_REPORT_5:
        return ENTER_FEELINGS_WAIT_HABITS_REPORT_5;

      case ENTER_HABITS_WAIT_FINISHED_REPORT_6:
        return ENTER_HABITS_WAIT_FINISHED_REPORT_6;

      case FINISHED_REPORT_7:
        return FINISHED_REPORT_7;
    }
    return stateAdoptiveParent;
  }

}
