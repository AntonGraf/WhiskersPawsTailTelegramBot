package pro.sky.whiskerspawstailtelegrambot.util;

import org.springframework.stereotype.Component;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.record.VolunteerRecord;
import pro.sky.whiskerspawstailtelegrambot.service.VolunteerService;

import java.util.Collection;

/**
 * Класс для парсинга из бд.
 */

@Component
public class ParserToBot {

    /**
     * Парсит строки из каждой сущнсоти в одну строку. Тут конкретно для ответа на вызов
     * /calltovolunteer
     * @param volunteerList принимаем лист волонтеров
     * @return отпарсиная строка для ответа
     */
    public String parserVolunteer(Collection<VolunteerRecord> volunteerList) {
        if(volunteerList.isEmpty()) throw new ElemNotFound();
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for (VolunteerRecord volunteerRecord : volunteerList)
            stringBuilder.append(++count)
                    .append(") ")
                    .append(volunteerRecord)
                    .append('\n').append('\n');
        return stringBuilder.toString();
    }
}
