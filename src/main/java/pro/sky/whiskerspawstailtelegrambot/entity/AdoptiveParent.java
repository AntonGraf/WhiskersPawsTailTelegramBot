package pro.sky.whiskerspawstailtelegrambot.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

/**
 * Класс сущность, взаимодействует с таблицей БД adoptive_parent, отвечает за пользователя, который
 * хочет взять животное к себе.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "adoptive_parent")
@Slf4j
public class AdoptiveParent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;
    /**
     * Поле полного имени
     */
    @Column(name = "full_name")
    String fullName;
    /**
     * Поле телефона
     */
    @Column(name = "phone")
    String phone;

    /**
     * явялется ли усыновителем или нет
     */
    @Column(name = "is_parent")
    boolean isParent;
    /**
     * Состояние (этапы) по которому проходит пользователь, от первоначального взятия животного, до
     * полного одобрения со стороны приюта
     */
    @Column(name = "state")
    String state;
    /**
     * chat id для отправки обратного сообщения
     */
    @Column(name = "chat_id")
    long chatId;

    /**
     * Список собак
     */
    @OneToMany(mappedBy = "adoptiveParent", fetch = FetchType.EAGER)
    @JsonBackReference
    List<Dog> dogs;

}
