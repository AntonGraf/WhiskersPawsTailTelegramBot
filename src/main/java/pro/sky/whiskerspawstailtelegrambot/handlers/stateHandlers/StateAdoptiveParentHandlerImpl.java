package pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.handlers.InputMessageHandler;

@Component
public class StateAdoptiveParentHandlerImpl implements InputMessageHandler {

  @Override
  public SendMessage startHandler(Message message) {
    return null;
  }
}
