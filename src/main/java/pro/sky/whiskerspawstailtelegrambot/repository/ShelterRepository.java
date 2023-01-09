package pro.sky.whiskerspawstailtelegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.whiskerspawstailtelegrambot.entity.Shelter;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}