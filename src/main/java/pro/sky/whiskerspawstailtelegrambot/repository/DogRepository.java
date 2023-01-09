package pro.sky.whiskerspawstailtelegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;

import java.util.Collection;

/**
 * Репозиторий для собак
 */
@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE dog SET adoptive_parent_id = :adoptiveParentId WHERE id = :dogId")
    void addIdAdoptiveParent (Long dogId, Long adoptiveParentId);


}
