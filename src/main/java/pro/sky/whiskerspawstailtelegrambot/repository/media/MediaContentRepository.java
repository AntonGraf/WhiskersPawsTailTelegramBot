package pro.sky.whiskerspawstailtelegrambot.repository.media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.whiskerspawstailtelegrambot.entity.media.MediaContent;

@Repository
public interface MediaContentRepository extends JpaRepository<MediaContent, Long> {

}
