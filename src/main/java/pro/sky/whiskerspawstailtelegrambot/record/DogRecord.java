package pro.sky.whiskerspawstailtelegrambot.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.entity.Shelter;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link pro.sky.whiskerspawstailtelegrambot.entity.Dog} entity
 */
@AllArgsConstructor
//@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DogRecord implements Serializable {

    Long id;


    String fullName;

    String age;


    String description;


    @JsonIgnore
    String filePath;

    @JsonIgnore
    long fileSize;

    @JsonIgnore
    String mediaType;

    @JsonIgnore
    byte[] photo;

    @JsonIgnore
    Shelter shelter;
    @JsonIgnore
    AdoptiveParent adoptiveParent;

    @JsonIgnore
    List<ReportRecord> reports;

}