package pro.sky.whiskerspawstailtelegrambot.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;
import pro.sky.whiskerspawstailtelegrambot.mapper.DogMapper;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.DogRepository;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;


import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;


/**
 * Сервис слой для собак
 */
@Service
@Slf4j
@Transactional
public class DogService {


    @Value("${dog.photo.dir.path}")
    private String dogDir;

    DogRepository dogRepository;
    DogMapper dogMapper;


    public DogService(DogRepository dogRepository, DogMapper dogMapper) {
        this.dogRepository = dogRepository;
        this.dogMapper = dogMapper;
    }


    /**
     * Получение собаки bp БД по id
     * @param dogId
     * @return возвращает собаку
     */
    public DogRecord findDog(long dogId) { //Get
        log.info("Поиск собаки в БД" + dogId);
        Dog dog = dogRepository.findById(dogId).orElseThrow(ElemNotFound::new);
        return dogMapper.toRecord(dog);
    }

    /**
     * Коллекция всех собак в БД
     * @return список всех собак хранящиеся в БД
     */
    public Collection<DogRecord> findAllDog() { //Get
        log.info("Поиск всех собак в БД");
        Collection<DogRecord> dog = dogMapper.toRecordList(dogRepository.findAll());
        return dog;
    }

    /**
     * Удаление сбаки из БД по id
     * @param dogId
     */
    public DogRecord removeDog (long dogId) { //Delete
        log.info("Поиск собаки в БД");
        DogRecord dogRecord = findDog(dogId);
        dogRepository.deleteById(dogId);
        return dogRecord;
    }

    /**
     * Изменение собаки в БД
     * @param dogRecord
     * @return измененую собаку
     */
    public DogRecord editDog(Long gogId, DogRecord dogRecord) { //Put
        log.info("Изменение данных собаки в БД");
        Dog dog = dogMapper.toEntity(dogRecord);
        return dogMapper.toRecord(dogRepository.save(dog));
    }

    /**
     * загрузка фотографии собаки в БД
     * @param dogId
     * @param file
     * @throws IOException
     */
    public void uploadPhoto(long dogId, MultipartFile file) throws IOException { //Put фото
        DogRecord dogRecord = findDog(dogId);
        Path filePath = Path.of(dogDir, dogId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Dog dog = dogMapper.toEntity(dogRecord);
        dog.setFilePath(filePath.toString());
        dog.setFileSize(file.getSize());
        dog.setMediaType(file.getContentType());
        dog.setPhoto(file.getBytes());
        dogRepository.save(dog);
    }


    /**
     * вспомогательный медот для загрузки фотографий
     * @param fileName
     * @return расширение файла
     */
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    /**
     * Добавление собаки в БД
     * @param dogRecord
     * @return новая собака
     */
    public DogRecord addDog(DogRecord dogRecord) { //Post
        log.info("Добавление собаки в БД");
        Dog dog = dogMapper.toEntity(dogRecord);
        return dogMapper.toRecord(dogRepository.save(dog));
    }




}
