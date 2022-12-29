package pro.sky.whiskerspawstailtelegrambot.record;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link pro.sky.whiskerspawstailtelegrambot.entity.Volunteer} entity
 */
@AllArgsConstructor
@Getter
public class VolunteerRecord implements Serializable {
    private final long id;
    private final String fullName;
    private final String phone;
}