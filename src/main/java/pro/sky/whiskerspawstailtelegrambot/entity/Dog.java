package pro.sky.whiskerspawstailtelegrambot.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;


/**
 * Собака. В база данных dog.
 */
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

    @Column(name = "name")
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

    /**
     * Приют, к которому принадлежит собака
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shelter_id")
    Shelter shelter;

    /**
     * Хозяин собаки
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adoptive_parent_id")
    AdoptiveParent adoptiveParent;


    /**
     * Отчеты хозяина для данной собаки
     */
    @OneToMany(mappedBy = "dog", fetch=FetchType.EAGER)
    @JsonManagedReference
    List<Report> reports;
}
