package pro.sky.whiskerspawstailtelegrambot.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.control.MappingControl.Use;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.entity.media.MediaContent;
import pro.sky.whiskerspawstailtelegrambot.entity.media.MediaDocument;
import pro.sky.whiskerspawstailtelegrambot.entity.media.MediaPhoto;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFoundChecked;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.mapper.PetMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.PetMapperImpl;
import pro.sky.whiskerspawstailtelegrambot.mapper.media.MediaDocumentMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.media.MediaDocumentMapperImpl;
import pro.sky.whiskerspawstailtelegrambot.mapper.media.MediaPhotoMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.media.MediaPhotoMapperImpl;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.media.DocumentRepository;
import pro.sky.whiskerspawstailtelegrambot.repository.media.MediaContentRepository;
import pro.sky.whiskerspawstailtelegrambot.repository.media.PhotoRepository;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;

@ExtendWith(MockitoExtension.class)
class MediaServiceTest {


  @Mock
  PhotoRepository photoRepository;
  @Mock
  DocumentRepository documentRepository;
  @Mock
  MediaContentRepository mediaContentRepository;
  MediaDocument mediaDocument;
  MediaPhoto mediaPhoto;
  MediaContent mediaContent;
  @InjectMocks
  MediaService mediaService;

  @BeforeEach
  void setUp() throws IOException {
    mediaDocument = new MediaDocument();
    mediaDocument.setId(1L);
    mediaDocument.setDocName("dad");
    mediaDocument.setMimeType("png");
    mediaDocument.setFileSize(100);
    mediaDocument.setTelegramFileId("100");

    mediaPhoto = new MediaPhoto();
    mediaPhoto.setId(1L);
    mediaPhoto.setFileSize(100);
    mediaPhoto.setTelegramFileId("100");

    mediaContent = new MediaContent();
    mediaContent.setId(1L);

  }
  @Test
  void saveDocument() {
    when(documentRepository.save(mediaDocument)).thenReturn(mediaDocument);

    assertThat(mediaService.saveDocument(mediaDocument)).isEqualTo(mediaDocument);
    verify(documentRepository, times(1)).save(any());
  }

  @Test
  void saveDocumentNegative() {
    when(documentRepository.save(mediaDocument)).thenReturn(null);

    assertThat(mediaService.saveDocument(mediaDocument)).isEqualTo(null);
    verify(documentRepository, times(1)).save(any());
  }

  @Test
  void savePhoto() {
    when(photoRepository.save(mediaPhoto)).thenReturn(mediaPhoto);

    assertThat(mediaService.savePhoto(mediaPhoto)).isEqualTo(mediaPhoto);
    verify(photoRepository, times(1)).save(any());
  }

  @Test
  void savePhotoNegative() {
    when(photoRepository.save(mediaPhoto)).thenReturn(null);

    assertThat(mediaService.savePhoto(mediaPhoto)).isEqualTo(null);
    verify(photoRepository, times(1)).save(any());
  }
  @Test
  void saveMediaContent() {
    when(mediaContentRepository.save(mediaContent)).thenReturn(mediaContent);

    assertThat(mediaService.saveMediaContent(mediaContent)).isEqualTo(mediaContent);
    verify(mediaContentRepository, times(1)).save(any());
  }

  @Test
  void saveMediaContentNegative() {
    when(mediaContentRepository.save(mediaContent)).thenReturn(null);

    assertThat(mediaService.saveMediaContent(mediaContent)).isEqualTo(null);
    verify(mediaContentRepository, times(1)).save(any());
  }

}