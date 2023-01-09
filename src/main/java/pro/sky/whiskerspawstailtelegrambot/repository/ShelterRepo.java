package pro.sky.whiskerspawstailtelegrambot.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.entity.Shelter;

import java.util.Collection;

/**
 * Репозиторий для приюта
 */
@Repository
public interface ShelterRepo extends JpaRepository<Shelter, Long> {

}
