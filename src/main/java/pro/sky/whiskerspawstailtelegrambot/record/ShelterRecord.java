package pro.sky.whiskerspawstailtelegrambot.record;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link pro.sky.whiskerspawstailtelegrambot.entity.Shelter} entity
 */
@AllArgsConstructor
@Getter
public class ShelterRecord implements Serializable {
    private final Long id;
    private final String aboutShelter;
    private final String shelterSafetyEquipment;
    private final String ruleOfMeeting;
    private final List<String> listOfDoc;
    private final String recOfTransportation;
    private final String homeImprovementForPuppy;
    private final String homeImprovementForDog;
    private final String homeImprovementForDogWithDisabilities;
    private final String cynologistAdvice;
    private final List<String> cynologist;
    private final List<String> listOfReasonForRejection;
}