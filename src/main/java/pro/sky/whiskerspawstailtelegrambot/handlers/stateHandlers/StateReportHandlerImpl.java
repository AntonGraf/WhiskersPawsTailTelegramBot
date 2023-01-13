package pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers;



import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.handlers.InputMessageHandler;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;
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


}
