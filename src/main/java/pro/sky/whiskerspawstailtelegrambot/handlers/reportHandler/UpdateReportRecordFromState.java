package pro.sky.whiskerspawstailtelegrambot.handlers.reportHandler;

import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers.GetPetIdFromString;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;

public class UpdateReportRecordFromState {



  public ReportRecord reportRecord;
  private Message message;
  String value;
  long petId;
  byte[] photo;
  public UpdateReportRecordFromState(ReportRecord reportRecord, Message message) {
    this.reportRecord = reportRecord;
    this.message = message;
    init();
  }

  public void init(){

    value = message.getText();
    GetPetIdFromString getPetIdFromString = new GetPetIdFromString();
    long petId = getPetIdFromString.get(value);
    photo = null;

  }


  public ReportRecord updateStartReport1() {
    return null;
  }

  public ReportRecord updateEnterIdPetWaitPhotoReport2() {
    reportRecord.setPet_id(petId);
    return reportRecord;
  }

  public ReportRecord updateEnterPhotoWaitDietReport3() {
    reportRecord.setPhotoPet(photo);
    return reportRecord;
  }

  public ReportRecord updateEnterDietWaitFeelingsReport4() {
    reportRecord.setDiet(value);
    return reportRecord;
  }

  public ReportRecord updateEnterFeelingsWaitHabitsReport5() {
    reportRecord.setReportAboutFeelings(value);
    return reportRecord;
  }

  public ReportRecord updateEnterHabitsFinishedReportSetStateFree_6() {
    reportRecord.setReportAboutHabits(value);
    return reportRecord;
  }


}
