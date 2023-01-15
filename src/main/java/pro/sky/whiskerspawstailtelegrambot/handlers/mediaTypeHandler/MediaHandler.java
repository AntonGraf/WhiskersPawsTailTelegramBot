package pro.sky.whiskerspawstailtelegrambot.handlers.mediaTypeHandler;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler.WorkingWithReport;
import pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers.StateHandler;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent;
/**
 * Обработка медиа
 * @param baseInfo
 * @return
 */
@Service
public class MediaHandler {

  StateHandler stateHandler;
  Photo photo;
// TODO: 15.01.2023 функционал находится в разработе)

  public SendMessage workingState(GetBaseInfoFromUpdate baseInfo) {

    SendMessage sendMessage = null;

    if (baseInfo.getMessage().hasPhoto()) {

    }

    if (baseInfo.getMessage().hasDocument()) {

    }

    return sendMessage;
  }


}
