package pro.sky.whiskerspawstailtelegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFoundChecked;
import pro.sky.whiskerspawstailtelegrambot.loger.FormLogInfo;
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
   * Получить AdoptiveParent по chat id
   *
   * @param chatId chatId
   * @return AdoptiveParentRecord
   */
  public AdoptiveParentRecord getAdoptiveParentByChatId(Long chatId) throws ElemNotFoundChecked {
    log.info(FormLogInfo.getInfo());
    AdoptiveParent adoptiveParent = stateChangeRepos.getAdoptiveParentByChatId(chatId)
        .orElseThrow(() -> new ElemNotFoundChecked("Пользователь по такому id не найден"));

    return adoptiveParentMapper.toRecord(adoptiveParent);
  }


  /**
   * получить состояние пользователя по chatId
   *
   * @param chatId string chatId
   * @return состояни пользователя
   */
  public StateAdoptiveParent getStateAdoptiveParentByChatId(Long chatId) {
    log.info(FormLogInfo.getInfo());
    AdoptiveParentRecord adoptiveParentRecord = null;

    try {
      adoptiveParentRecord = getAdoptiveParentByChatId(chatId);
      String state = adoptiveParentRecord.getState();
      return StateAdoptiveParent.valueOf(state);

    } catch (ElemNotFoundChecked e) {
      log.info(FormLogInfo.getCatch());
      return StateAdoptiveParent.NULL;
    }
  }

  /**
   * Обновить state пользователя по его chatId
   *
   * @param chatId long chatId
   * @param state  стейт на который нужно обновить
   * @return
   */
  public StateAdoptiveParent updateStateAdoptiveParentByChatId(Long chatId,
      StateAdoptiveParent state) {
    try {
      AdoptiveParent adoptiveParent = stateChangeRepos.getAdoptiveParentByChatId(chatId)
          .orElseThrow(() -> new ElemNotFoundChecked("Пользователь по такому id не найден"));
      adoptiveParent.setState(state.name());
      stateChangeRepos.save(adoptiveParent);
      adoptiveParentMapper.toRecord(adoptiveParent);
      return state;
    } catch (ElemNotFoundChecked e) {
      return StateAdoptiveParent.NULL;
    }

  }


}
