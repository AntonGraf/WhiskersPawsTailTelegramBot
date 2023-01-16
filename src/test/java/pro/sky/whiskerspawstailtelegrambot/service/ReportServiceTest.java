package pro.sky.whiskerspawstailtelegrambot.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
import pro.sky.whiskerspawstailtelegrambot.entity.Pet;
import pro.sky.whiskerspawstailtelegrambot.entity.Report;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFoundChecked;
import pro.sky.whiskerspawstailtelegrambot.mapper.ReportMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.ReportMapperImpl;
import pro.sky.whiskerspawstailtelegrambot.record.PetRecord;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.PetRepository;
import pro.sky.whiskerspawstailtelegrambot.repository.ReportRepository;
import pro.sky.whiskerspawstailtelegrambot.util.ParserToBot;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {


  @Mock
  ReportRepository reportRepository;
  @Spy
  ReportMapper reportMapper = new ReportMapperImpl();

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
  void getReportById() throws ElemNotFoundChecked {
    when(reportRepository.findById(anyLong())).thenReturn(Optional.of(report));
    when(reportMapper.toRecord(any(Report.class))).thenReturn(reportRecord);

    assertThat(reportService.getReportById(1l)).isEqualTo(reportRecord);
    verify(reportRepository, times(1)).findById(any());
  }

  @Test
  void getReportByIdNegative() {
    assertThatExceptionOfType(ElemNotFoundChecked.class).isThrownBy(() -> reportService.getReportById(2l));
    verify(reportRepository, times(1)).findById(any());
  }

  @Test
  void getReportByChatIdAndIsReportCompletedFalse() throws Exception {
    when(reportRepository.getByChatIdAndIsReportCompletedFalse(anyLong())).thenReturn(
        Optional.ofNullable(report));

    assertThat(reportService.getReportByChatIdAndIsReportCompletedFalse(999L)).isEqualTo(
        reportRecord);
    verify(reportRepository, times(1)).getByChatIdAndIsReportCompletedFalse(any());
  }

  @Test
  void getReportByChatIdAndIsReportCompletedFalseNegative() {
    when(reportRepository.getByChatIdAndIsReportCompletedFalse(anyLong())).thenReturn(null);

    assertThatExceptionOfType(Exception.class).isThrownBy(
        () -> reportService.getReportByChatIdAndIsReportCompletedFalse(999L));
    verify(reportRepository, times(1)).getByChatIdAndIsReportCompletedFalse(any());
  }

  @Test
  void addNewBlankReportWithChatId() {

    ReportRecord report1 = new ReportRecord();
    report1.setChatId(999l);
    report1.setIsReportCompleted(false);

    assertThat(reportService.addNewBlankReportWithChatId(999L)).isEqualTo(report1);
    verify(reportRepository, times(1)).save(any());
  }

  @Test
  void deleteAllByChatIdAndIsReportCompletedFalse() {

    List<Report> reports = new ArrayList<>();
    reports.add(report);
    reports.add(updateReport);

    when(reportRepository.getAllByChatIdAndIsReportCompletedFalse(anyLong())).thenReturn(reports);
    reportService.deleteAllByChatIdAndIsReportCompletedFalse(999l);
;
    verify(reportRepository, times(1)).deleteAll(any());
  }

  @Test
  void deleteAllByChatIdAndIsReportCompletedFalseNegative() {
    List<Report> reports = new ArrayList<>();
    reports.add(report);
    reports.add(updateReport);

    when(reportRepository.getAllByChatIdAndIsReportCompletedFalse(anyLong())).thenReturn(reports);
    reportService.deleteAllByChatIdAndIsReportCompletedFalse(999l);
    ;
    verify(reportRepository, times(1)).deleteAll(any());
  }

  @Test
  void updateReport() throws ElemNotFoundChecked {
    when(reportRepository.findById(anyLong())).thenReturn(Optional.ofNullable(report));
    when(reportService.getReportById(anyLong())).thenReturn(reportRecord);

    assertThat(reportService.updateReport(updateReportRecord)).isEqualTo(updateReportRecord);
    verify(reportRepository, times(2)).findById(any());
    verify(reportRepository, times(1)).save(any());

  }

  @Test
  void updateReportNegative()  {
    assertThatExceptionOfType(ElemNotFoundChecked.class).isThrownBy(
        () -> reportService.getReportById(null));
    assertThatExceptionOfType(RuntimeException.class).isThrownBy(
        () -> reportService.updateReport(updateReportRecord));
    verify(reportRepository, times(0)).save(any());
  }

  @Test
  void showAllAdoptedPets() throws Exception {
    Pet pet1 = initDog(initAdoptiveParent(111L), 1L);
    Pet pet2 = initDog(initAdoptiveParent(111L), 2L);
    Collection<Pet> pets = new ArrayList<>();
    pets.add(pet1);
    pets.add(pet2);

    PetRecord petRecord = initPetRecord(initAdoptiveParent(111L), 1L);
    PetRecord petRecord2 = initPetRecord(initAdoptiveParent(111L), 2L);
    Collection<PetRecord> petRecords = new ArrayList<>();
    petRecords.add(petRecord);
    petRecords.add(petRecord2);

    when(petService.getAllPetAdoptiveParentByChatId(anyLong())).thenReturn(petRecords);

    ParserToBot parserToBot = new ParserToBot();
    String allAdoptedPets = parserToBot.parserPet(petRecords);

    assertThat(reportService.showAllAdoptedPetsByChatId(111L)).isEqualTo(allAdoptedPets);
    verify(petService, times(1)).getAllPetAdoptiveParentByChatId(anyLong());
  }

  @Test
  void showAllAdoptedPetsNegative() throws Exception {
    when(petService.getAllPetAdoptiveParentByChatId(anyLong())).thenReturn(null);

    assertThatExceptionOfType(Exception.class).isThrownBy(
        () -> reportService.showAllAdoptedPetsByChatId(999L));
    verify(petService, times(1)).getAllPetAdoptiveParentByChatId(anyLong());
  }

  @Test
  void getPetById() {
    PetRecord petRecord = initPetRecord(initAdoptiveParent(111L), 1L);
    when(petService.findPet(anyLong())).thenReturn(petRecord);

    assertThat(reportService.getPetByPetId(anyLong())).isEqualTo(petRecord);
    verify(petService, times(1)).findPet(anyLong());
  }

  @Test
  void getPetByIdNegative() {
    when(petService.findPet(anyLong())).thenReturn(null);
    assertThat(reportService.getPetByPetId(anyLong())).isNull();
    verify(petService, times(1)).findPet(anyLong());
  }

  AdoptiveParent initAdoptiveParent(Long id) {
    AdoptiveParent adoptiveParent = new AdoptiveParent();
    adoptiveParent.setChatId(id);
    return adoptiveParent;
  }

  PetRecord initPetRecord(AdoptiveParent adoptiveParent, Long idPet) {

    PetRecord petRecord = new PetRecord();
    petRecord.setId(idPet);
    petRecord.setFullName("dog 1");
    petRecord.setAdoptiveParent(adoptiveParent);
    return petRecord;
  }
  Pet initDog(AdoptiveParent adoptiveParent, Long idPet) {

    Pet pet = new Pet();
    pet.setId(idPet);
    pet.setFullName("dog 1");
    pet.setAdoptiveParent(adoptiveParent);
    return pet;
  }

}