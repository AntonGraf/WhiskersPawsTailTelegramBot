package pro.sky.whiskerspawstailtelegrambot.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.whiskerspawstailtelegrambot.entity.Report;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.mapper.ReportMapper;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.ReportRepository;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

  @Mock
  ReportRepository reportRepository;
  @Mock
  ReportMapper reportMapper;
  @InjectMocks
  ReportService reportService;

  ReportRecord reportRecord;
  ReportRecord newReportRecord;
  Report report;
  Report newReport;

  @BeforeEach
  void setUp() throws IOException {

    reportRecord = new ReportRecord();
    newReportRecord = new ReportRecord();
    report = new Report();
    newReport = new Report();

    byte[] data;
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
      BufferedImage bImage = ImageIO.read(new File("./src/test/img/photoForTest.png"));
      ImageIO.write(bImage, "png", bos);
      data = bos.toByteArray();
    }
    reportRecord.setId(1);
    reportRecord.setDog_id(1);
//    reportRecord.setPhotoDog(data);
    reportRecord.setDiet("test diet");
    reportRecord.setReportAboutFeelings("test AboutFeelings");
    reportRecord.setReportAboutHabits("test AboutHabits");
    reportRecord.setStateReport("FINISHED");

    newReportRecord.setId(2);
    newReportRecord.setDog_id(2);
//    newReportRecord.setPhotoDog(data);
    newReportRecord.setDiet("test new diet");
    newReportRecord.setReportAboutFeelings("test new AboutFeelings");
    newReportRecord.setReportAboutHabits("test new AboutHabits");
    newReportRecord.setStateReport("NOT_STARTED");

    report.setId(1);
    report.setDog_id(1);
//    report.setPhotoDog(data);
    report.setDiet("test diet");
    report.setReportAboutFeelings("test AboutFeelings");
    report.setReportAboutHabits("test AboutHabits");
    report.setStateReport("FINISHED");

    newReport.setId(2);
    newReport.setDog_id(2);
//    newReport.setPhotoDog(data);
    newReportRecord.setDiet("test new diet");
    newReportRecord.setReportAboutFeelings("test new AboutFeelings");
    newReportRecord.setReportAboutHabits("test new AboutHabits");
    newReportRecord.setStateReport("NOT_STARTED");


  }



  @Test
  void getReportById() {
    when(reportRepository.findById(anyLong())).thenReturn(Optional.of(report));
    when(reportMapper.toRecord(any(Report.class))).thenReturn(reportRecord);
    assertThat(reportService.getReportById(1l)).isEqualTo(reportRecord);
  }

  @Test
  void getReportByIdNegative() {
    assertThatExceptionOfType(ElemNotFound.class).isThrownBy(() -> reportService.getReportById(2));

  }

  @Test
  void getReportByPetId() {
    when(reportRepository.getReportByDog_id(anyLong())).thenReturn(report);
    when(reportMapper.toRecord(any(Report.class))).thenReturn(reportRecord);
    assertThat(reportService.getReportByPetId(1l)).isEqualTo(reportRecord);
  }

  @Test
  void getReportByPetIdNegative() {
    assertThat(reportService.getReportByPetId(2)).isNull();
  }

  @Test
  void addNewReportInDbForPetByPetId() {
    when(reportRepository.getReportByDog_id(anyLong())).thenReturn(report);
    when(reportMapper.toRecord(any(Report.class))).thenReturn(reportRecord);
    assertThat(reportService.getReportByPetId(1l)).isEqualTo(reportRecord);
  }
  @Test
  void addNewReportInDbForPetByPetIdNegative() {
    assertThat(reportService.getReportByPetId(2)).isNull();
  }

  @Test
  void updateReportByReportId() {
    when(reportRepository.save(any(Report.class))).thenReturn(report);
    when(reportMapper.toRecord(report)).thenReturn(reportRecord);


  }
  @Test
  void updateReportByReportIdNegative() {
  }
  @Test
  void showAllAdoptedPets() {
  }

  @Test
  void changeStateAdoptiveParent() {
  }

  @Test
  void getStateAdoptiveParentByChatId() {
  }

  @Test
  void getStateReportByPetId() {
  }
}