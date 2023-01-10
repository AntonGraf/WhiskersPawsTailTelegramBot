package pro.sky.whiskerspawstailtelegrambot.service;

import java.util.Collection;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.util.ParserToBot;
import pro.sky.whiskerspawstailtelegrambot.util.StateSendReport;

@Service
@Slf4j
@Transactional
public class ReportService {

  private final DogService dogService;
  private final ParserToBot parserToBot;

  private final AdoptiveParentService adoptiveParentService;

  public ReportService(DogService dogService, ParserToBot parserToBot,
      AdoptiveParentService adoptiveParentService) {
    this.dogService = dogService;
    this.parserToBot = parserToBot;
    this.adoptiveParentService = adoptiveParentService;
  }

  public String showAllAdoptedPets(Message message) {
    log.debug("Вызов метода showAllAdoptedPets");
    long chatId = message.getChatId();
    Collection<DogRecord> dogRecordsFilter = dogService.findAllDog()
        .stream()
        .filter(d -> d.getAdoptiveParent() != null)
        .filter(d -> d.getAdoptiveParent().getChatId() == chatId)
        .collect(Collectors.toList());
    String allAdoptedPets = parserToBot.parserDog(dogRecordsFilter);
    return allAdoptedPets == null ? AllText.YOU_HAVE_NO_ADOPTED_PETS_TEXT : allAdoptedPets;
  }

  public void changeStateAdoptiveParent(Message message) {
    long chatId = message.getChatId();
    long id = adoptiveParentService.getParentIdByNameAndPhoneAndChatId(null, null, chatId);
    AdoptiveParentRecord adoptiveParentRecord = adoptiveParentService.getAdoptiveParentByID(id);
    adoptiveParentRecord.setState(StateSendReport.WAIT_SEND_REPORT.getText());
    adoptiveParentService.updateAdoptiveParent(id, adoptiveParentRecord);

  }
}
