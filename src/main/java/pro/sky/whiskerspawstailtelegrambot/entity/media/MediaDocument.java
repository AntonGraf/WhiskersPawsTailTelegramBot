package pro.sky.whiskerspawstailtelegrambot.entity.media;

import com.google.common.base.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "media_document")
public class MediaDocument {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String telegramFileId;
  private String docName;
  @OneToOne
  private MediaContent mediaContent;
  private String mimeType;
  private Integer fileSize;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MediaDocument that = (MediaDocument) o;
    return Objects.equal(id, that.id) && Objects.equal(
        telegramFileId, that.telegramFileId) && Objects.equal(docName,
        that.docName) && Objects.equal(mediaContent, that.mediaContent)
        && Objects.equal(mimeType, that.mimeType)
        && Objects.equal(fileSize, that.fileSize);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, telegramFileId, docName, mediaContent, mimeType, fileSize);
  }
}