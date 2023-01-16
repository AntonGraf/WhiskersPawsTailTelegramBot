package pro.sky.whiskerspawstailtelegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;

/**
 * Репозиторий для усыновителей
 */
@Repository
@Transactional
public interface AdoptiveParentRepo extends JpaRepository<AdoptiveParent, Long> {
    AdoptiveParent getAdoptiveParentByFullName(String fullName);
    AdoptiveParent getAdoptiveParentByPhone(String phone);
    AdoptiveParent getAdoptiveParentByChatId(Long chatId);




}
