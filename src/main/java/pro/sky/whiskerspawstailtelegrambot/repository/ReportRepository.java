package pro.sky.whiskerspawstailtelegrambot.repository;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.whiskerspawstailtelegrambot.entity.Report;

/**
 * Репозиторий для питомцев
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

  Report getReportByPet_id(Long id);

  Report getReportByChatIdAndIsReportCompletedFalse(Long chatId);

  List<Report> getReportsByChatIdAndIsReportCompletedFalse(Long chatId);

  Report getReportByIsReportCompletedFalseAndChatId(Long chatId);
  List<Report> getAllByIsReportCompletedFalseAndChatId(Long chatId);

}
