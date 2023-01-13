package pro.sky.whiskerspawstailtelegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.whiskerspawstailtelegrambot.entity.Pet;

/**
 * Репозиторий для питомцев
 */
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

  @Modifying
  @Query(nativeQuery = true, value = "UPDATE pet SET adoptive_parent_id = :adoptiveParentId WHERE id = :petId")
  void addIdAdoptiveParent (Long petId, Long adoptiveParentId);


}
