package pro.sky.whiskerspawstailtelegrambot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import pro.sky.whiskerspawstailtelegrambot.controller.DogController;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;
import pro.sky.whiskerspawstailtelegrambot.mapper.DogMapper;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.DogRepository;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
class DogServiceTest {

    private final long TESTID = 1;
    @Mock
    DogRepository dogRepository;
    @InjectMocks
    DogService dogService;

    @Spy
    DogMapper dogMapper;

    @Mock
    static
    Dog dog;



    @BeforeEach
    void setUp() throws IOException {
//        DogRecord dogRecordTest = dogService.findDog(TESTID);
//        dog = new Dog();
//        dog.setFullName("testName");
//        dog.setAge(1);
//        dog.setDescription("testDog");
//        dog.setFileSize(1024);
    }

//    @AfterEach
//    void tearDown() {
////        dogService.removeDog(TESTID);
//    }

//    public void contextLoads() {
//        assertNotNull(dogController);
//        assertThat(dogController).isNotNull();
//    }


//    @Test
//    void findDog() {
//        DogRecord dogRecord = dogMapper.toRecord(dog);
//        Assertions.assertNotNull(dog);
////        Assertions.assertNotNull(dogRecord);
//        Assertions.assertEquals(dog.getFullName(), dogRecord.getFullName());
//
////        Assertions.assertInstanceOf(dogRecord, DogRecord.class);
//        Assertions.assertNotNull(dogRecord);
////        Assertions.assertEquals(dog, dogRecord);
//    }

    @Test
    void findAllDog() {
    }

    @Test
    void removeDog() {
    }

    @Test
    void editDog() {
    }

    @Test
    void addIdAdoptiveParent() {
    }

    @Test
    void uploadPhoto() {
    }

    @Test
    void addDog() {
    }
//
//    public static Stream<Arguments> provideIDataForTest() {
//        return Stream.of(
//                Dog dog = new Dog();
//        );


}