package pro.sky.whiskerspawstailtelegrambot.mapper.media;

import java.util.Collection;
import org.mapstruct.Mapper;
import pro.sky.whiskerspawstailtelegrambot.entity.media.MediaPhoto;
import pro.sky.whiskerspawstailtelegrambot.record.media.MediaPhotoRecord;

@Mapper(componentModel = "spring")
public interface MediaPhotoMapper {


  MediaPhoto toEntity(MediaPhotoRecord mediaPhotoRecord);

  MediaPhotoRecord toRecord(MediaPhoto mediaPhoto);

  Collection<MediaPhoto> toEntityList(Collection<MediaPhotoRecord> mediaPhotoRecords);

  Collection<MediaPhotoRecord> toRecordList(Collection<MediaPhoto> mediaPhotoEntities);



}
