package pro.sky.whiskerspawstailtelegrambot.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.entity.Report;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.mapper.ReportMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.ReportMapperImpl;
import pro.sky.whiskerspawstailtelegrambot.record.PetRecord;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.PetRepository;
import pro.sky.whiskerspawstailtelegrambot.repository.ReportRepository;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {


  @Mock
  ReportRepository reportRepository;
  @Spy
  ReportMapper reportMapper = new ReportMapperImpl();
  //  @Mock
//  ReportMapper reportMapper;
  @InjectMocks
  ReportService reportService;

  @Mock
  PetService petService;
  @Mock
  PetRepository petRepository;

  ReportRecord reportRecord;
  ReportRecord updateReportRecord;
  Report report;
  Report updateReport;

  @BeforeEach
  void setUp() throws IOException {

    reportRecord = new ReportRecord();
    updateReportRecord = new ReportRecord();
    report = new Report();
    updateReport = new Report();

    byte[] data;
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
      BufferedImage bImage = ImageIO.read(new File("./src/test/img/photoForTest.png"));
      ImageIO.write(bImage, "png", bos);
      data = bos.toByteArray();
    }
    reportRecord.setId(1l);
    reportRecord.setPet_id(1l);
//    reportRecord.setPhotoDog(data);
    reportRecord.setDiet("test ");
    reportRecord.setReportAboutFeelings("test ");
    reportRecord.setReportAboutHabits("test ");
    reportRecord.setIsReportCompleted(false);
    reportRecord.setChatId(999l);
//    reportRecord.setStateReport("FINISHED");

    updateReportRecord.setId(1l);
    updateReportRecord.setPet_id(1l);
//    updateReportRecord.setPhotoDog(data);
    updateReportRecord.setDiet("update test ");
    updateReportRecord.setReportAboutFeelings("update test ");
    updateReportRecord.setReportAboutHabits("update test ");
    updateReportRecord.setIsReportCompleted(false);
    updateReportRecord.setChatId(999l);
//    updateReportRecord.setStateReport("NOT_STARTED");

    report.setId(1l);
    report.setPet_id(1l);
//    report.setPhotoDog(data);
    report.setDiet("test ");
    report.setReportAboutFeelings("test ");
    report.setReportAboutHabits("test ");
    report.setIsReportCompleted(false);
    report.setChatId(999l);
//    report.setStateReport("FINISHED");

    updateReport.setId(1l);
    updateReport.setPet_id(1l);
//    newReport.setPhotoDog(data);
    updateReport.setDiet("update test ");
    updateReport.setReportAboutFeelings("update test ");
    updateReport.setReportAboutHabits("update test ");
    updateReport.setIsReportCompleted(false);
    updateReport.setChatId(999l);
//    updateReport.setStateReport("NOT_STARTED");
  }
  @Test
  void getReportById() {
    when(reportRepository.findById(anyLong())).thenReturn(Optional.of(report));
    when(reportMapper.toRecord(any(Report.class))).thenReturn(reportRecord);

    assertThat(reportService.getReportById(1l)).isEqualTo(reportRecord);
    verify(reportRepository, times(1)).findById(any());
  }

  @Test
  void getReportByIdNegative() {
    assertThatExceptionOfType(ElemNotFound.class).isThrownBy(() -> reportService.getReportById(2));
    verify(reportRepository, times(1)).findById(any());
  }

  @Test
  void getReportByPetId() {
    when(reportRepository.getReportByPet_id(anyLong())).thenReturn(report);
    when(reportMapper.toRecord(any(Report.class))).thenReturn(reportRecord);

    assertThat(reportService.getReportByPetId(1l)).isEqualTo(reportRecord);
    verify(reportRepository, times(1)).getReportByPet_id(any());
  }

  @Test
  void getReportByPetIdNegative() {
    assertThat(reportService.getReportByPetId(2)).isNull();
    verify(reportRepository, times(1)).getReportByPet_id(any());
  }

  @Test
  void getReportByChatIdAndIsReportCompletedFalse() {
    when(reportRepository.getReportByChatIdAndIsReportCompletedFalse(anyLong())).thenReturn(report);

    assertThat(reportService.getReportByChatIdAndIsReportCompletedFalse(999)).isEqualTo(reportRecord);
    verify(reportRepository, times(1)).getReportByChatIdAndIsReportCompletedFalse(any());
  }
  @Test
  void getReportByChatIdAndIsReportCompletedFalseNegative() {
    when(reportRepository.getReportByChatIdAndIsReportCompletedFalse(anyLong())).thenReturn(null);

    assertThat(reportService.getReportByChatIdAndIsReportCompletedFalse(999)).isNull();
    verify(reportRepository, times(1)).getReportByChatIdAndIsReportCompletedFalse(any());
  }

  @Test
  void addNewBlankReportWithChatId() {

    ReportRecord report1 = new ReportRecord();
    report1.setChatId(999l);
    report1.setIsReportCompleted(false);

    assertThat(reportService.addNewBlankReportWithChatId(999)).isEqualTo(report1);
    verify(reportRepository, times(1)).save(any());
  }
  @Test
  void removeAllBlankReportByChatId() {

//    when(reportRepository.save(any(Report.class))).thenReturn(report);
//    when(reportRepository.save(any(Report.class))).thenReturn(updateReport);
    List<Report> reports =new ArrayList<>();
    reports.add(report);
    reports.add(updateReport);
    when(reportRepository.findAll()).thenReturn(reports);
    when(reportRepository.findAll().size()).thenReturn(2);
//    assertThat(reportService.removeAllBlankReportByChatId(999l)))

var q = reportRepository.findAll();
    when(reportRepository.getAllByIsReportCompletedFalseAndChatId(anyLong())).thenReturn(null);
//    assertThat(reportService.removeAllBlankReportByChatId(999l)).
//    verify(reportRepository, times(1)).getAllByIsReportCompletedFalseAndChatId(any());

  }

  @Test
  void updateReport() {
  }

  @Test
  void showAllAdoptedPets() {
  }

  @Test
  void getPetById() {
  }

  AdoptiveParent initAdoptiveParent(long id) {
    AdoptiveParent adoptiveParent = new AdoptiveParent();
    adoptiveParent.setChatId(id);
    return adoptiveParent;
  }

  PetRecord initDogReport(AdoptiveParent adoptiveParent) {

    PetRecord petRecord = new PetRecord();
    petRecord.setId(1l);
    petRecord.setFullName("dog 1");
    petRecord.setAdoptiveParent(adoptiveParent);
    return petRecord;
  }
}