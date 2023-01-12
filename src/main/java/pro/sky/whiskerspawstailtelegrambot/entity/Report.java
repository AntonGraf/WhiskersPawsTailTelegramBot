package pro.sky.whiskerspawstailtelegrambot.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;


/**
 * Сущность для отчета о животном
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    /**
     * Рацион животного
     */
    @Column(name = "diet")
    String diet;

    /**
     * Общее самочувствие и привыкание к новому месту
     */
    @Column(name = "report_about_feelings")
    String reportAboutFeelings;

    /**
     * Изменение в поведении: отказ от старых привычек, приобретение новых
     */
    @Column(name = "report_about_habits")
    String reportAboutHabits;

    /**
     * Фото животного
     */
    @Column(name = "photo_dog")
    byte[] photoDog;

    /**
     * id собаки
     */
    @Column(name = "dog_id")
    long dog_id;

    /**
     * Присоединение к собаке
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dog_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    Dog dog;

    /**
     * Этапы заполнения отчета
     */
    @Column(name = "state_report")
    String stateReport;

}

