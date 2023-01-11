package pro.sky.whiskerspawstailtelegrambot.service;

import java.util.Collection;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import pro.sky.whiskerspawstailtelegrambot.mapper.ReportMapperImpl;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.util.ParserToBot;
import pro.sky.whiskerspawstailtelegrambot.util.StateAdoptiveParent;

@Service
@Slf4j
@Transactional
public class ReportService {

  private final DogService dogService;
  private final ParserToBot parserToBot;
  private final FormReplyMessages formReplyMessages;

  private final AdoptiveParentService adoptiveParentService;
  private ConfigKeyboard configKeyboard;

  public ReportService(DogService dogService, ParserToBot parserToBot,
      FormReplyMessages formReplyMessages, AdoptiveParentService adoptiveParentService,
      ConfigKeyboard configKeyboard) {
    this.dogService = dogService;
    this.parserToBot = parserToBot;
    this.formReplyMessages = formReplyMessages;
    this.adoptiveParentService = adoptiveParentService;
    this.configKeyboard = configKeyboard;
  }

  /**
   * Показать всех животных принадлежащих пользователю
   *
   * @param message Message из Update
   * @return Список животных в  текстовом формате для отправки пользователю.
   */
  public String showAllAdoptedPets(Message message) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());
    long chatId = message.getChatId();
    Collection<DogRecord> dogRecordsFilter = dogService.findAllDog()
        .stream()
        .filter(d -> d.getAdoptiveParent() != null)
        .filter(d -> d.getAdoptiveParent().getChatId() == chatId)
        .collect(Collectors.toList());
    String allAdoptedPets = parserToBot.parserDog(dogRecordsFilter);
    return allAdoptedPets == null ? AllText.YOU_HAVE_NO_ADOPTED_PETS_TEXT : allAdoptedPets;
  }

  /**
   * Изменениние состояния пользователя
   *
   * @param message Message из Update
   * @param state   Список состояний пользвателя
   * @return true если получилось изменить состояние пользователя
   */
  public SendMessage changeStateAdoptiveParent(Message message, String textReplyMessage,
      StateAdoptiveParent state) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());
    long chatId = message.getChatId();

    AdoptiveParentRecord adoptiveParentRecord = adoptiveParentService.getAdoptiveParentByChatId(
        chatId);

    if (adoptiveParentRecord != null) {
      adoptiveParentRecord.setState(state.getText());
      adoptiveParentService.updateAdoptiveParent(adoptiveParentRecord.getId(),
          adoptiveParentRecord);
      return formReplyMessages.replyMessage(message,
          textReplyMessage,
          configKeyboard.initKeyboardOnClickStart());
    }
    return formReplyMessages.replyMessage(message,
        AllText.ERROR_REPLY_TEXT,
        configKeyboard.initKeyboardOnClickStart());
  }

  public ReportMapperImpl addReport() {

    return null;
  }

  public StateAdoptiveParent getStateAdoptiveParentByChatId(long chatId) {
    return adoptiveParentService.getStateAdoptiveParentByChatId(chatId);
  }

}
