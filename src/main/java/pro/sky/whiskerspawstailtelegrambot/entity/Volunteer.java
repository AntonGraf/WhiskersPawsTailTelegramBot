package pro.sky.whiskerspawstailtelegrambot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * Класс шаблона волонтера
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    /** Информация о волонтерах */
    @Column(name = "info_volunteer")
    String infoVolunteer;
    /** Поле полное имя волонтера */
    @Column(name = "full_name")
    String fullName;
    /** Поле номер телефона волонтера */
    @Column(name = "phone")
    String phone;
    /** Расписание волонтера */
    @Column(name = "schedule")
    String schedule;

    /**
     * Приют, которому помогает волонтер
     */
    @ManyToOne(cascade=CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "shelter_id")
    Shelter shelter;
}
