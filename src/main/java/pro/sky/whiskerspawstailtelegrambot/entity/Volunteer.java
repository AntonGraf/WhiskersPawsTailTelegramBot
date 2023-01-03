package pro.sky.whiskerspawstailtelegrambot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
/** Класс шаблона волонтера */
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    /** Поле полное имя волонтера */
    @Column(name = "full_name")
    String fullName;
    /** Поле номер телефона волонтера */
    @Column(name = "phone")
    String phone;
}
