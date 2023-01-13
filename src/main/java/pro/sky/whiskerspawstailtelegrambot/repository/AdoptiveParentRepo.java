package pro.sky.whiskerspawstailtelegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;

/**
 * Репозиторий для усыновителей
 */
//@Repository
public interface AdoptiveParentRepo extends JpaRepository<AdoptiveParent, Long> {
    AdoptiveParent getAdoptiveParentByFullName(String fullName);
    AdoptiveParent getAdoptiveParentByPhone(String phone);
    AdoptiveParent getAdoptiveParentByChatId(Long chatId);

//    @Query(value = "SELECT state FROM adoptive_parent where :chatId == adoptive_parent.chat_id", nativeQuery = true)
//    AdoptiveParent getStateAdoptiveParentByChatId2(Long chatId);
//    @Query(value = "SELECT * FROM adoptive_parent where :chatId == adoptive_parent.chat_id", nativeQuery = true)
//    AdoptiveParent getAdoptiveParentByChatId2(@Param("chatId") Long chatId);



}
