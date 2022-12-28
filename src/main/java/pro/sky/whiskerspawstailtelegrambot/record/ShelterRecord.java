package pro.sky.whiskerspawstailtelegrambot.record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pro.sky.whiskerspawstailtelegrambot.entity.Shelter;

import java.io.Serializable;

/**
 * A DTO for the {@link Shelter} entity
 */
@AllArgsConstructor
@Getter
public class ShelterRecord implements Serializable {
    private final Long id;
    private final String aboutShelter;
    private final String shelterSafetyEquipment;
    private final String ruleOfMeeting;
    private final String recOfTransportation;
    private final String homeImprovementForPuppy;
    private final String homeImprovementForDog;
    private final String homeImprovementForDogWithDisabilities;
    private final String cynologistAdvice;
}