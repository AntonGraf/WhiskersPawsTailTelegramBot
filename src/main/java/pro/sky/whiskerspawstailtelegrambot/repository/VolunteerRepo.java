package pro.sky.whiskerspawstailtelegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.whiskerspawstailtelegrambot.entity.Volunteer;

import java.util.Optional;

/**
 * Репозиторий для волонтеров
 */
@Repository
@Transactional
public interface VolunteerRepo extends JpaRepository<Volunteer, Long> {
    Optional<Volunteer> findVolunteerByFullName(String fullName);
}
