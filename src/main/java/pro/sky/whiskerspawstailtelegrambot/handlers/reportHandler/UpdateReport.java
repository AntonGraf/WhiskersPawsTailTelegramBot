package pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.handlers.mediaContentHandler.MediaHandler;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.service.MediaService;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;

/**
 * Создание ReportRecord, проверка и добавление вводимой информации в ReportRecord
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateReport {

  final ReportRecord reportRecord;
  Message message;
  String value;
  CheckingReport checkingReport;
  final ReportService reportService;
  final GetBaseInfoFromUpdate baseInfo;
  private MediaService mediaService;

  /**
   * Создание ReportRecord, проверка и добавление вводимой информации в ReportRecord
   */
  public UpdateReport(ReportRecord reportRecord, ReportService reportService,
      GetBaseInfoFromUpdate baseInfo, MediaService mediaService) {
    this.reportRecord = reportRecord;
    this.reportService = reportService;
    this.baseInfo = baseInfo;
    this.mediaService = mediaService;
    init();
  }

  public void init() {
    checkingReport = new CheckingReport();
    message = baseInfo.getMessage();
    value = baseInfo.getTextMessage();
  }

  public ReportRecord checkAndUpdateReportId() {
    Long petId = checkingReport.checkCorrectPetId(value, reportService);
    if (petId != null) {
      reportRecord.setPet_id(petId);
      return reportRecord;
    }
    return null;
  }

  public ReportRecord checkAndUpdatePhotoOrDoc() {
    boolean isCorrect = checkingReport.checkPhotoOrDoc(message);
    if (isCorrect) {
      byte[] bytes = new byte[0];
      if (message.hasPhoto()) {
        bytes = mediaService.processPhoto(baseInfo).getMediaContent().getFileAsArrayOfBytes();
      }
      if (message.hasDocument()) {
        bytes = mediaService.processDoc(baseInfo).getMediaContent().getFileAsArrayOfBytes();
      }
      reportRecord.setPhotoPet(bytes);
      return reportRecord;
    }
    return null;
  }

  public ReportRecord checkAndUpdateEnterDiet() {
    boolean isCorrect = checkingReport.checkText(value);
    if (isCorrect) {
      reportRecord.setDiet(value);
      return reportRecord;
    }
    return null;
  }

  public ReportRecord checkAndUpdateEnterFeelings() {
    boolean isCorrect = checkingReport.checkText(value);
    if (isCorrect) {
      reportRecord.setReportAboutFeelings(value);
      return reportRecord;
    }
    return null;
  }

  public ReportRecord checkAndUpdateEnterHabits() {
    boolean isCorrect = checkingReport.checkText(value);
    if (isCorrect) {
      reportRecord.setReportAboutHabits(value);
      reportRecord.setIsReportCompleted(true);
      return reportRecord;
    }
    return null;
  }


}
