package pro.sky.whiskerspawstailtelegrambot.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.whiskerspawstailtelegrambot.entity.Report;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Репозиторий для питомцев
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

//  Report getReportByChatIdAndIsReportCompletedFalse(Long chatId);

  Optional<Report> getByChatIdAndIsReportCompletedFalse(Long chatId);

  List <Report> getAllByChatIdAndIsReportCompletedFalse(Long chatId);


  //
//  Optional<Report> getReportByChatIdAndIsReportCompletedFalse(Long chatId);
//  Optional<List<Report>> getAllByChatIdAndIsReportCompletedFalse(Long chatId);
//
//  Optional<List<Report>> getAllByChatIdAndIsReportCompletedFalse(Long chatId);

//  List<Report> getReportsByChatIdAndIsReportCompletedFalse(Long chatId);
//
//  Report getReportByIsReportCompletedFalseAndChatId(Long chatId);

  List<Report> findReportsByDateTimeAfter(LocalDateTime dateTime);

  Long countByPet_idAndDateTimeAfter(Long petId, LocalDateTime dateTime);
}
