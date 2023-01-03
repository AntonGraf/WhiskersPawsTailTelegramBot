package pro.sky.whiskerspawstailtelegrambot.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * Класс сущность, взаимодействует с таблицей БД, отвечает за пользователя, который хочет взять
 * животное к себе.
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
  @Column(name = "id")
  long id;

  @Column(name = "chat_id")
  long chatId;
  @Column(name = "full_name")
  String fullName;
  @Column(name = "phone")
  String phone;

  /**
   * Состояние (этапы) по которому проходит пользователь, от первоначального взятия животного, до
   * полного одобрения со стороны приюта
   */
  @Column(name = "state")
  String state;
  @Column(name = "is_parent")
  boolean isParent;

}
