package pro.sky.whiskerspawstailtelegrambot.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.entity.Report;
import pro.sky.whiskerspawstailtelegrambot.entity.Shelter;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link pro.sky.whiskerspawstailtelegrambot.entity.Dog} entity
 */
@AllArgsConstructor
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DogRecord implements Serializable {
    long id;
    @NotNull
    String fullName;

    @NotNull
    int age;

    @NotNull
    String description;

    String filePath;

    long fileSize;

    String mediaType;

    byte[] photo;

    @JsonIgnore
    Shelter shelter;


    @JsonIgnore
    AdoptiveParent adoptiveParent;

    @JsonIgnore
    List<ReportRecord> reports;

}