package pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers;

import liquibase.pro.packaged.T;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.handlers.InputMessageHandler;
import pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.util.StateReport;
import pro.sky.whiskerspawstailtelegrambot.util.StateReportEnum;


@Component
public class StateCommonHandlerImpl implements InputMessageHandler {

  private final StateAdoptiveParentHandlerImpl stateAdoptiveParentHandler;
  private final StateReportHandlerImpl stateReportHandler;
  private final StateEnumeration stateEnumeration;

  public StateCommonHandlerImpl(StateAdoptiveParentHandlerImpl stateAdoptiveParentHandler,
      StateReportHandlerImpl stateReportHandler, StateEnumeration stateEnumeration) {
    this.stateAdoptiveParentHandler = stateAdoptiveParentHandler;
    this.stateReportHandler = stateReportHandler;
    this.stateEnumeration = stateEnumeration;
  }

  @Override
  public SendMessage startHandler(Message message) {
    return null;
  }



  public StateAdoptiveParent checkState(StateAdoptiveParent stateAdoptiveParent){

    StateAdoptiveParent newState = stateEnumeration.selectStateAdoptiveParentRegistration(
        stateAdoptiveParent);

    if(newState == stateAdoptiveParent){
      return  stateAdoptiveParent;
    }
    return null;
  }


  public StateReport getStateReportByPetId(long petId) {
    return stateReportHandler.getStateReportByPetId(petId);
  }

  public StateAdoptiveParent getStateAdoptiveParentByChatId(long chatId) {
    return stateAdoptiveParentHandler.getStateAdoptiveParentByChatId(chatId);
  }

  private SendMessage processingStates(Message message) {

//    long id = 0;
//    try {
//      StateAdoptiveParent stateAdoptiveParent =
//          id);
//
//
//    } catch (Exception e) {
//      return sendMessage;
//    }
    return null;
  }
}
