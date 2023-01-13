package pro.sky.whiskerspawstailtelegrambot.service;


import static java.nio.file.StandardOpenOption.CREATE_NEW;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.mapper.DogMapper;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.DogRepository;


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
     * Получение собаки в БД по id
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
        return dogMapper.toRecordList(dogRepository.findAll());
    }

    /**
     * Удаление сбаки из БД по id
     *
     * @param dogId
     */
    public void removeDog (long dogId) { //Delete
        log.info("Поиск собаки в БД");
        DogRecord dogRecord = findDog(dogId);
        dogRepository.deleteById(dogRecord.getId());
    }

    /**
     * Изменение собаки в БД
     * @param dogId
     * @param fullName
     * @param age
     * @param description
     * @param photo
     * @throws IOException
     */
    public void editDog(Long dogId, String fullName, int age, String description, MultipartFile photo) throws IOException { //Put
        log.info("Изменение данных собаки в БД");
        DogRecord dogRecord = findDog(dogId);
        Dog dog = dogMapper.toEntity(dogRecord);
        dog.setFullName(fullName);
        dog.setAge(age);
        dog.setDescription(description);
        uploadPhoto(dogId, photo);
        dogRepository.save(dog);
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
     * @return расширение файла
     */
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * Добавление новой собаки в БД
     *
     * @param fullName
     * @param age
     * @param description
     * @param photo
     * @throws IOException
     */
    public void addDog(String fullName, int age, String description, MultipartFile photo) throws IOException { //Post
        log.info("Добавление собаки в БД");
        Dog dog = new Dog();
        dog.setFullName(fullName);
        dog.setAge(age);
        dog.setDescription(description);
        DogRecord dogRecord = dogMapper.toRecord(dogRepository.save(dog));
        uploadPhoto(dogRecord.getId(), photo);
    }





}
