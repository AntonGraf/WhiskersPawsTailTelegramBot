package pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;

/**
 * Создание ReportRecord,
 * проверка и добавление вводимой информации в ReportRecord
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateReport {
  final ReportRecord reportRecord;
  Message message;
  String value;
  CheckingReport checkingReport;
  final ReportService reportService;
  final GetBaseInfoFromUpdate baseInfo;
  /**
   * Создание ReportRecord,
   * проверка и добавление вводимой информации в ReportRecord
   */
  public UpdateReport(ReportRecord reportRecord, ReportService reportService,
      GetBaseInfoFromUpdate baseInfo) {
    this.reportRecord = reportRecord;
    this.reportService = reportService;
    this.baseInfo = baseInfo;
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
    byte[] photo = checkingReport.checkPhotoOrDoc(message);
    if (photo != null) {
      reportRecord.setPhotoPet(photo);
    }
    return reportRecord;
  }

  public ReportRecord checkAndUpdateEnterDiet() {
    boolean isCorrect = checkingReport.checkText(value);
    if (isCorrect) {
      reportRecord.setDiet(value);
    }
    return reportRecord;
  }

  public ReportRecord checkAndUpdateEnterFeelings() {
    boolean isCorrect = checkingReport.checkText(value);
    if (isCorrect) {
      reportRecord.setReportAboutFeelings(value);
    }
    return reportRecord;
  }

  public ReportRecord checkAndUpdateEnterHabits() {
    boolean isCorrect = checkingReport.checkText(value);
    if (isCorrect) {
      reportRecord.setReportAboutHabits(value);
    }
    return reportRecord;
  }


}
