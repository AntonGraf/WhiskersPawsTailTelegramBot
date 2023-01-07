package pro.sky.whiskerspawstailtelegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;

/**
 * Репозиторий для усыновителей
 */
@Repository
public interface AdoptiveParentRepo extends JpaRepository<AdoptiveParent, Long> {
}
