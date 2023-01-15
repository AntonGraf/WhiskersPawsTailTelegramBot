package pro.sky.whiskerspawstailtelegrambot.record;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    Boolean isReportCompleted;
    Long chatId;
    LocalDateTime dateTime;
}