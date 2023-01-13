package pro.sky.whiskerspawstailtelegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.service.StateChangeAdoptiveParentService;

@Repository
public interface StateChangeAdoptiveParentRepository extends
    JpaRepository<AdoptiveParent, Long> {

  AdoptiveParent getAdoptiveParentByChatId(Long chatId);
}
