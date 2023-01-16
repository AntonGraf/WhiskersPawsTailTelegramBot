package pro.sky.whiskerspawstailtelegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.whiskerspawstailtelegrambot.TelegramBotUpdatesListener;
import pro.sky.whiskerspawstailtelegrambot.mapper.ReportMapper;
import pro.sky.whiskerspawstailtelegrambot.record.PetRecord;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.ReportRepository;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ValidateReportService {
    //Репозиторий с отчетами
    private final ReportRepository reportRepository;
    //Сервис с испытательными сроками
    private final TestPeriodService testPeriodService;
    //Маппер отчетов
    private final ReportMapper reportMapper;
    //Подключенный бот для отправки сообщений
    private final TelegramBotUpdatesListener bot;

    public ValidateReportService(ReportRepository reportRepository, TestPeriodService testPeriodService,
                                 ReportMapper reportMapper,
                                 TelegramBotUpdatesListener bot) {
        this.reportRepository = reportRepository;
        this.testPeriodService = testPeriodService;
        this.reportMapper = reportMapper;
        this.bot = bot;
    }

    /**
     * Возвращает список отчетов, присланных за посление 24 часа
     * @return  - список отчетов
     */
    public Collection<ReportRecord> getReportsByLastDay() {
        log.info("Получаем отчеты за последние сутки");
        return reportMapper.toRecordList(reportRepository.findReportsByDateTimeAfter(LocalDateTime.now().minusDays(1)));
    }

    /**
     * Возвращает список плохих отчетов
     * @return      - список плохих отчетов
     */
    public Collection<ReportRecord> getBadReportsByLastDay() {
        log.info("Получаем некорректно заполненные отчеты за последние сутки");
        return getReportsByLastDay().stream()
                .filter(this::isBadReport)
                .collect(Collectors.toList());
    }

    /**
     * Отправляет сообщение усыновителям, которые плохо заполнили отчет
     */
    public void sendMessageToAdoptiveParentsWithBadReport() {
        log.info("Отправляем сообщение усыновителям, приславших плохой отчет");
        getBadReportsByLastDay().stream()
                .map(report -> report.getPet().getAdoptiveParent().getChatId())
                .forEach(chatId -> sendMessage(chatId, AllText.BAD_REPORT));
    }

    /**
     * Отправляет сообщение усыновителям, которые не прислали отчеты за последние дни
     * @param countDays - количество дней, которые усыновители не присылали отчеты
     */
    public void sendMessageToAdoptiveParentAnyReportLastDays(int countDays) {
        log.info("Отправляем сообщение усыновителям, не приславших отчеты за последние два дня");
        Collection<PetRecord> petRecordsWithTestPeriod = testPeriodService.getPetsHaveTestPeriod();
        LocalDateTime reportDateTime = LocalDateTime.now().minusDays(countDays);

        for (PetRecord petRecord : petRecordsWithTestPeriod) {
            long countReports = reportRepository.countByPet_idAndDateTimeAfter(petRecord.getId(), reportDateTime);
            if (countReports == 0) {
                sendMessage(petRecord.getAdoptiveParent().getChatId(), AllText.NO_REPORT);
            }
        }
    }

    /**
     * Отправка сообщения усыновителю, через телеграмбот
     * @param chatId    - куда отправлять сообщение
     * @param text      - какой текст отправлять
     */
    private void sendMessage(long chatId, String text)  {
        log.debug("Отправляем сообщение " + chatId);
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), text);
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения " + chatId);
        }
    }

    /**
     * Проверяет на корректность отчеты
     * @param reportRecord    - проверяемый отчет
     * @return          - true, если отчет заполнен не корректно
     */
    private boolean isBadReport(ReportRecord reportRecord) {
        if (reportRecord.getDiet() == null || reportRecord.getDiet().isEmpty()) {
            return true;
        }
        if (reportRecord.getPhotoPet() == null || reportRecord.getPhotoPet().length < 100) {
            return true;
        }
        if (reportRecord.getReportAboutFeelings() == null || reportRecord.getReportAboutFeelings().isEmpty()) {
            return true;
        }
        if (reportRecord.getReportAboutHabits() == null || reportRecord.getReportAboutHabits().isEmpty()) {
            return true;
        }
        return false;
    }
}
