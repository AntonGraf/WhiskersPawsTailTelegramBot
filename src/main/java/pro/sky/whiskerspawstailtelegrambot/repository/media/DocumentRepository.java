package pro.sky.whiskerspawstailtelegrambot.repository.media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.whiskerspawstailtelegrambot.entity.media.MediaDocument;

@Repository
public interface DocumentRepository extends JpaRepository<MediaDocument, Long> {

}
