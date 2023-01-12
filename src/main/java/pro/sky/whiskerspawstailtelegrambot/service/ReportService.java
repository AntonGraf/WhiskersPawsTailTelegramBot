package pro.sky.whiskerspawstailtelegrambot.service;

import java.util.Collection;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.entity.Report;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.mapper.ReportMapper;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.ReportRepository;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.util.ParserToBot;
import pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.util.StateReport;

@Service
@Slf4j
@Transactional
public class ReportService {

  private final DogService dogService;
  private final ParserToBot parserToBot;
  private final FormReplyMessages formReplyMessages;
  private final ReportRepository reportRepository;
  private final AdoptiveParentService adoptiveParentService;
  private final ConfigKeyboard configKeyboard;

  private final ReportMapper reportMapper;

  public ReportService(DogService dogService, ParserToBot parserToBot,
      FormReplyMessages formReplyMessages, ReportRepository reportRepository,
      AdoptiveParentService adoptiveParentService,
      ConfigKeyboard configKeyboard, ReportMapper reportMapper) {
    this.dogService = dogService;
    this.parserToBot = parserToBot;
    this.formReplyMessages = formReplyMessages;
    this.reportRepository = reportRepository;
    this.adoptiveParentService = adoptiveParentService;
    this.configKeyboard = configKeyboard;
    this.reportMapper = reportMapper;
  }


  /**
   * Получить отчет по его Id
   * @param id  Id отчета
   * @return отчет по его Id
   */
  public ReportRecord getReportById(long id) {
    Report report = reportRepository.findById(id).orElseThrow(() -> new ElemNotFound());
    return reportMapper.toRecord(report);
  }

  /**
   * Получить отчет по Id животного
   * @param petId Id животного
   * @return отчет по id животного
   */
  public ReportRecord getReportByPetId(long petId) {

    Report report = reportRepository.getReportByPetId(petId);
    ReportRecord reportRecord = null;
    if (report != null) {
      reportRecord = reportMapper.toRecord(report);
    }
    return reportRecord;
  }

  /**
   * Создать новый отчет и сохранить его в базе данных
   * с привязкой к животному по его id
   * @param petId Id животного
   * @return новй созданый отчет
   */
  public ReportRecord addNewReportInDbForPetByPetId(long petId) {
    Report report = new Report();
    report.setDog_id(petId);
    reportRepository.save(report);
    return reportMapper.toRecord(report);
  }

  /**
   * Обновить существующий отчет по его id
   * @param id id отчета
   * @param newReportRecord новый отчет
   * @return обновленный отчет
   */
  public ReportRecord updateReportByReportId(long id, ReportRecord newReportRecord) {

    ReportRecord oldReportRecord = getReportById(id);
    oldReportRecord.setPhotoDog(newReportRecord.getPhotoDog());
    oldReportRecord.setDiet(newReportRecord.getDiet());
    oldReportRecord.setReportAboutFeelings(newReportRecord.getReportAboutFeelings());
    oldReportRecord.setReportAboutHabits(newReportRecord.getReportAboutHabits());
    oldReportRecord.setStateReport(newReportRecord.getStateReport());

    return reportMapper.toRecord(reportRepository.save(reportMapper.toEntity(oldReportRecord)));
  }


  /**
   * Показать всех животных принадлежащих пользователю
   *
   * @param message Message из Update
   * @return Список животных в  текстовом формате для отправки пользователю.
   */
  public String showAllAdoptedPets(Message message) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());
    long chatId = message.getChatId();
    Collection<DogRecord> dogRecordsFilter = dogService.findAllDog()
        .stream()
        .filter(d -> d.getAdoptiveParent() != null)
        .filter(d -> d.getAdoptiveParent().getChatId() == chatId)
        .collect(Collectors.toList());
    String allAdoptedPets = parserToBot.parserDog(dogRecordsFilter);
    return allAdoptedPets == null ? AllText.YOU_HAVE_NO_ADOPTED_PETS_TEXT : allAdoptedPets;
  }

  /**
   * Изменениние состояния пользователя
   *
   * @param message Message из Update
   * @param state   Список состояний пользвателя
   * @return true если получилось изменить состояние пользователя
   */
  public SendMessage changeStateAdoptiveParent(Message message, String textReplyMessage,
      StateAdoptiveParent state) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());
    long chatId = message.getChatId();

    AdoptiveParentRecord adoptiveParentRecord = adoptiveParentService.getAdoptiveParentByChatId(
        chatId);

    if (adoptiveParentRecord != null) {
      adoptiveParentRecord.setState(state.getText());
      adoptiveParentService.updateAdoptiveParent(adoptiveParentRecord.getId(),
          adoptiveParentRecord);
      return formReplyMessages.replyMessage(message,
          textReplyMessage,
          configKeyboard.initKeyboardOnClickStart());
    }
    return formReplyMessages.replyMessage(message,
        AllText.ERROR_REPLY_TEXT,
        configKeyboard.initKeyboardOnClickStart());
  }

  /**
   * Получить состояние пользователя, по его чат ид
   * @param chatId чат ид пользователя
   * @return состояние пользователя из бд
   */
  public StateAdoptiveParent getStateAdoptiveParentByChatId(long chatId) {
    return adoptiveParentService.getStateAdoptiveParentByChatId(chatId);
  }

  /**
   * Получить состояние отчета по id животного
   * @param petId id животного
   * @return Состояние отчета из бд или null
   */
  public StateReport getStateReportByPetId(long petId) {

    ReportRecord reportRecord = getReportByPetId(petId);
    StateReport stateReport;
    if (reportRecord != null) {
      return StateReport.valueOf(reportRecord.getStateReport());
    }
    return null;
  }



}
