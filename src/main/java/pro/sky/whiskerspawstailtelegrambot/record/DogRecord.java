package pro.sky.whiskerspawstailtelegrambot.record;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.entity.Report;
import pro.sky.whiskerspawstailtelegrambot.entity.Shelter;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link pro.sky.whiskerspawstailtelegrambot.entity.Dog} entity
 */
@AllArgsConstructor
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DogRecord implements Serializable {
    long id;
    String fullName;
    int age;
    Byte[] photo;
    String description;
    List<ReportRecord> reports;
    //Shelter shelter;
    //AdoptiveParent adoptiveParent;
}