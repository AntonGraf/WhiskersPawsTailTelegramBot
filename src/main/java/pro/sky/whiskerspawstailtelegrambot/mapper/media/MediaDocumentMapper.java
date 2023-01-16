package pro.sky.whiskerspawstailtelegrambot.mapper.media;

import java.util.Collection;
import org.mapstruct.Mapper;
import pro.sky.whiskerspawstailtelegrambot.entity.media.MediaDocument;
import pro.sky.whiskerspawstailtelegrambot.record.media.MediaDocumentRecord;

@Mapper(componentModel = "spring")
public interface MediaDocumentMapper {

  MediaDocument toEntity(MediaDocumentRecord mediaDocumentRecord);

  MediaDocumentRecord toRecord(MediaDocument mediaDocument);

  Collection<MediaDocument> toEntityList(Collection<MediaDocumentRecord> mediaDocumentRecords);

  Collection<MediaDocumentRecord> toRecordList(Collection<MediaDocument> mediaDocuments);
}
