package pro.sky.whiskerspawstailtelegrambot.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.entity.Pet;
import pro.sky.whiskerspawstailtelegrambot.entity.Shelter;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link Pet} entity
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PetRecord implements Serializable {
    Long id;
    @NotNull
    String fullName;

    @NotNull
    String age;

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