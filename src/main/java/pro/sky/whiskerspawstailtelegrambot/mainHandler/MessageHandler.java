package pro.sky.whiskerspawstailtelegrambot.mainHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.configuration.ConfigButton;
import pro.sky.whiskerspawstailtelegrambot.textAndButtons.AllText;


/**
 * обработка текстового сообщения
 */
@Slf4j
@Component("MessageHandler")
public class MessageHandler implements MainHandler {

    /**
     * Метод, который отвечает на входящее сообщение, либо на выбор в меню
     * @param update адейт от пользователя в виде текста
     * @return отправляем ответ
     */
    @Override
    public SendMessage handler(Update update) {
        SendMessage sendMessage = null;
        String chatId = String.valueOf(update.getMessage().getChatId());
        boolean checkUpdate = !update.getMessage().hasText();
        if (!checkUpdate) {
            log.debug("Обработка сообщения в виде текста");
            //здесь инжект текст кнопок, любой текст крч
            if (readUpdate(update).equals(AllText.START_TEXT)) {
                sendMessage = new SendMessage(chatId,"get a welcome message");
            } else if (readUpdate(update).equals(AllText.CALL_TO_VOLUNTEER)) {
                //цепляем сервисом бд волонтера
            } else {
                sendMessage = new SendMessage(chatId,"Воспользуйтесь кнопками, либо командами меню");
            }
        }
        return sendMessage;
    }

    //для удобства
    private String readUpdate(Update update) {
        return update.getMessage().getText();
    }




}


