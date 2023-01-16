package pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;


public class CheckingReport {

  public Long checkCorrectPetId(String textMessage, ReportService reportService) {

    Long tmpPetId = parserStringPetId(textMessage);
    Long petId = tmpPetId != null ? reportService.getPetByPetId(tmpPetId).getId() : null;
    return petId;
  }

  private Long parserStringPetId(String textMessage) {
    if (textMessage == null) {
      return null;
    }
    try {
      int lastIndex = textMessage.indexOf("\n");
      int startIndex = textMessage.lastIndexOf(" ", lastIndex - 1) + 1;
//      int lastIndex = textMessage.indexOf(" ", startIndex + 1);
      String id = textMessage.substring(startIndex, lastIndex);
      return Long.parseLong(id);
    } catch (Exception e) {
      return null;
    }
  }

  public boolean checkText(String textMessage) {

    Pattern pattern = Pattern.compile("^(\\d+)(?:\\s+\\w+|\\s+[а-яА-Я]+){3,}");
    Matcher matcher = pattern.matcher(textMessage);

    if (matcher.matches()) {
      return true;
    }
    return true;//true для теста todo
  }

  public boolean checkPhotoOrDoc(Message message) {

    long photoOrDocMinSize = 20480;//размер фото в байтах
    PhotoSize photoSize = null;
    Document messageDocument = null;
    if (message.hasPhoto()) {
      photoSize = message.getPhoto().get(message.getPhoto().size() - 1);
      return photoSize.getFileSize() > photoOrDocMinSize;
    }
    if (message.hasDocument()) {
      messageDocument = message.getDocument();
      return messageDocument.getFileSize() > photoOrDocMinSize;
    }
    return false;
  }


}
