package pro.sky.whiskerspawstailtelegrambot.record;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;

import java.io.Serializable;

/**
 * A DTO for the {@link pro.sky.whiskerspawstailtelegrambot.entity.Report} entity
 */
@AllArgsConstructor
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReportRecord implements Serializable {
    long report_id;
    String diet;
    String reportAboutFeelings;
    //Dog dog;
    String reportAboutHabits;
    Byte[] photoDog;
    long dog_id;
}