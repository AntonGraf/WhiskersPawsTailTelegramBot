package pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.service.MediaService;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;

/**
 * Создание ReportRecord, проверка и добавление вводимой информации в ReportRecord
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateReport {


  String value;
  CheckingReport checkingReport;
  final ReportService reportService;
  final GetBaseInfoFromUpdate baseInfo;
  private MediaService mediaService;

  /**
   * Создание ReportRecord, проверка и добавление вводимой информации в ReportRecord
   */
  public UpdateReport(ReportService reportService,
      GetBaseInfoFromUpdate baseInfo, MediaService mediaService) {
    this.reportService = reportService;
    this.baseInfo = baseInfo;
    this.mediaService = mediaService;
    init();
  }

  public void init() {
    checkingReport = new CheckingReport();
    value = baseInfo.getTextMessage();
  }

  public ReportRecord checkAndUpdateReportId(ReportRecord reportRecord)
      throws IllegalArgumentException {
    Long petId = checkingReport.checkCorrectPetId(value, reportService);
    reportRecord.setPet_id(petId);
    return reportRecord;

  }

  public ReportRecord checkAndUpdatePhotoOrDoc(ReportRecord reportRecord)
      throws IllegalArgumentException {
    checkingReport.checkPhotoOrDoc(baseInfo);
    byte[] bytes = new byte[0];
    if (baseInfo.getMessage().hasPhoto()) {
      bytes = mediaService.processPhoto(baseInfo).getMediaContent().getFileAsArrayOfBytes();
    }
    if (baseInfo.getMessage().hasDocument()) {
      bytes = mediaService.processDoc(baseInfo).getMediaContent().getFileAsArrayOfBytes();
    }
    reportRecord.setPhotoPet(bytes);
    return reportRecord;
  }

  public ReportRecord checkAndUpdateEnterDiet(ReportRecord reportRecord)
      throws IllegalArgumentException {
    checkingReport.checkText(value);
    reportRecord.setDiet(value);
    return reportRecord;
  }

  public ReportRecord checkAndUpdateEnterFeelings(ReportRecord reportRecord)
      throws IllegalArgumentException {
    checkingReport.checkText(value);
    reportRecord.setReportAboutFeelings(value);
    return reportRecord;
  }

  public ReportRecord checkAndUpdateEnterHabits(ReportRecord reportRecord)
      throws IllegalArgumentException {
    checkingReport.checkText(value);
    reportRecord.setReportAboutHabits(value);
    reportRecord.setIsReportCompleted(true);
    reportRecord.setIsReportCompleted(true);
    reportRecord.setDateTime(LocalDateTime.now());
    return reportRecord;

  }


}
