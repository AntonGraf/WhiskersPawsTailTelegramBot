package pro.sky.whiskerspawstailtelegrambot.util;

import java.util.Collection;
import java.util.stream.Collectors;
import liquibase.pro.packaged.T;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;

public class FilterAdoptedPets {

  public Collection<DogRecord> byChatId(long chatId, Collection<DogRecord> collectionPets){

    collectionPets = collectionPets
        .stream()
        .filter(d -> d.getAdoptiveParent() != null)
        .filter(d -> d.getAdoptiveParent().getChatId() == chatId)
        .collect(Collectors.toList());

    return collectionPets;
  }



}
