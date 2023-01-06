package pro.sky.whiskerspawstailtelegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;

/**
 * Репозиторий для собак
 */
@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {

}
