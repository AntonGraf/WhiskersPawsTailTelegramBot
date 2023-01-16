package pro.sky.whiskerspawstailtelegrambot.service;

import java.util.Collection;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.whiskerspawstailtelegrambot.entity.Report;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFoundChecked;
import pro.sky.whiskerspawstailtelegrambot.loger.FormLogInfo;
import pro.sky.whiskerspawstailtelegrambot.mapper.ReportMapper;
import pro.sky.whiskerspawstailtelegrambot.record.PetRecord;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.ReportRepository;
import pro.sky.whiskerspawstailtelegrambot.util.ParserToBot;

@Service
@Slf4j
@Transactional
public class ReportService {

  private final PetService petService;
  private final ReportRepository reportRepository;
  private final AdoptiveParentService adoptiveParentService;
  private final ReportMapper reportMapper;

  public ReportService(PetService petService, ReportRepository reportRepository,
      AdoptiveParentService adoptiveParentService, ReportMapper reportMapper) {
    this.petService = petService;
    this.reportRepository = reportRepository;
    this.adoptiveParentService = adoptiveParentService;
    this.reportMapper = reportMapper;
  }

  /**
   * Получить отчет по его Id
   *
   * @param id Id отчета
   * @return отчет по его Id
   */
  public ReportRecord getReportById(Long id) throws ElemNotFoundChecked {
    log.info(FormLogInfo.getInfo());
    Report report = reportRepository.findById(id)
        .orElseThrow(() -> new ElemNotFoundChecked("Отчет по такому id не найден"));
    return reportMapper.toRecord(report);
  }

  /**
   * Получить незавершенный отчет по chatId пользователя
   */
  public ReportRecord getReportByChatIdAndIsReportCompletedFalse(Long chatId)
      throws Exception {
    log.info(FormLogInfo.getInfo());
    Report allReportCompletedFalse = reportRepository.getByChatIdAndIsReportCompletedFalse(
        chatId).orElseThrow(Exception::new);//может возникнуть ситуация когда отчетов будет несколько, тогда исключение

    return reportMapper.toRecord(allReportCompletedFalse);
  }

  /**
   * Создать новый пустой отчет и сохранить его в базе данных
   *
   * @return новй созданый отчет
   */
  public ReportRecord addNewBlankReportWithChatId(Long chatId) {
    log.info(FormLogInfo.getInfo());
    Report report = new Report();
    report.setChatId(chatId);
    report.setIsReportCompleted(false);
    reportRepository.save(report);
    return reportMapper.toRecord(report);
  }

  /**
   * удалить все отчеты по чат id со статусом false используется при нажатии отправить отчет и при
   * отмене отчета для того что бы не создавались новые пустые отчеты, в случае некоректного
   * заполнения
   *
   * @param chatId
   */
  public void deleteAllByChatIdAndIsReportCompletedFalse(Long chatId) {
    Collection<Report> allStartedReport = reportRepository.getAllByChatIdAndIsReportCompletedFalse(
        chatId);
    reportRepository.deleteAll(allStartedReport);
  }

  /**
   * Обновить существующий отчет по его id
   *
   * @param newReportRecord новый отчет
   * @return обновленный отчет
   */
  public ReportRecord updateReport(ReportRecord newReportRecord) {
    log.info(FormLogInfo.getInfo());

    ReportRecord oldReportRecord = null;
    try {
      Long id = newReportRecord.getId();
      oldReportRecord = getReportById(id);
      oldReportRecord.setPet_id(newReportRecord.getPet_id());
      oldReportRecord.setPhotoPet(newReportRecord.getPhotoPet());
      oldReportRecord.setDiet(newReportRecord.getDiet());
      oldReportRecord.setReportAboutFeelings(newReportRecord.getReportAboutFeelings());
      oldReportRecord.setReportAboutHabits(newReportRecord.getReportAboutHabits());
      oldReportRecord.setIsReportCompleted(newReportRecord.getIsReportCompleted());
      oldReportRecord.setDateTime(newReportRecord.getDateTime());
      reportRepository.save(reportMapper.toEntity(oldReportRecord));
    } catch (ElemNotFoundChecked e) {
      throw new RuntimeException("Невозможно обновть ReportRecord");
    }
    return newReportRecord;
  }


  /**
   * Показать всех животных принадлежащих пользователю
   *
   * @param chatId чат id пользователя
   * @return Список животных в  текстовом формате для отправки пользователю.
   */
  public String showAllAdoptedPetsByChatId(Long chatId) throws Exception {
    log.info(FormLogInfo.getInfo());

    Collection<PetRecord> allPetByChatId = petService.getAllPetAdoptiveParentByChatId(chatId);

      ParserToBot parserToBot = new ParserToBot();
      return parserToBot.parserPet(allPetByChatId);

  }

  /**
   * получить питомца по его ID
   *
   * @param petId
   * @return
   */
  public PetRecord getPetByPetId(Long petId) throws ElemNotFound {
    return petService.findPet(petId);
  }

}
