package pro.sky.whiskerspawstailtelegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.mapper.AdoptiveParentMapper;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.StateRepository;
import pro.sky.whiskerspawstailtelegrambot.service.enums.StateAdoptiveParent;

@Service
@Slf4j
public class StateService {

  private final AdoptiveParentMapper adoptiveParentMapper;
  private final StateRepository stateChangeRepos;

  public StateService(AdoptiveParentMapper adoptiveParentMapper,
      StateRepository stateRepository) {
    this.adoptiveParentMapper = adoptiveParentMapper;
    this.stateChangeRepos = stateRepository;
  }

  /**
   * Получить id AdoptiveParent по chat id
   *
   * @param chatId chatId
   * @return AdoptiveParentRecord
   */
  public Long getAdoptiveParentIdByChatId(long chatId) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());
    AdoptiveParent adoptiveParent = stateChangeRepos.getAdoptiveParentByChatId(chatId);
    if(adoptiveParent != null){
      return stateChangeRepos.getAdoptiveParentByChatId(chatId).getChatId();
    }
    return null;
  }


  /**
   * Получить AdoptiveParent по chat id
   *
   * @param chatId chatId
   * @return AdoptiveParentRecord
   */
  public AdoptiveParentRecord getAdoptiveParentByChatId(long chatId) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());
    return adoptiveParentMapper.toRecord(stateChangeRepos.getAdoptiveParentByChatId(chatId));
  }


  /**
   * Получить state AdoptiveParent по chatId
   *
   * @param chatId string chatId
   * @return состояни пользователя
   */
  public StateAdoptiveParent getStateAdoptiveParentByChatId(long chatId) {
    log.info("Вызов метода " + new Throwable()
        .getStackTrace()[0]
        .getMethodName() + " класса " + this.getClass().getName());
    AdoptiveParentRecord adoptiveParentRecord = getAdoptiveParentByChatId(chatId);
    if (adoptiveParentRecord != null) {
      String state = adoptiveParentRecord.getState();
      return StateAdoptiveParent.valueOf(state);
    }
    return null;
  }

  /**
   * Обновить state пользователя по его chatId
   *
   * @param chatId long chatId
   * @param state  стейт на который нужно обновить
   * @return
   */
  public AdoptiveParentRecord updateStateAdoptiveParentByChatId(long chatId,
      StateAdoptiveParent state) {
    Long id = getAdoptiveParentIdByChatId(chatId);
    AdoptiveParent adoptiveParent = stateChangeRepos.getAdoptiveParentByChatId(chatId);
    if (adoptiveParent != null) {
      adoptiveParent.setState(state.name());
      stateChangeRepos.save(adoptiveParent);
      return adoptiveParentMapper.toRecord(adoptiveParent);
    }
    return null;
  }




}
