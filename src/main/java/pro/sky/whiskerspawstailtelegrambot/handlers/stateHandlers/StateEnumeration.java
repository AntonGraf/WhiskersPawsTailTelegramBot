package pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers;

import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.FINISHED_REPORT_7;
import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.NOT_STARTED;
import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.ONLY_NAME;
import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.SUCCESS_REG;
import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.THE_FIRST_STATE;
import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.ENTER_DIET_WAIT_FEELINGS_REPORT_4;
import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.ENTER_FEELINGS_WAIT_HABITS_REPORT_5;
import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.ENTER_HABITS_WAIT_FINISHED_REPORT_6;
import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.ENTER_ID_PET_WAIT_PHOTO_REPORT_2;
import static pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent.ENTER_PHOTO_WAIT_DIET_REPORT_3;


import org.springframework.stereotype.Component;
import pro.sky.whiskerspawstailtelegrambot.handlers.RegistrationHandler;
import pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent.StateAdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.util.StateReportEnum;

@Component
public class StateEnumeration {

  private final RegistrationHandler registrationHandler;

  public StateEnumeration(RegistrationHandler registrationHandler) {
    this.registrationHandler = registrationHandler;
  }

  public StateAdoptiveParent selectStateAdoptiveParentRegistration(
      StateAdoptiveParent state) {

    switch (state) {

      case THE_FIRST_STATE:
        return THE_FIRST_STATE;

      case ONLY_NAME:
        return ONLY_NAME;

      case SUCCESS_REG:
        return SUCCESS_REG;

    }

    switch (state) {

      case NOT_STARTED:
        return NOT_STARTED;

      case ENTER_ID_PET_WAIT_PHOTO_REPORT_2:
        return ENTER_ID_PET_WAIT_PHOTO_REPORT_2;

      case ENTER_PHOTO_WAIT_DIET_REPORT_3:
        return ENTER_PHOTO_WAIT_DIET_REPORT_3;

      case ENTER_DIET_WAIT_FEELINGS_REPORT_4:
        return ENTER_DIET_WAIT_FEELINGS_REPORT_4;

      case ENTER_FEELINGS_WAIT_HABITS_REPORT_5:
        return ENTER_FEELINGS_WAIT_HABITS_REPORT_5;

      case ENTER_HABITS_WAIT_FINISHED_REPORT_6:
        return ENTER_HABITS_WAIT_FINISHED_REPORT_6;

      case FINISHED_REPORT_7:
        return FINISHED_REPORT_7;

    }
    return state;
  }

  public StateReportEnum selectStateAdoptiveParentReportFilling(
      StateReportEnum stateReportEnum) {

    switch (stateReportEnum) {

      case NOT_STARTED:
        return NOT_STARTED;

      case ENTER_ID_PET_WAIT_PHOTO_REPORT_2:
        return ENTER_ID_PET_WAIT_PHOTO_REPORT_2;

      case ENTER_PHOTO_WAIT_DIET_REPORT_3:
        return ENTER_PHOTO_WAIT_DIET_REPORT_3;

      case ENTER_DIET_WAIT_FEELINGS_REPORT_4:
        return ENTER_DIET_WAIT_FEELINGS_REPORT_4;

      case ENTER_FEELINGS_WAIT_HABITS_REPORT_5:
        return ENTER_FEELINGS_WAIT_HABITS_REPORT_5;

      case ENTER_HABITS_WAIT_FINISHED_REPORT_6:
        return ENTER_HABITS_WAIT_FINISHED_REPORT_6;

      case FINISHED_REPORT_7:
        return FINISHED_REPORT_7;

    }
    return stateReportEnum;
  }


}