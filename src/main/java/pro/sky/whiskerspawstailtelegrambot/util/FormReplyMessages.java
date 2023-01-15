package pro.sky.whiskerspawstailtelegrambot.util;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import pro.sky.whiskerspawstailtelegrambot.configuration.ConfigMenu;
import pro.sky.whiskerspawstailtelegrambot.record.PetRecord;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;

/**
 * сервис для формирования сообщений
 */
@Service
public class FormReplyMessages {
    /**
     * сформировать ответное сообщение, с указаным текстом и клавиатурой
     */
    public SendMessage replyMessage(String chatId, String textReplyMessage, ReplyKeyboardMarkup keyboardMarkup) {
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

    /**
     * сформировать ответное сообщение с сообщение об ошибке, с указаным текстом и inline клавиатурой
     * @param message
     * @param textReplyMessage
     * @param inlineKeyboardMarkup
     * @return
     */
    public SendMessage replyMessageError(Message message, String textReplyMessage, InlineKeyboardMarkup inlineKeyboardMarkup) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    /**
     * сформировать ответное сообщение с сообщение об ошибке, с указаным текстом и обычной клавиатурой
     * @param message
     * @param textReplyMessage
     * @param replyKeyboardMarkup
     * @return
     */
    public SendMessage replyMessageError(Message message, String textReplyMessage, ReplyKeyboardMarkup replyKeyboardMarkup) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage(chatId, textReplyMessage);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage replyMessageWithPhoto(Message message, String typeOfAnimal,
        Collection<PetRecord> petRecordCollections) {
        ParserToBot parserToBot = new ParserToBot();
        String chatId = String.valueOf(message.getChatId());
        List<PetRecord> petRecords = petRecordCollections
            .stream()
            .filter(x -> x.getPetType() != null && x.getPetType().equals(typeOfAnimal))
            .collect(Collectors.toList());

 /*   SendPhoto sendPhoto = new SendPhoto();
    sendPhoto.setChatId(chatId);
    sendPhoto.setCaption(textReplyMessage);
    File image;
    try {
      image = ResourceUtils.getFile("classpath:" + "photo_pet/4.png");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    sendPhoto.setPhoto(new InputFile(image));*/
        SendMessage sendMessage = new SendMessage(chatId,parserToBot.parseToTypeOfAnimal(petRecords));
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdownV2(true);
        sendMessage.setParseMode("Markdown");
        sendMessage.setDisableWebPagePreview(true);

        sendMessage.setText("[inline URL](http://127.0.0.1:8080/swagger-ui/index.html#)");
        //sendMessage.setText("`inline fixed-width code`");
        return sendMessage;
    }
}
