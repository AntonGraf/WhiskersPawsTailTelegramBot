package pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.pro.packaged.B;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;


public class CheckingReport {

  public Long checkCorrectPetId(String textMessage, ReportService reportService)
      throws IllegalArgumentException {

    Long tmpPetId = parserStringPetId(textMessage);
    Long petId = reportService.getPetByPetId(tmpPetId).getId();
    return petId;
  }

  private Long parserStringPetId(String textMessage) {
    try {
      int lastIndex = textMessage.indexOf("\n");
      int startIndex = textMessage.lastIndexOf(" ", lastIndex - 1) + 1;
      String id = textMessage.substring(startIndex, lastIndex);
      return Long.parseLong(id);
    } catch (Exception e) {
      throw new NumberFormatException("Введен некорректный Id питомца");
    }
  }

  public void checkText(String textMessage) throws IllegalArgumentException {

    Pattern pattern = Pattern.compile("^(\\d+)(?:\\s+\\w+|\\s+[а-яА-Я]+){3,}");
    pattern = Pattern.compile("\\w*\\s*[а-яА-Я]*"); //todo для отладки
    Matcher matcher = pattern.matcher(textMessage);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Введен некоректный текст");
    }
  }

  public boolean checkPhotoOrDoc(GetBaseInfoFromUpdate info) throws IllegalArgumentException {

    long photoOrDocMinSize = 20480;//размер фото в байтах

    if (info.getMessage().hasPhoto()) {
      PhotoSize photoSize = info.getMessage().getPhoto()
          .get(info.getMessage().getPhoto().size() - 1);
      return photoSize.getFileSize() > photoOrDocMinSize;
    }
    if (info.getMessage().hasDocument()) {
      Document messageDocument = info.getMessage().getDocument();
      return messageDocument.getFileSize() > photoOrDocMinSize;
    }
    throw new IllegalArgumentException("Некоректное фото или документ");
  }


}
