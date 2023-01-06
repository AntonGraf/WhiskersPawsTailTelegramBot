package pro.sky.whiskerspawstailtelegrambot.controller;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;
import pro.sky.whiskerspawstailtelegrambot.service.DogService;

import java.io.IOException;
import java.util.Collection;

@RestController
@Getter
@Setter
@Slf4j
@RequestMapping("/dogs")
public class DogController {

    private final DogService dogService;


    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Dog> findDog(@PathVariable Long id) {
        return ResponseEntity.ok(dogService.findDog(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Dog>> findAllDog() {
        return ResponseEntity.ok(dogService.findAllDog());
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Dog> addDog (
            @RequestParam String fullName,
            @RequestParam int age,
            @RequestParam String description,
            @RequestParam MultipartFile photo) throws IOException {
        Dog dog = new Dog();
        dog.setFullName(fullName);
        dog.setAge(age);
        dog.setDescription(description);
        dog.setPhoto(photo.getBytes());
        dogService.editDog(dog);
        dogService.uploadPhoto(dog.getId(), photo);
        return ResponseEntity.ok(dog);
    }


    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Dog> editDog (
                                   @RequestParam Long id,
                                   @RequestParam String fullName,
                                   @RequestParam int age,
                                   @RequestParam String description,
                                   @RequestParam MultipartFile photo) throws IOException {
        Dog dog = new Dog();
        dog.setFullName(fullName);
        dog.setAge(age);
        dog.setDescription(description);
        dog.setPhoto(photo.getBytes());
        dogService.editDog(dog);
        dogService.uploadPhoto(id, photo);
        return ResponseEntity.ok(dog);
    }

    @PutMapping(value = "/{dogId}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Dog> uploadAvatar (@RequestParam MultipartFile photo, @PathVariable Long dogId) throws IOException {
        dogService.uploadPhoto(dogId, photo);
        return ResponseEntity.ok().build();
    }



    @DeleteMapping("{id}")
    public ResponseEntity<Dog>  removeDog (@PathVariable Long id) {
        dogService.removeDog(id);
        return ResponseEntity.ok().build();
    }




}
