//package pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers;
//
//import static pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent.FINISHED;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent.NOT_STARTED;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent.ONLY_NAME;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent.SUCCESS_REG;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent.THE_FIRST_STATE;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent.WAIT_DIET_REPORT;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent.WAIT_FEELINGS_REPORT;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent.WAIT_HABITS_REPORT;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent.WAIT_ID_PET_REPORT;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent.WAIT_PHOTO_REPORT;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateReportEnum.FINISHED;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateReportEnum.NOT_STARTED;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateReportEnum.WAIT_DIET_REPORT;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateReportEnum.WAIT_FEELINGS_REPORT;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateReportEnum.WAIT_HABITS_REPORT;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateReportEnum.WAIT_ID_PET_REPORT;
//import static pro.sky.whiskerspawstailtelegrambot.util.StateReportEnum.WAIT_PHOTO_REPORT;
//
//import liquibase.pro.packaged.E;
//import liquibase.pro.packaged.T;
//import org.springframework.stereotype.Component;
//import pro.sky.whiskerspawstailtelegrambot.handlers.RegistrationHandler;
//import pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent;
//import pro.sky.whiskerspawstailtelegrambot.util.StateReportEnum;
//
//@Component
//public class StateEnumeration {
//
//  private final RegistrationHandler registrationHandler;
//
//  public StateEnumeration(RegistrationHandler registrationHandler) {
//    this.registrationHandler = registrationHandler;
//  }
//
//  public StateAdoptiveParent selectStateAdoptiveParentRegistration(
//      StateAdoptiveParent state) {
//
//    switch (state) {
//
//      case THE_FIRST_STATE:
//        return THE_FIRST_STATE;
//
//      case ONLY_NAME:
//        return ONLY_NAME;
//
//      case SUCCESS_REG:
//        return SUCCESS_REG;
//
//    }
//
//    switch (state) {
//
//      case NOT_STARTED:
//        return NOT_STARTED;
//
//      case WAIT_ID_PET_REPORT:
//        return WAIT_ID_PET_REPORT;
//
//      case WAIT_PHOTO_REPORT:
//        return WAIT_PHOTO_REPORT;
//
//      case WAIT_DIET_REPORT:
//        return WAIT_DIET_REPORT;
//
//      case WAIT_FEELINGS_REPORT:
//        return WAIT_FEELINGS_REPORT;
//
//      case WAIT_HABITS_REPORT:
//        return WAIT_HABITS_REPORT;
//
//      case FINISHED:
//        return FINISHED;
//
//    }
//    return state;
//  }
//
//  public StateReportEnum selectStateAdoptiveParentReportFilling(
//      StateReportEnum stateReportEnum) {
//
//    switch (stateReportEnum) {
//
//      case NOT_STARTED:
//        return NOT_STARTED;
//
//      case WAIT_ID_PET_REPORT:
//        return WAIT_ID_PET_REPORT;
//
//      case WAIT_PHOTO_REPORT:
//        return WAIT_PHOTO_REPORT;
//
//      case WAIT_DIET_REPORT:
//        return WAIT_DIET_REPORT;
//
//      case WAIT_FEELINGS_REPORT:
//        return WAIT_FEELINGS_REPORT;
//
//      case WAIT_HABITS_REPORT:
//        return WAIT_HABITS_REPORT;
//
//      case FINISHED:
//        return FINISHED;
//
//    }
//    return stateReportEnum;
//  }
//
//
//}