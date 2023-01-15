package pro.sky.whiskerspawstailtelegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;

@Repository
public interface StateRepository extends
    JpaRepository<AdoptiveParent, Long> {

  AdoptiveParent getAdoptiveParentByChatId(Long chatId);
}
