package pro.sky.whiskerspawstailtelegrambot.record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;

import java.io.Serializable;

/**
 * A DTO for the {@link AdoptiveParent} entity
 */
@AllArgsConstructor
@Getter
public class AdoptiveParentRecord implements Serializable {
    private final long id;
    private final String fullName;
    private final String phone;
    private final String state;
    private final boolean status;
}