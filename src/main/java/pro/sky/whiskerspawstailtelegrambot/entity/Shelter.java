package pro.sky.whiskerspawstailtelegrambot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
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
    @CollectionTable(name = "shelter_list_of_doc", joinColumns = @JoinColumn(name = "shelter_id"))
    @Column(name = "list_if_doc")
    List<String> listOfDoc;
    String recOfTransportation;
    String homeImprovementForPuppy;
    String homeImprovementForDog;
    String homeImprovementForDogWithDisabilities;
    String cynologistAdvice;
    @ElementCollection
    @CollectionTable(name = "shelter_cynologist", joinColumns = @JoinColumn(name = "shelter_id"))
    @Column(name = "cynologist")
    List<String> cynologist;
    @ElementCollection
    @CollectionTable(name = "shelter_list_of_reason_for_rejection", joinColumns = @JoinColumn(name = "shelter_id"))
    @Column(name = "list_of_reason_for_rejection")
    List<String> listOfReasonForRejection;
}
