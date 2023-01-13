package pro.sky.whiskerspawstailtelegrambot.handlers;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**  обработчик входящих сообшений  */
@Component
public interface InputMessageHandler {

  /**  запуск обработчика  */
  SendMessage startHandler(Message message);

}
