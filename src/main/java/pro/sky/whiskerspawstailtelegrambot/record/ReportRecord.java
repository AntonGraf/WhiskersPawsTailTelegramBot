package pro.sky.whiskerspawstailtelegrambot.record;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link pro.sky.whiskerspawstailtelegrambot.entity.Report} entity
 */
@AllArgsConstructor
@Getter
public class ReportRecord implements Serializable {
    private final long id;
    private final String diet;
    private final String reportAboutFeelings;
    private final String reportAboutHabits;
    private final Byte[] photoDog;
}