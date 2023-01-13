package pro.sky.whiskerspawstailtelegrambot.record;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * A DTO for the {@link pro.sky.whiskerspawstailtelegrambot.entity.Report} entity
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportRecord implements Serializable {
    Long id;
    Long pet_id;
    byte[] photoPet;
    String diet;
    String reportAboutFeelings;
    String reportAboutHabits;
    String stateReport;
}