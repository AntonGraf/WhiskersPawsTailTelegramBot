package pro.sky.whiskerspawstailtelegrambot.handlers.mediaContentHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.service.MediaService;

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
        sendMessage = mediaService.processPhoto(baseInfo.getMessage());
      } catch (RuntimeException ex) {
        log.error("Ошибка загрузки фото");
        return null;
      }
    }

    if (baseInfo.getMessage().hasDocument()) {
      try {
        sendMessage = mediaService.processDoc(baseInfo.getMessage());
      } catch (RuntimeException ex) {
        log.error("Ошибка загрузки фото");
        return null;
      }
    }

    return sendMessage;
  }


}
