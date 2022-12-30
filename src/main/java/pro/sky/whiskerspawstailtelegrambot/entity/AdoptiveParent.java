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
public class AdoptiveParent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String fullName;
    String phone;
    String state;
    boolean status;

}
