package pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.handlers.InputMessageHandler;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.service.AdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.service.StateChangeAdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent;

@Component

public class StateAdoptiveParentHandlerImpl implements InputMessageHandler {

  private final AdoptiveParentService adoptiveParentService;
  private final StateChangeAdoptiveParentService stateChangeAdoptiveParentService;


  public StateAdoptiveParentHandlerImpl(AdoptiveParentService adoptiveParentService,
      StateChangeAdoptiveParentService stateChangeAdoptiveParentService) {
    this.adoptiveParentService = adoptiveParentService;
    this.stateChangeAdoptiveParentService = stateChangeAdoptiveParentService;
  }

  @Override
  public SendMessage startHandler(Message message) {
    return null;
  }

  public StateAdoptiveParent getStateAdoptiveParentByChatId(long chatId) {

    AdoptiveParentRecord adoptiveParentRecord = adoptiveParentService.getAdoptiveParentByChatId(
        chatId);
    StateAdoptiveParent stateAdoptiveParent = StateAdoptiveParent.valueOf(
        adoptiveParentRecord.getState());

    return stateAdoptiveParent;
  }




}
