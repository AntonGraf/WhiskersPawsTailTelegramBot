package pro.sky.whiskerspawstailtelegrambot.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import pro.sky.whiskerspawstailtelegrambot.entity.media.MediaDocument;
import pro.sky.whiskerspawstailtelegrambot.entity.media.MediaPhoto;
import pro.sky.whiskerspawstailtelegrambot.entity.media.MediaContent;
import pro.sky.whiskerspawstailtelegrambot.handlers.mainHandler.GetBaseInfoFromUpdate;
import pro.sky.whiskerspawstailtelegrambot.mapper.media.MediaDocumentMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.media.MediaPhotoMapper;
import pro.sky.whiskerspawstailtelegrambot.record.media.MediaDocumentRecord;
import pro.sky.whiskerspawstailtelegrambot.record.media.MediaPhotoRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.media.DocumentRepository;
import pro.sky.whiskerspawstailtelegrambot.repository.media.PhotoRepository;
import pro.sky.whiskerspawstailtelegrambot.repository.media.MediaContentRepository;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;

@Slf4j
@Service
public class MediaService {

  @Value("${telegram.bot.token}")
  private String token;
  @Value("${service.file_info.uri}")
  private String fileInfoUri;
  @Value("${service.file_storage.uri}")
  private String fileStorageUri;
  //  @Value("${link.address}")
//  private String linkAddress;
  private final PhotoRepository photoRepository;
  private final DocumentRepository documentRepository;
  private final MediaContentRepository mediaContentRepository;
  private FormReplyMessages formReplyMessages;
  private final MediaDocumentMapper mediaDocumentMapper;
  private final MediaPhotoMapper mediaPhotoMapper;
  ConfigKeyboard configKeyboard;

  public MediaService(PhotoRepository photoRepository,
      DocumentRepository documentRepository,
      MediaContentRepository mediaContentRepository, MediaDocumentMapper mediaDocumentMapper,
      MediaPhotoMapper mediaPhotoMapper) {
    this.photoRepository = photoRepository;
    this.documentRepository = documentRepository;
    this.mediaContentRepository = mediaContentRepository;
    this.mediaDocumentMapper = mediaDocumentMapper;
    this.mediaPhotoMapper = mediaPhotoMapper;
    formReplyMessages = new FormReplyMessages();
    configKeyboard = new ConfigKeyboard();
  }


  public SendMessage processDoc(Message message) {
    Document telegramDoc = message.getDocument();
    String fileId = telegramDoc.getFileId();
    ResponseEntity<String> response = getFilePath(fileId);
    if (response.getStatusCode() == HttpStatus.OK) {
      MediaContent persistentBinaryContent = getPersistentMediaContent(response);
      MediaDocument documentRecord = buildTransientDoc(telegramDoc, persistentBinaryContent);
      saveDocument(documentRecord);
      return formReplyMessages.replyMessage(message.getChatId().toString(), AllText.DOC_SUCCESSFUL_REPORT_TEXT,
          configKeyboard.initKeyboardOnClickStart());
    } else {
      log.error("Bad response from telegram service: " + response);
      return null;
    }
  }

  public MediaDocumentRecord processDoc(GetBaseInfoFromUpdate info) {
    org.telegram.telegrambots.meta.api.objects.Document telegramDoc = info.getMessage()
        .getDocument();
    String fileId = telegramDoc.getFileId();
    ResponseEntity<String> response = getFilePath(fileId);
    if (response.getStatusCode() == HttpStatus.OK) {
      MediaContent persistentBinaryContent = getPersistentMediaContent(response);
      MediaDocument mediaDocument = buildTransientDoc(telegramDoc, persistentBinaryContent);
      return mediaDocumentMapper.toRecord(saveDocument(mediaDocument));
    } else {
      log.error("Bad response from telegram service: " + response);
      return null;
    }
  }

  public MediaDocument saveDocument(MediaDocument mediaDocument) {
    return documentRepository.save(mediaDocument);
  }


