package pro.sky.whiskerspawstailtelegrambot.record;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link pro.sky.whiskerspawstailtelegrambot.entity.Shelter} entity
 */
@Value
public class ShelterRecord implements Serializable {
    Long id;
    String aboutShelter;
    String shelterSafetyEquipment;
    String ruleOfMeeting;
    List<String> listOfDoc;
    String recOfTransportation;
    String homeImprovementForPuppy;
    String homeImprovementForDog;
    String homeImprovementForDogWithDisabilities;
    String cynologistAdvice;
    List<String> cynologist;
    List<String> listOfReasonForRejection;
}