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
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    Byte[] photoDog;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonBackReference
    private Dog dog;

}

