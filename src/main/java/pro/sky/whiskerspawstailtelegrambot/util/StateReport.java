package pro.sky.whiskerspawstailtelegrambot.util;

/**
 * Список состояний отчета
 */
public enum StateReport {

  NOT_STARTED("NOT_STARTED"),
  WAIT_ID_PET_REPORT("WAIT_ID_PET_REPORT"),
  WAIT_PHOTO_REPORT("WAIT_PHOTO_REPORT"),
  WAIT_DIET_REPORT("WAIT_DIET_REPORT"),
  WAIT_HEALTH_REPORT("WAIT_HEALTH_REPORT"),
  WAIT_HABITS_REPORT("WAIT_HABITS_REPORT"),
  WAIT_CLICK_SEND_REPORT("WAIT_CLICK_SEND_REPORT"),
  FINISHED("FINISHED");

  StateReport(String name) {
  }

  public String getText() {
    return name();
  }
}
