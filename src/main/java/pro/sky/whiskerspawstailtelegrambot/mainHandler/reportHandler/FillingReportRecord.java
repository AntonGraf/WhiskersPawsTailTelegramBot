package pro.sky.whiskerspawstailtelegrambot.mainHandler.reportHandler;

import static pro.sky.whiskerspawstailtelegrambot.util.StateReport.WAIT_ID_PET_REPORT;

import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.service.ReportService;
import pro.sky.whiskerspawstailtelegrambot.util.ParserToBot;


public class FillingReportRecord {

  /**
   * Метод создает отчет и меняет  StateReport на WAIT_ID_PET_REPORT
   * @return новый ReportRecord
   */
  public ReportRecord newReport(){

    ReportRecord reportRecord = new ReportRecord();
    reportRecord.setStateReport(WAIT_ID_PET_REPORT);
    return reportRecord;

  }

  public DogRecord checkCorrectDogId(String textMessage, ReportService reportService){

    ParserToBot parserToBot = new ParserToBot();
    Long dogId = parserToBot.parserStringPetId(textMessage);

    DogRecord dogRecord = dogId != null ? reportService.getDogById(dogId) : null;
   return dogRecord;

  }



//  public ReportRecord saveReportInDb(Message message, ReportRecord reportRecord) {
//
//    long chatId = message.getChatId();
//    reportService.addReport();
//
//
//
//  }



}
