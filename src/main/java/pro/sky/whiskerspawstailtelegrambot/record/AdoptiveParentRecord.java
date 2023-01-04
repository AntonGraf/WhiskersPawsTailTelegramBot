package pro.sky.whiskerspawstailtelegrambot.record;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link AdoptiveParent} entity
 */
@AllArgsConstructor
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdoptiveParentRecord implements Serializable {
    long id;
    String fullName;
    String phone;
    String state;
    boolean status;
    boolean isParent;
    List<DogRecord> dogs;
}