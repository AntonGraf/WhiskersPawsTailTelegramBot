package pro.sky.whiskerspawstailtelegrambot.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import pro.sky.whiskerspawstailtelegrambot.entity.Shelter;

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
    String infoVolunteer;
    String schedule;
    @JsonIgnore
    Shelter shelter;

    @Override
    public String toString() {
        return "Меня зовут " + fullName + ", мой телефон для связи " + phone +
                ", доступен : " + schedule + ".";
    }
}