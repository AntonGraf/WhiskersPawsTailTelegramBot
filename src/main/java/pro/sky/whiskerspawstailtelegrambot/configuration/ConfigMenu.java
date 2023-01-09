package pro.sky.whiskerspawstailtelegrambot.configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.whiskerspawstailtelegrambot.TelegramBotUpdatesListener;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, где создается меню комнад в боте
 */
@Configuration
@Slf4j
@Data
public class ConfigMenu {
    public void initMenu(TelegramBotUpdatesListener bot){
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand(AllText.START_TEXT, AllText.WELCOME_MESSAGE_TEXT));
        listofCommands.add(new BotCommand(AllText.CALL_TO_VOLUNTEER_TEXT, AllText.START_MENU_TEXT));
        try {
            bot.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }
}
