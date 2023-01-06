package pro.sky.whiskerspawstailtelegrambot.util;


import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

/**
 * сервис для формирования сообщений
 */
@Service
public class FormReplyMessages {

    public SendMessage replyMessage(Message message, String textReplyMessage, ReplyKeyboardMarkup keyboardMarkup) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

}
