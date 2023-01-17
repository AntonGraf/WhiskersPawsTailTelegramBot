package pro.sky.whiskerspawstailtelegrambot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.whiskerspawstailtelegrambot.TelegramBotUpdatesListener;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.entity.Pet;
import pro.sky.whiskerspawstailtelegrambot.entity.Report;
import pro.sky.whiskerspawstailtelegrambot.mapper.PetMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.PetMapperImpl;
import pro.sky.whiskerspawstailtelegrambot.mapper.ReportMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.ReportMapperImpl;
import pro.sky.whiskerspawstailtelegrambot.repository.ReportRepository;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ValidateReportServiceTest {

    @Mock
    ReportRepository reportRepository;
    @Mock
    ReportMapper reportMapper;
    @Mock
    TestPeriodService testPeriodService;
    @Mock
    TelegramBotUpdatesListener bot;
    @InjectMocks
    ValidateReportService out;
    ReportMapper mapper = new ReportMapperImpl();
    PetMapper petMap = new PetMapperImpl();

    @Test
    void getReportsByLastDay() {
        Collection<Report> testReports = getTestReports().stream()
                .filter(report ->
                        report.getDateTime().isAfter(LocalDateTime.of(2023, 1, 16, 0, 0)))
                .collect(Collectors.toList());

        testReports.forEach(this::fillReports);

        lenient().when(reportRepository.findReportsByDateTimeAfter(any(LocalDateTime.class)))
                .thenReturn(List.copyOf(testReports));
        lenient().when(reportMapper.toRecordList(anyCollection())).thenReturn(mapper.toRecordList(testReports));

        assertTrue(out.getReportsByLastDay().containsAll(mapper.toRecordList(testReports)));
    }

    @Test
    void getBadReportsByLastDay() {
        Collection<Report> testReports = getTestReports().stream()
                .filter(report ->
                        report.getDateTime().isAfter(LocalDateTime.of(2023, 1, 16, 0, 0)))
                .collect(Collectors.toList());

        Report testReport1 = getTestReport();
        fillReports(testReport1);
        testReport1.setReportAboutHabits(null);
        testReports.add(testReport1);

        Report testReport2 = getTestReport();
        fillReports(testReport2);
        testReport2.setReportAboutFeelings(null);
        testReports.add(testReport2);

        Report testReport3 = getTestReport();
        fillReports(testReport3);
        testReport3.setDiet(null);
        testReports.add(testReport3);

        Report testReport4 = getTestReport();
        fillReports(testReport4);
        testReport4.setPhotoPet(null);
        testReports.add(testReport4);

        lenient().when(reportRepository.findReportsByDateTimeAfter(any(LocalDateTime.class)))
                .thenReturn(List.copyOf(testReports));
        lenient().when(reportMapper.toRecordList(anyCollection())).thenReturn(mapper.toRecordList(testReports));

        assertTrue(out.getBadReportsByLastDay().containsAll(mapper.toRecordList(testReports)));

    }

    @Test
    void sendMessageToAdoptiveParentsWithBadReport() throws TelegramApiException {
        Collection<Report> testReports = getTestReports().stream()
                .filter(report ->
                        report.getDateTime().isAfter(LocalDateTime.of(2023, 1, 16, 0, 0)))
                .collect(Collectors.toList());
        lenient().when(reportRepository.findReportsByDateTimeAfter(any(LocalDateTime.class)))
                .thenReturn(List.copyOf(testReports));
        Report report = getTestReport();
        report.setPet(getTestPet());
        lenient().when(reportMapper.toRecordList(anyCollection())).thenReturn(mapper.toRecordList(List.of(report)));
        SendMessage sendMessage = new SendMessage(String.valueOf(213), AllText.BAD_REPORT);
        Message actualMessage = new Message();
        actualMessage.setText(AllText.BAD_REPORT);
        actualMessage.setChat(new Chat(213L, "private"));
        lenient().when(bot.execute(sendMessage)).thenReturn(actualMessage);
        assertDoesNotThrow(() -> out.sendMessageToAdoptiveParentsWithBadReport());
    }

    @Test
    void sendMessageToAdoptiveParentAnyReportLastDays() throws TelegramApiException {
        Collection<Report> testReports = getTestReports().stream()
                .filter(report ->
                        report.getDateTime().isBefore(LocalDateTime.of(2023, 1, 16, 0, 0)))
                .collect(Collectors.toList());

        Pet pet = getTestPet();

        lenient().when(testPeriodService.getPetsHaveTestPeriod())
                .thenReturn(petMap.toRecordList(List.of(pet)));

        lenient().when(reportRepository.countByPet_idAndDateTimeAfter(anyLong(), any(LocalDateTime.class)))
                .thenReturn(0L);

        SendMessage sendMessage = new SendMessage(String.valueOf(213), AllText.NO_REPORT);
        Message actualMessage = new Message();
        actualMessage.setText(AllText.NO_REPORT);
        actualMessage.setChat(new Chat(213L, "private"));
        lenient().when(bot.execute(sendMessage)).thenReturn(actualMessage);
        assertDoesNotThrow(() -> out.sendMessageToAdoptiveParentAnyReportLastDays(2));
    }

    @Test
    void sendMessageToAdoptiveParentAnyReportLastDaysNegativeTest() throws TelegramApiException {
        Collection<Report> testReports = getTestReports().stream()
                .filter(report ->
                        report.getDateTime().isBefore(LocalDateTime.of(2023, 1, 16, 0, 0)))
                .collect(Collectors.toList());

        Pet pet = getTestPet();

        lenient().when(testPeriodService.getPetsHaveTestPeriod())
                .thenReturn(petMap.toRecordList(List.of(pet)));

        lenient().when(reportRepository.countByPet_idAndDateTimeAfter(anyLong(), any(LocalDateTime.class)))
                .thenReturn(0L);

        SendMessage sendMessage = new SendMessage(String.valueOf(213), AllText.NO_REPORT);
        Message actualMessage = new Message();
        actualMessage.setText(AllText.NO_REPORT);
        actualMessage.setChat(new Chat(213L, "private"));
        lenient().when(bot.execute(any(SendMessage.class))).thenThrow(TelegramApiException.class);
        assertDoesNotThrow(() -> out.sendMessageToAdoptiveParentAnyReportLastDays(2));
    }

    private Collection<Report> getTestReports() {
        List<Report> reports = new ArrayList<>();
        reports.add(getTestReport());

        Report report = new Report();
        report.setId(2L);
        report.setChatId(213L);
        report.setDateTime(LocalDateTime.of(2023, 1, 16, 11, 0));
        reports.add(report);

        return reports;
    }

    private Report getTestReport() {
        Report report = new Report();
        report.setId(1L);
        report.setChatId(213L);
        report.setDateTime(LocalDateTime.of(2023, 1, 15, 12, 0));
        report.setPet(getTestPet());

        return report;
    }

    private Pet getTestPet() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setFullName("Шарик");
        pet.setAdoptiveParent(getTestAdoptiveParent());
        pet.setTestPeriod(true);
        pet.setTestPeriodTime(LocalDateTime.of(2023, 2, 15, 12, 0));

        return pet;
    }

    private AdoptiveParent getTestAdoptiveParent() {
        AdoptiveParent adoptiveParent = new AdoptiveParent();
        adoptiveParent.setId(1L);
        adoptiveParent.setChatId(213L);
        return adoptiveParent;
    }

    private byte[] getTestPhoto() {
        byte[] photo = {12, 12, 14};
        return photo;
    }

    private void fillReports(Report report) {
        report.setReportAboutFeelings("dsfadsfas");
        report.setReportAboutHabits("asdafsd");
        report.setPhotoPet(getTestPhoto());
        report.setDiet("dsfsaf");
    }
}