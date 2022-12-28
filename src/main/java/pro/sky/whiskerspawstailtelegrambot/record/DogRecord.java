package pro.sky.whiskerspawstailtelegrambot.record;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link pro.sky.whiskerspawstailtelegrambot.entity.Dog} entity
 */
@AllArgsConstructor
@Getter
public class DogRecord implements Serializable {
    private final long id;
    private final String fullName;
    private final int age;
    private final Byte[] photo;
    private final String description;
}