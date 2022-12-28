package pro.sky.whiskerspawstailtelegrambot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String aboutShelter;
    String shelterSafetyEquipment;
    String ruleOfMeeting;
/*
    Set<String> listOfDoc;
*/
    String recOfTransportation;
    String homeImprovementForPuppy;
    String homeImprovementForDog;
    String homeImprovementForDogWithDisabilities;
    String cynologistAdvice;
    /*HashMap<String, String> cynologist;
    Set<String> listOfReasonForRejection;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
