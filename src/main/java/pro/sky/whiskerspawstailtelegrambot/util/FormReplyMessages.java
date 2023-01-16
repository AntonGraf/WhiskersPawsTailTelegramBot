package pro.sky.whiskerspawstailtelegrambot.util;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import pro.sky.whiskerspawstailtelegrambot.record.PetRecord;

/**
 * сервис для формирования сообщений
 */
@Service
@Getter
public class FormReplyMessages {


  @Value("${api.url.photo}")
  String apiUrlPhoto;

  /**
   * сформировать ответное сообщение, с указаным текстом и клавиатурой
   */
  public SendMessage replyMessage(String chatId, String textReplyMessage,
      ReplyKeyboardMarkup keyboardMarkup) {
    SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
    sendMessage.setReplyMarkup(keyboardMarkup);
    return sendMessage;
  }

  /**
   * сформировать ответное сообщение, с указаным текстом и inline клавиатурой
   */
  public SendMessage replyMessage(String chatId, String textReplyMessage,
      InlineKeyboardMarkup inlineKeyboardMarkup) {
    SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    return sendMessage;
  }

  /**
   * сформировать ответное сообщение с сообщение об ошибке, с указаным текстом и inline клавиатурой
   *
   * @param chatId
   * @param textReplyMessage
   * @param inlineKeyboardMarkup
   * @return
   */
  public SendMessage replyMessageError(String chatId, String textReplyMessage,
      InlineKeyboardMarkup inlineKeyboardMarkup) {
    SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    return sendMessage;
  }

  /**
   * сформировать ответное сообщение с сообщение об ошибке, с указаным текстом и обычной
   * клавиатурой
   *
   * @param chatId
   * @param textReplyMessage
   * @param replyKeyboardMarkup
   * @return
   */
  public SendMessage replyMessageError(String chatId, String textReplyMessage,
      ReplyKeyboardMarkup replyKeyboardMarkup) {
    SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
    sendMessage.setReplyMarkup(replyKeyboardMarkup);
    return sendMessage;
  }

  public SendMessage replyMessageWithTypeAnimal(String chatId, String typeOfAnimal,
      Collection<PetRecord> petRecordCollections) {
    ParserToBot parserToBot = new ParserToBot();
    List<PetRecord> petRecords = petRecordCollections
        .stream()
        .filter(x -> x.getPetType() != null && x.getPetType().equals(typeOfAnimal))
        .collect(Collectors.toList());
    String justList = parserToBot.parseToTypeOfAnimal(petRecords);
    SendMessage sendMessage = new SendMessage();
    StringBuilder stringBuilder = null;
    String URI = null;
    if (justList != null) {
      stringBuilder = new StringBuilder();
      List<String> cut = List.of(justList.split("link="));
      for (String s : cut) {
        String stringInt = s.substring(s.lastIndexOf("\n") + 1);
        String stringCutTextWithoutNum = s.substring(0, s.lastIndexOf("\n"));
        URI = this.getApiUrlPhoto() + stringInt;
        stringBuilder
            .append(stringCutTextWithoutNum)
            .append("\n")
            .append("Фото животного ").append("<a href='").append(URI).append("'>")
            .append("посмотреть").append("</a>")
            .append("\n");
      }
    }else {
        sendMessage = new SendMessage(chatId,"Таких животных пока нет");
    }
      if (stringBuilder != null) {
          sendMessage.setText(stringBuilder.toString());
      }
    sendMessage.setChatId(chatId);
    sendMessage.enableHtml(true);
    return sendMessage;
  }

}
