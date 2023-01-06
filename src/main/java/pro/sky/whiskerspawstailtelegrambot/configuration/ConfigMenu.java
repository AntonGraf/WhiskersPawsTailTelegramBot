package pro.sky.whiskerspawstailtelegrambot.configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.whiskerspawstailtelegrambot.TelegramBotUpdatesListener;
import pro.sky.whiskerspawstailtelegrambot.textAndButtons.AllText;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
@Data
public class ConfigMenu {
    public void initMenu(TelegramBotUpdatesListener bot){
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand(AllText.START_TEXT, "get a welcome message"));
        listofCommands.add(new BotCommand(AllText.CALL_TO_VOLUNTEER, "call to volunteer"));

        try {
            bot.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }
}
