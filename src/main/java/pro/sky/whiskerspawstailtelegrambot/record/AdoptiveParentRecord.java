package pro.sky.whiskerspawstailtelegrambot.record;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent} entity
 */
@AllArgsConstructor
@Getter
public class AdoptiveParentRecord implements Serializable {
    private final long id;
    private final String fullName;
    private final String phone;
    private final String state;
    private final String status;
}