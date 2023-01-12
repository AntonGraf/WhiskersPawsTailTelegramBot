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
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link pro.sky.whiskerspawstailtelegrambot.entity.Shelter} entity
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
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
    Set<DogRecord> dogs;
    Set<VolunteerRecord> volunteers;
    byte[] locationMap;
}