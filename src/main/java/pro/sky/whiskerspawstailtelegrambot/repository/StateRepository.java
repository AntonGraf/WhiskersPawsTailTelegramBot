package pro.sky.whiskerspawstailtelegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;

@Repository
@Transactional
public interface StateRepository extends
    JpaRepository<AdoptiveParent, Long> {

  AdoptiveParent getAdoptiveParentByChatId(Long chatId);
}
