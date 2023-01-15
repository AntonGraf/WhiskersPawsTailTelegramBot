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
@Table(name = "media_photo")
public class MediaPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telegramFileId;
    @OneToOne
    private MediaContent mediaContent;
    private Integer fileSize;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MediaPhoto mediaPhoto = (MediaPhoto) o;
        return Objects.equal(id, mediaPhoto.id)
            && Objects.equal(telegramFileId, mediaPhoto.telegramFileId)
            && Objects.equal(mediaContent, mediaPhoto.mediaContent)
            && Objects.equal(fileSize, mediaPhoto.fileSize);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, telegramFileId, mediaContent, fileSize);
    }
}

