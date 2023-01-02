package pro.sky.whiskerspawstailtelegrambot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
//@Entity
/* Класс шаблона волонтера */
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    /* Поле полное имя волонтера */
    String fullName;
    /* Поле номер телефона волонтера */
    String phone;
}
