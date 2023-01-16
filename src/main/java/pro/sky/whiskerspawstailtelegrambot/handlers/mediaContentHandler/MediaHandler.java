package pro.sky.whiskerspawstailtelegrambot.handlers.mediaContentHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.service.MediaService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;

/**
 * Обработка медиа
 */
@Service
@Slf4j
public class MediaHandler {

  private final MediaService mediaService;

  public MediaHandler(MediaService mediaService) {

    this.mediaService = mediaService;
  }

  public SendMessage workingState(GetBaseInfoFromUpdate baseInfo) {

    SendMessage sendMessage = null;

    if (baseInfo.getMessage().hasPhoto()) {
      try {
        //todo  тут нужно обрабатывать различные условия сохранения фото в бд, пака такой необходимости нет
//        sendMessage = mediaService.processPhoto(baseInfo.getMessage());
      } catch (RuntimeException ex) {
        log.error("Ошибка загрузки фото");
        return null;
      }
    }

    if (baseInfo.getMessage().hasDocument()) {
      try {
        //todo тут нужно обрабатывать различные условия сохранения документа в бд, пака такой необходимости нет
//        sendMessage = mediaService.processDoc(baseInfo.getMessage());
      } catch (RuntimeException ex) {
        log.error("Ошибка загрузки фото");
        return null;
      }
    }

    return new SendMessage(baseInfo.getChatId(), AllText.ERROR_REPLY_TEXT); //todo временно пока нет обработчика фото и документа
  }


}
