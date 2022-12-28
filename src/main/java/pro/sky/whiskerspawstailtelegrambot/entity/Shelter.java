package pro.sky.whiskerspawstailtelegrambot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String aboutShelter;
    String shelterSafetyEquipment;
    String ruleOfMeeting;
    @ElementCollection
    List<String> listOfDoc;
    String recOfTransportation;
    String homeImprovementForPuppy;
    String homeImprovementForDog;
    String homeImprovementForDogWithDisabilities;
    String cynologistAdvice;
    @ElementCollection
    List<String> cynologist;
    @ElementCollection
    List<String> listOfReasonForRejection;

}
