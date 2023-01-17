package pro.sky.whiskerspawstailtelegrambot.util;

import java.util.Collection;
import java.util.stream.Collectors;
import pro.sky.whiskerspawstailtelegrambot.record.PetRecord;

public class FilterAdoptedPets {

  /**
   * ФПолучить список питомцев которые есть у пользователя по его chatId
   * @param chatId
   * @param collectionPets список всех питомцев в БД
   * @return
   */
  public Collection<PetRecord> byChatId(Long chatId, Collection<PetRecord> collectionPets){

    collectionPets = collectionPets
        .stream()
        .filter(d -> d.getAdoptiveParent() != null)
        .filter(d -> d.getAdoptiveParent().getChatId() == chatId)
        .collect(Collectors.toList());

    return collectionPets;
  }



}
