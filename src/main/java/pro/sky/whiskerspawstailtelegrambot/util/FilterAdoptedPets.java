package pro.sky.whiskerspawstailtelegrambot.util;

import java.util.Collection;
import java.util.stream.Collectors;
import pro.sky.whiskerspawstailtelegrambot.record.PetRecord;

public class FilterAdoptedPets {

  public Collection<PetRecord> byChatId(long chatId, Collection<PetRecord> collectionPets){

    collectionPets = collectionPets
        .stream()
        .filter(d -> d.getAdoptiveParent() != null)
        .filter(d -> d.getAdoptiveParent().getChatId() == chatId)
        .collect(Collectors.toList());

    return collectionPets;
  }



}
