package pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers;

import java.util.List;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.handlers.InputMessageHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler.ReportHandler;
import pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.util.StateReport;
import pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateReportList;


@Component
public class StateCommonHandlerImpl implements InputMessageHandler {

  private final StateAdoptiveParentHandlerImpl stateAdoptiveParentHandler;
  private final StateReportHandlerImpl stateReportHandler;

  private final ReportHandler reportHandler;

  public StateCommonHandlerImpl(StateAdoptiveParentHandlerImpl stateAdoptiveParentHandler,
      StateReportHandlerImpl stateReportHandler, ReportHandler reportHandler) {
    this.stateAdoptiveParentHandler = stateAdoptiveParentHandler;
    this.stateReportHandler = stateReportHandler;
    this.reportHandler = reportHandler;
  }

  @Override
  public SendMessage startHandler(Message message) {
    return null;
  }

  public SendMessage workingState(Message message, StateAdoptiveParent stateAdoptiveParent) {

    checkState(stateAdoptiveParent);
    return null;
  }

  public SendMessage checkState(long chatId, StateAdoptiveParent stateAdoptiveParent) {

    boolean isReportState = isReportState(stateAdoptiveParent);

    if (isReportState) {
      reportHandler.workingState(message stateAdoptiveParent);
    }


  }


  public StateReport getStateReportByPetId(long petId) {
    return stateReportHandler.getStateReportByPetId(petId);
  }

  public StateAdoptiveParent getStateAdoptiveParentByChatId(long chatId) {
    return stateAdoptiveParentHandler.getStateAdoptiveParentByChatId(chatId);
  }

  public boolean isReportState(StateAdoptiveParent stateAdoptiveParent) {

    StateReportList stateReportList = new StateReportList();
    List<String> reportList = stateReportList.get();

    return reportList.stream().anyMatch(s -> s.equals(stateAdoptiveParent.name()));
  }


}
