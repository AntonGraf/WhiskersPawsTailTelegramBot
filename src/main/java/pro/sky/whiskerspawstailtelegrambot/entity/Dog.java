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
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column (name = "name")
    String fullName;

    /**
     * При создании таблицы будет установлено ограничение: age > 0
     */
    int age;

    /**
     * Необходимо установить ограничение на размер файла при заполнении БД. Например в 1024Кб.
     * Продублировать это ограничение в контроллер для Swagger API для заполнения БД.
     */
    Byte[] photo;
    String description;
}
