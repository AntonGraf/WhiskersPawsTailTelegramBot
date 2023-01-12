package pro.sky.whiskerspawstailtelegrambot.mapper;

import org.mapstruct.Mapper;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;

import java.util.Collection;

/**
 * маппер для {@link AdoptiveParent}
 * готовый рекорд {@link pro.sky.whiskerspawstailtelegrambot.mapper.DogMapper}
 */
@Mapper(componentModel = "spring")
public interface DogMapper {
    Dog toEntity(DogRecord dogRecord);

    DogRecord toRecord(Dog dog);

    Collection<Dog> toEntityList(Collection<DogRecord> dogRecords);

    Collection<DogRecord> toRecordList(Collection<Dog> dogs);
}
