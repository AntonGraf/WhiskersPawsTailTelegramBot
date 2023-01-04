package pro.sky.whiskerspawstailtelegrambot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Используется для хранения информации о приютах:
 */
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
    Long id;
    //Информация о приюте
    String aboutShelter;
    //Информация о технике безопасности
    String shelterSafetyEquipment;
    //Правила знакомства с собакой
    String ruleOfMeeting;
    //Список документов, необходимых для того, чтобы взять собаку из приюта
    @ElementCollection
    @CollectionTable(name = "shelter_list_of_doc", joinColumns = @JoinColumn(name = "shelter_id"))
    @Column(name = "list_if_doc")
    List<String> listOfDoc;
    //Рекомендации по транспортировке животного
    String recOfTransportation;
    //Рекомендации по обустройству дома для щенка
    String homeImprovementForPuppy;
    //Рекомендации по обустройству дома для взрослой собаки
    String homeImprovementForDog;
    //Рекомендации по обустройству дома для взрослой собаки с ограниченными возможностями
    String homeImprovementForDogWithDisabilities;
    //Советы кинолога по первичному общению с собакой
    String cynologistAdvice;
    //Список рекомендуемых приютом кинологов
    @ElementCollection
    @CollectionTable(name = "shelter_cynologist", joinColumns = @JoinColumn(name = "shelter_id"))
    @Column(name = "cynologist")
    List<String> cynologist;
    //Список причин, по которым могут отказать и не дать забрать собаку
    @ElementCollection
    @CollectionTable(name = "shelter_list_of_reason_for_rejection", joinColumns = @JoinColumn(name = "shelter_id"))
    @Column(name = "list_of_reason_for_rejection")
    List<String> listOfReasonForRejection;

    /**
     * Список собак, которые принадлежат приюту
     */
    @OneToMany(mappedBy = "shelter", fetch = FetchType.EAGER)
    @JsonBackReference
    Set<Dog> dogs;

    /**
     * Список волонтеров приюта
     */
    @OneToMany(mappedBy = "shelter", fetch=FetchType.EAGER)
    @JsonBackReference
    Set<Volunteer> volunteers;
}
