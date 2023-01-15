package pro.sky.whiskerspawstailtelegrambot.entity.media;

import com.google.common.base.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "media_content")
public class MediaContent {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private byte[] fileAsArrayOfBytes;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MediaContent that = (MediaContent) o;
    return Objects.equal(id, that.id) && Objects.equal(
        fileAsArrayOfBytes, that.fileAsArrayOfBytes);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, fileAsArrayOfBytes);
  }
}
