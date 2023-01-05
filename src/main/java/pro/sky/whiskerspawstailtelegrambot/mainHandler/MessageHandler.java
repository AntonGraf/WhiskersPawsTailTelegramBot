package pro.sky.whiskerspawstailtelegrambot.mainHandler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.configuration.ConfigButton;

@Component("MessageHandler")
public class MessageHandler implements MainHandler {

    final ConfigButton configButton;

    public MessageHandler(ConfigButton configButton) {
        this.configButton = configButton;
    }

    @Override
    public SendMessage handler(Update update) {
        SendMessage sendMessage = null;
        String chatId = String.valueOf(update.getMessage().getChatId());
        boolean checkUpdate = update.getMessage() == null && !update.getMessage().hasText();
        if (!checkUpdate) {
            if (readUpdate(update).equals("/start")) {
                sendMessage = new SendMessage(chatId,"Здрасте");
                configButton.initButton(sendMessage);
            }else {
                sendMessage = new SendMessage(chatId,"Воспользуйтесь кнопками, либо командами меню");
            }
        }
        return sendMessage;
    }

    private String readUpdate(Update update) {
        return update.getMessage().getText();
    }




}


