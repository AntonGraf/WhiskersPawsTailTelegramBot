package pro.sky.whiskerspawstailtelegrambot.service;

import java.util.Collection;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.util.ParserToBot;

@Service
@Slf4j
@Transactional
public class ReportService {

  private final DogService dogService;
  private final ParserToBot parserToBot;

  public ReportService(DogService dogService, ParserToBot parserToBot) {
    this.dogService = dogService;
    this.parserToBot = parserToBot;
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
  public String showAllAdoptedPets2(Message message) {
    log.debug("Вызов метода showAllAdoptedPets");
    MessageEntity messageEntity = new MessageEntity();
    long chatId = message.getChatId();
    Collection<DogRecord> dogRecordsFilter = dogService.findAllDog()
        .stream()
        .filter(d -> d.getAdoptiveParent() != null)
        .filter(d -> d.getAdoptiveParent().getChatId() == chatId)
        .collect(Collectors.toList());
    String allAdoptedPets = parserToBot.parserDog(dogRecordsFilter);
    return allAdoptedPets == null ? AllText.YOU_HAVE_NO_ADOPTED_PETS_TEXT : allAdoptedPets;
  }
}
