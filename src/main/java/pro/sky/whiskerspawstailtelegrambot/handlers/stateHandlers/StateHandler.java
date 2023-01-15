package pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.handlers.RegistrationHandler;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler.ReportHandler;
import pro.sky.whiskerspawstailtelegrambot.service.StateService;
import pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent;

/**
 * обработчик состояний
 */
@Component
public class StateHandler {


  private final RegistrationHandler registrationHandler;
  private final StateService stateService;

  private final ReportHandler reportHandler;

  public StateHandler(RegistrationHandler registrationHandler, StateService stateService,
      ReportHandler reportHandler) {
    this.registrationHandler = registrationHandler;
    this.stateService = stateService;
    this.reportHandler = reportHandler;
  }

  /**
   * разделить процесс в зависимости от состояния
   */
  public SendMessage processByState(GetBaseInfoFromUpdate baseInfo,
      StateAdoptiveParent stateAdoptiveParent) {
    SendMessage sendMessage = null;

    boolean isReportState = isReportState(stateAdoptiveParent);
    boolean isRegistrationState = isRegistrationState(stateAdoptiveParent);

// todo добавь сюда любое состояние
    if (isReportState) {
      sendMessage = reportHandler.workingState(baseInfo, stateAdoptiveParent);
    }
    if (isRegistrationState) {
      sendMessage = registrationHandler.workingState(baseInfo, stateAdoptiveParent);
    }
    return sendMessage;
  }

  /**
   * получить состояние пользователя по chatId
   */
  public StateAdoptiveParent getStateAdoptiveParentByChatId(long chatId) {
    StateAdoptiveParent stateAdoptiveParentByChatId = stateService.getStateAdoptiveParentByChatId(
        chatId);
    if (stateAdoptiveParentByChatId == null) {
      return null;
    }
    return stateAdoptiveParentByChatId;
  }

  /**
   * проверить на соответсвие состоянию из состояний отправки отчета
   *
   * @param stateAdoptiveParent состояние пользователя
   */
  private boolean isReportState(StateAdoptiveParent stateAdoptiveParent) {
    List<String> reportList = StateAdoptiveParent.getReportList(new ArrayList<>());
    return reportList.stream().anyMatch(s -> s.equals(stateAdoptiveParent.name()));
  }

  /**
   * проверить на соответсвие состоянию из состояний регистрации
   *
   * @param stateAdoptiveParent состояние пользователя
   */
  private boolean isRegistrationState(StateAdoptiveParent stateAdoptiveParent) {
    List<String> registrationList = StateAdoptiveParent.getRegistrationList(new ArrayList<>());
    return registrationList.stream().anyMatch(s -> s.equals(stateAdoptiveParent.name()));
  }


}
