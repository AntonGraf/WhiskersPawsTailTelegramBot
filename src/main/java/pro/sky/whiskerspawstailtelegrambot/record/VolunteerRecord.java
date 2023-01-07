package pro.sky.whiskerspawstailtelegrambot.record;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * A DTO for the {@link pro.sky.whiskerspawstailtelegrambot.entity.Volunteer} entity
 */
@AllArgsConstructor
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class VolunteerRecord implements Serializable {
    long id;
    String fullName;
    String phone;
    String info_volunteer;
    String schedule;

    @Override
    public String toString() {
        return "Меня зовут " + fullName + ",мой телефон для связи " + phone +
                ",доступен: " + schedule;
    }


    // Shelter shelter;
}