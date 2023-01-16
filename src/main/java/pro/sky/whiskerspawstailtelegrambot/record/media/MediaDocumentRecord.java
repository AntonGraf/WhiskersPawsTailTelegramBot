package pro.sky.whiskerspawstailtelegrambot.record.media;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pro.sky.whiskerspawstailtelegrambot.entity.media.MediaContent;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class MediaDocumentRecord implements Serializable {

  @JsonIgnore
  private Long id;
  private String telegramFileId;
  private String docName;
  private MediaContent MediaContent;
  private String mimeType;
  private Integer fileSize;





}
