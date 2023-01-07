package pro.sky.whiskerspawstailtelegrambot.util;

import org.springframework.stereotype.Component;
import pro.sky.whiskerspawstailtelegrambot.record.VolunteerRecord;
import pro.sky.whiskerspawstailtelegrambot.service.VolunteerService;

import java.util.Collection;

/**
 * Класс для парсинга из бд.
 */

@Component
public class ParserToBot {

    public String parserVolunteer(Collection<VolunteerRecord> volunteerList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (VolunteerRecord volunteerRecord : volunteerList) {
            if (volunteerRecord.toString().length() > 0) {
                stringBuilder.append(volunteerRecord).append('\n');
            }
        }
        return stringBuilder.toString();
    }
}
