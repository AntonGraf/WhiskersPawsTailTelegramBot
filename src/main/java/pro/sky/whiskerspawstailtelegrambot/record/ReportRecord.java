package pro.sky.whiskerspawstailtelegrambot.record;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;

import java.io.Serializable;

/**
 * A DTO for the {@link pro.sky.whiskerspawstailtelegrambot.entity.Report} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportRecord implements Serializable {
    Long id;
    Long dog_id;
    byte[] photoDog;
    String diet;
    String reportAboutFeelings;
    String reportAboutHabits;
    String stateReport;
}