package pro.sky.whiskerspawstailtelegrambot.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;

import javax.persistence.*;
import java.util.List;

/**
 * Усыновитель. В база данных adoptive_parent.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "adoptive_parent")
@Slf4j
public class AdoptiveParent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    /**
     * Поле полного имени
     */
    String fullName;
    /**
     * Поле телефона
     */
    String phone;

    /**
     * Состояние (этапы) по которому проходит пользователь,
     * от первоначального взятия животного, до полного одобрения со стороны приюта
     * */
    String state;

    /**
     * явялется ли усыновителем или нет
     */
    boolean isParent;

    /**
     * Список собак
     */
    @OneToMany(mappedBy = "adoptiveParent", fetch=FetchType.EAGER)
    @JsonBackReference
    List<Dog> dogs;
}