  public SendMessage processPhoto(Message message) {
    var photoSizeCount = message.getPhoto().size();
    var photoIndex = photoSizeCount > 1 ? message.getPhoto().size() - 1 : 0;
    PhotoSize telegramPhoto = message.getPhoto().get(photoIndex);
    String fileId = telegramPhoto.getFileId();
    ResponseEntity<String> response = getFilePath(fileId);
    if (response.getStatusCode() == HttpStatus.OK) {
      MediaContent persistentBinaryContent = getPersistentMediaContent(response);
      MediaPhoto mediaPhoto = buildTransientPhoto(telegramPhoto, persistentBinaryContent);
      savePhoto(mediaPhoto);
      return formReplyMessages.replyMessage(message.getChatId().toString(), AllText.PHOTO_SUCCESSFUL_REPORT_TEXT,
          configKeyboard.initKeyboardOnClickStart());
    } else {
      log.error("Bad response from telegram service: " + response);
      return null;
    }
  }

  public MediaPhotoRecord processPhoto(GetBaseInfoFromUpdate info) {
    var photoSizeCount = info.getMessage().getPhoto().size();
    var photoIndex = photoSizeCount > 1 ? info.getMessage().getPhoto().size() - 1 : 0;
    PhotoSize telegramPhoto = info.getMessage().getPhoto().get(photoIndex);
    String fileId = telegramPhoto.getFileId();
    ResponseEntity<String> response = getFilePath(fileId);
    if (response.getStatusCode() == HttpStatus.OK) {
      MediaContent persistentBinaryContent = getPersistentMediaContent(response);
      MediaPhoto mediaPhoto = buildTransientPhoto(telegramPhoto, persistentBinaryContent);
      return mediaPhotoMapper.toRecord(savePhoto(mediaPhoto));
    } else {
      log.error("Bad response from telegram service: " + response);
      return null;
    }
  }

  public MediaPhoto savePhoto(MediaPhoto mediaPhoto) {
    return photoRepository.save(mediaPhoto);
  }

  private MediaContent getPersistentMediaContent(ResponseEntity<String> response) {
    String filePath = getFilePath(response);
    byte[] fileInByte = downloadFile(filePath);
    MediaContent transientBinaryContent = MediaContent.builder()
        .fileAsArrayOfBytes(fileInByte)
        .build();
    return mediaContentRepository.save(transientBinaryContent);
  }

  private String getFilePath(ResponseEntity<String> response) {
    JSONObject jsonObject = new JSONObject(response.getBody());
    return String.valueOf(jsonObject
        .getJSONObject("result")
        .getString("file_path"));
  }

  private MediaDocument buildTransientDoc(Document telegramDoc,
      MediaContent persistentBinaryContent) {
    return MediaDocument.builder()
        .telegramFileId(telegramDoc.getFileId())
        .docName(telegramDoc.getFileName())
        .mediaContent(persistentBinaryContent)
        .mimeType(telegramDoc.getMimeType())
        .fileSize(telegramDoc.getFileSize())
        .build();
  }

  private MediaPhoto buildTransientPhoto(PhotoSize telegramPhoto,
      MediaContent persistentBinaryContent) {
    return MediaPhoto.builder()
        .telegramFileId(telegramPhoto.getFileId())
        .mediaContent(persistentBinaryContent)
        .fileSize(telegramPhoto.getFileSize())
        .build();
  }

  private ResponseEntity<String> getFilePath(String fileId) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> request = new HttpEntity<>(headers);

    return restTemplate.exchange(
        fileInfoUri,
        HttpMethod.GET,
        request,
        String.class,
        token, fileId
    );
  }

  private byte[] downloadFile(String filePath) {
    String fullUri = fileStorageUri.replace("{telegram.bot.token}", token)
        .replace("{filePath}", filePath);
    URL urlObj = null;
    try {
      urlObj = new URL(fullUri);
    } catch (MalformedURLException e) {
      log.error("Error download");
      return null;
    }

    //TODO подумать над оптимизацией
    try (InputStream is = urlObj.openStream()) {
      return is.readAllBytes();
    } catch (IOException e) {
      log.error("Error download");
      return null;
    }
  }

//    public String generateLink(Long docId, LinkType linkType) {
//        var hash = cryptoTool.hashOf(docId);
//        return "http://" + linkAddress + "/" + linkType + "?id=" + hash;
//    }
}
