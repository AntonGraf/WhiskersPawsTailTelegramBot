package pro.sky.whiskerspawstailtelegrambot.util;


import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import pro.sky.whiskerspawstailtelegrambot.configuration.ConfigMenu;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;

/**
 * сервис для формирования сообщений
 */
@Service
public class FormReplyMessages {
    /**
     * сформировать ответное сообщение, с указаным текстом и клавиатурой
     */
    public SendMessage replyMessage(Message message, String textReplyMessage, ReplyKeyboardMarkup keyboardMarkup) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    /**
     * сформировать ответное сообщение, с указаным текстом и inline клавиатурой
     */
    public SendMessage replyMessage(Message message, String textReplyMessage, InlineKeyboardMarkup inlineKeyboardMarkup) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage replyMessageError(Message message, String textReplyMessage, InlineKeyboardMarkup inlineKeyboardMarkup) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }
    public SendMessage replyMessageError(Message message, String textReplyMessage, ReplyKeyboardMarkup inlineKeyboardMarkup) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage replyMessageSetAllKeyBoardNull(Message message, String textReplyMessage, InlineKeyboardMarkup inlineKeyboardMarkup) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage.setReplyMarkup(new ConfigKeyboard().formReplyKeyboardInOneRow(""));

        return sendMessage;
    }

}
