package pro.sky.whiskerspawstailtelegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.whiskerspawstailtelegrambot.entity.Report;

/**
 * Репозиторий для собак
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

   Report getReportByDog_id(Long id);

}
