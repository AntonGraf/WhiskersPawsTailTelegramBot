package pro.sky.whiskerspawstailtelegrambot.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;
import pro.sky.whiskerspawstailtelegrambot.repository.DogRepository;

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


    @Value(value = "photo_dog")
    private String dogDir;

    DogRepository dogRepository;


    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }


    public Dog findDog(long dogId) { //Get
        log.info("Поиск собаки в БД");
        Dog dog = dogRepository.findById(dogId).orElseThrow(); //(DogNotFoundException::new)
        log.info("Собака найдена в БД");
        return dog;
    }
    public Collection<Dog> findAllDog() { //Get
        log.info("Поиск всех собак в БД");
        Collection<Dog> dog = dogRepository.findAll();
        log.info("Собака найдена в БД");
        return dog;
    }

    public void removeDog (long dogId) { //Delete
        log.info("Поиск собаки в БД");
        Dog dog = findDog(dogId);
        if (dog.getId() > 0) {
            dogRepository.deleteById(dogId);
        }
        log.info("Собака удалена из БД");
    }

    public Dog editDog(Dog dog) { //Put
        log.info("Изменение данных собаки в БД");
        dogRepository.save(dog);
        log.info("Данные в БД изменены");
        return dog;
    }

    public void uploadPhoto(long dogId, MultipartFile file) throws IOException { //Put фото
        Dog dog = findDog(dogId);
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

        dog.setFilePath(filePath.toString());
        dog.setFileSize(file.getSize());
        dog.setMediaType(file.getContentType());
        dog.setPhoto(file.getBytes());
        dogRepository.save(dog);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    public Dog addDog(Dog dog) { //Post
        log.info("Изменение данных собаки в БД");
        dogRepository.save(dog);
        log.info("Данные в БД изменены");
        return dog;
    }




}
