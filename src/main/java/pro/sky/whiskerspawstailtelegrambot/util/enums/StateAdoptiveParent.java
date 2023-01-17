package pro.sky.whiskerspawstailtelegrambot.util.enums;

import java.util.ArrayList;
import java.util.List;

public enum StateAdoptiveParent {

  FREE("FREE"),
  NULL("NULL"),

  THE_FIRST_STATE("только начал"),
  ONLY_NAME("ввел только имя"),
  SUCCESS_REG("зареган"),


  CANCEL_CREATE_REPORT("CANCEL_CREATE_REPORT"),
  START_0("START_0"),
  CHECK_ID_WAIT_PHOTO_REPORT_1("CHECK_ID_WAIT_PHOTO_REPORT_1"),
  CHECK_PHOTO_WAIT_DIET_REPORT_2("CHECK_PHOTO_WAIT_DIET_REPORT_2"),
  CHECK_DIET_WAIT_FEELINGS_REPORT_3("CHECK_DIET_WAIT_FEELINGS_REPORT_3"),
  CHECK_FEELINGS_WAIT_HABITS_REPORT_4("CHECK_FEELINGS_WAIT_HABITS_REPORT_4"),
  CHECK_HABITS_WAIT_FINISHED_REPORT_5("CHECK_HABITS_WAIT_FINISHED_REPORT_5"),
  WAIT_CLICK_SEND_REPORT("WAIT_CLICK_SEND_REPORT"),
  FINISHED_REPORT_6("FINISHED_REPORT_6");


  public static List<String> getRegistrationList(List<String> stateRegistrationList) {
    stateRegistrationList = new ArrayList<>();
    stateRegistrationList.add(NULL.name());
    stateRegistrationList.add(THE_FIRST_STATE.name());
    stateRegistrationList.add(ONLY_NAME.name());
    stateRegistrationList.add(SUCCESS_REG.name());
    return stateRegistrationList;
  }


  public static List<String> getReportList(List<String> stateReportList) {
    stateReportList = new ArrayList<>();
    stateReportList.add(CANCEL_CREATE_REPORT.name());
    stateReportList.add(START_0.name());
    stateReportList.add(CHECK_ID_WAIT_PHOTO_REPORT_1.name());
    stateReportList.add(CHECK_PHOTO_WAIT_DIET_REPORT_2.name());
    stateReportList.add(CHECK_DIET_WAIT_FEELINGS_REPORT_3.name());
    stateReportList.add(CHECK_FEELINGS_WAIT_HABITS_REPORT_4.name());
    stateReportList.add(CHECK_HABITS_WAIT_FINISHED_REPORT_5.name());
    stateReportList.add(FINISHED_REPORT_6.name());
    return stateReportList;
  }

  StateAdoptiveParent(String name) {

  }

}
