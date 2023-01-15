package pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;



public class CheckingReport {
  public Long checkCorrectPetId(String textMessage, ReportService reportService) {

    Long tmpPetId = parserStringPetId(textMessage);
    Long petId = tmpPetId != null ? reportService.getPetById(tmpPetId).getId() : null;
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

  public byte[] checkPhotoOrDoc(Message message) {

    List<PhotoSize> photoSizes = message.getPhoto();
    Document messageDocument = message.getDocument();

    if (photoSizes.size() > 0 && photoSizes.get(0).getFileSize() > 1024 ) {
     byte[] bytes = photoSizes.get(0).getFilePath().getBytes();
      return bytes;
    }
    if (messageDocument.getFileSize() > 1024) {
       byte[] bytes = messageDocument.getThumb().getFilePath().getBytes();
      return bytes;
    }
    return null;
  }


}
