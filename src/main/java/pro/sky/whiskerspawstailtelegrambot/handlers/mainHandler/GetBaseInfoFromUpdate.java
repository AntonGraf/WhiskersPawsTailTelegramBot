package pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler;//package pro.sky.whiskerspawstailtelegrambot.mainHandler.messageHandler;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
//@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetBaseInfoFromUpdate {

  String chatId;
  Long chatIdL;
  Message message;
  String textMessage;
  boolean isCallbackQuery;
  CallbackQuery callbackQuery;
  Message MessageFromCallbackQuery;
  List<PhotoSize> photos;
  Document document;


  public GetBaseInfoFromUpdate(Update update) {
    init(update);
  }

  public void init(Update update) {

    if (update.getCallbackQuery() != null) {
      callbackQuery = update.getCallbackQuery();
      MessageFromCallbackQuery = callbackQuery.getMessage();
      textMessage = callbackQuery.getData();
      chatIdL = callbackQuery.getMessage().getChatId();
      chatId = chatIdL.toString();
      isCallbackQuery = true;

    } else if (update.getMessage().hasPhoto() || update.getMessage().hasDocument()) {
      message = update.getMessage();
      chatIdL = update.getMessage().getChatId();
      chatId = chatIdL.toString();
      textMessage = update.getMessage().getCaption();
      photos = update.getMessage().getPhoto();
      document = update.getMessage().getDocument();
      isCallbackQuery = false;

    } else {
      message = update.getMessage();
      textMessage = message.getText();
      chatIdL = message.getChatId();
      chatId = chatIdL.toString();
      isCallbackQuery = false;
    }
  }


}
