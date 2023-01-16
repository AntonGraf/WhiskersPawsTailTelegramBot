package pro.sky.whiskerspawstailtelegrambot.service;

import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.whiskerspawstailtelegrambot.entity.Report;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.mapper.ReportMapper;
import pro.sky.whiskerspawstailtelegrambot.record.PetRecord;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.ReportRepository;
import pro.sky.whiskerspawstailtelegrambot.util.FilterAdoptedPets;
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
  public ReportRecord getReportById(long id) {
    Report report = reportRepository.findById(id).orElseThrow(() -> new ElemNotFound());
    return reportMapper.toRecord(report);
  }

  /**
   * Получить отчет по Id животного
   *
   * @param petId Id животного
   * @return отчет по id животного
   */
  public ReportRecord getReportByPetId(Long petId) {

    Report report = reportRepository.getReportByPet_id(petId);
    ReportRecord reportRecord = null;
    if (report != null) {
      reportRecord = reportMapper.toRecord(report);
    }
    return reportRecord;
  }

  /**
   * Получить незавершенный отчет по chatId пользователя
   */
  public ReportRecord getReportByChatIdAndIsReportCompletedFalse(Long chatId) {

    Report report = reportRepository.getReportByChatIdAndIsReportCompletedFalse(chatId);

    if (report != null) {
      return reportMapper.toRecord(report);
    }
    return null;
  }

  /**
   * Создать новый пустой отчет и сохранить его в базе данных
   *
   * @return новй созданый отчет
   */
  public ReportRecord addNewBlankReportWithChatId(Long chatId) {
    removeAllBlankReportByChatId(chatId);
    Report report = new Report();
    report.setChatId(chatId);
    report.setIsReportCompleted(false);
    reportRepository.save(report);
    return reportMapper.toRecord(report);
  }

  /**
   * удалить все отчеты по чат id со статусом false
   *
   * @param chatId
   */
  public List<Report> removeAllBlankReportByChatId(Long chatId) {
    List<Report> report = reportRepository.getAllByChatIdAndIsReportCompletedFalse(
        chatId);
    reportRepository.deleteAll(report);
    return report;
  }


  /**
   * Обновить существующий отчет по его id
   *
   * @param newReportRecord новый отчет
   * @return обновленный отчет
   */
  public ReportRecord updateReport(ReportRecord newReportRecord) {

    long id = newReportRecord.getId();

    ReportRecord oldReportRecord = getReportById(id);
    oldReportRecord.setPet_id(newReportRecord.getPet_id());
    oldReportRecord.setPhotoPet(newReportRecord.getPhotoPet());
    oldReportRecord.setDiet(newReportRecord.getDiet());
    oldReportRecord.setReportAboutFeelings(newReportRecord.getReportAboutFeelings());
    oldReportRecord.setReportAboutHabits(newReportRecord.getReportAboutHabits());
    oldReportRecord.setIsReportCompleted(newReportRecord.getIsReportCompleted());
    oldReportRecord.setDateTime(newReportRecord.getDateTime());

    reportRepository.save(reportMapper.toEntity(oldReportRecord));
    return newReportRecord;
  }


  /**
   * Показать всех животных принадлежащих пользователю
   *
   * @param chatId чат id пользователя
   * @return Список животных в  текстовом формате для отправки пользователю.
   */
  public String showAllAdoptedPetsByChatId(Long chatId) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());

    Collection<PetRecord> allPet = petService.findAllPet();
    if (allPet != null) {
      Collection<PetRecord> petRecords = petService.findAllPet();

      FilterAdoptedPets filterAdoptedPets = new FilterAdoptedPets();
      petRecords = filterAdoptedPets.byChatId(chatId, petRecords);

      ParserToBot parserToBot = new ParserToBot();
      String allAdoptedPets = parserToBot.parserPet(petRecords);
      return allAdoptedPets;
    }
    return null;
  }

  /**
   * получить питомца по его ID
   *
   * @param petId
   * @return
   */
  public PetRecord getPetByPetId(Long petId) {
    PetRecord petRecord;
    try {
      petRecord = petService.findPet(petId);
    } catch (Exception e) {
      petRecord = null;
    }
    return petRecord;
  }

}
