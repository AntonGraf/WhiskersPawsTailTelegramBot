package pro.sky.whiskerspawstailtelegrambot.mainHandler;


import static pro.sky.whiskerspawstailtelegrambot.util.StateSendReport.FREE;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.service.AdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.util.StateSendReport;


/**
 * обработка текстового сообщения
 */
@Slf4j
@Component("MessageHandler")
public class MessageHandler implements MainHandler {

  private final ReportAddHandler reportAddHandler;
  private final StandardReplyHandler standardReplyHandler;
  private final AdoptiveParentService adoptiveParentService;
  private final CallbackQueryHandler callbackQueryHandler;


  public MessageHandler(ReportAddHandler reportAddHandler,
      StandardReplyHandler standardReplyHandler, AdoptiveParentService adoptiveParentService,
      CallbackQueryHandler callbackQueryHandler) {
    this.reportAddHandler = reportAddHandler;
    this.standardReplyHandler = standardReplyHandler;
    this.adoptiveParentService = adoptiveParentService;
    this.callbackQueryHandler = callbackQueryHandler;
  }

  /**
   * Метод, который отвечает на входящее сообщение, либо на выбор в меню
   *
   * @param update адейт от пользователя в виде текста
   * @return отправляем ответ
   */
  @Override
  public SendMessage handler(Update update) {

    String chatId = null;
    SendMessage sendMessage = null;
    Message message = null;
    try {
      /*
       Проверям CallbackQuery от inline клавиатуры,
       если не null то обрабатываем его в callbackQueryHandler
       иначе идем дальше.
       В зависимости от варианта берем chatId
       */
      if (update.getCallbackQuery() != null) {
        chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        return sendMessage = callbackQueryHandler.handler(update.getCallbackQuery());
      } else {
        message = update.getMessage();
        chatId = message.getChatId().toString();
      }

        /*
       Проверям состояние пользователя,
       если состояни отличное от свободного,
        то обрабатываем состояние.
       */
      StateSendReport state = adoptiveParentService.getStateAdoptiveParentByChatId(Long.parseLong(chatId));
      if (state != FREE && state != null) {

        switch (state) {

          case REGISTRATION:
            //реализация статуса регистрации
            break;

          case WAIT_SEND_REPORT:
            return sendMessage = reportAddHandler.sendReport(message);
        }
      }

      /*
       * Обработка стандартных сообщений от пользователя,
       * если он находится в свободном состоянии (например не в состоянии регистрации или отправки отчета)
       */
      if (update.getMessage().hasText()) {
        return sendMessage = standardReplyHandler.handler(message);
      }

    } finally {
      if (sendMessage == null) {
        sendMessage = new SendMessage(chatId, AllText.ERROR_REPLY_TEXT);
      }
    }
    return sendMessage;
  }

}







