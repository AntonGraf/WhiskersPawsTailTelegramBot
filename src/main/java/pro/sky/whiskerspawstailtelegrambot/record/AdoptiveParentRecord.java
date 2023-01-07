package pro.sky.whiskerspawstailtelegrambot.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link AdoptiveParent} entity
 */
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdoptiveParentRecord implements Serializable {

    @JsonIgnore
    long id;

    @NotNull(message = "Обязательно нужно заполнить поле")
    @Size(message = "Длина не должна быть меньше 5 знаков и не больше 30", min = 5, max = 30)
    String fullName;
    @NotNull(message = "Обязательно нужно заполнить поле")
    @Size(message = "Длина не должна быть меньше 5 знаков и не больше 30", min = 5, max = 30)
    String phone;


    @JsonIgnore
    String state;

    @NotNull(message = "Обязательно нужно заполнить поле")
    boolean isParent;

    @JsonIgnore
    long chatId;
    @JsonIgnore
    List<DogRecord> dogs;

}