package pro.sky.whiskerspawstailtelegrambot.util;

public enum StateSendReport {

  FREE,
  REGISTRATION,
  WAIT_SEND_REPORT,
  NO_SEND_REPORT,
  NULL;

public String getText(){
  return name();
}

//public StateSendReport getStateSendReport(String state){
//  return StateSendReport.valueOf(state);
//}

}
