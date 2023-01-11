package pro.sky.whiskerspawstailtelegrambot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;
import pro.sky.whiskerspawstailtelegrambot.mapper.DogMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.DogMapperImpl;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.DogRepository;

import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DogServiceTest {

    @Mock
    DogRepository dogRepository;
    @InjectMocks
    DogService dogService;

    @Spy
    DogMapper dogMapper;


    @Mock
    Dog dog;


    @Mock
    DogRecord dogRecord;

    @Bean
    public DogMapper dogMapper() {
        return new DogMapperImpl();
    }






    @BeforeEach
    void setUp() throws IOException {
        dog = new Dog();
        dog.setId(1);
        dog.setFullName("testName");
        dog.setAge("1");
        dog.setDescription("testDog");
//        dogRecord = dogMapper.toRecord(dog);


    }

//    @AfterEach
//    void tearDown() {
////        dogService.removeDog();
//    }



    @Test
    void findDog() {
        DogRecord dogRecord1 = new DogRecord();
        when(dogMapper.toRecord(dog)).thenReturn(dogRecord1);

        assertThat(dog).isNotNull();
//        assertThat(dogRecord).isNotNull();

//        DogRecord dogRecord1 = new DogRecord();

//        DogRecord dogRecord1 = new DogRecord();
//        assertThat(dogRecord1 = dogMapper.toRecord(dog)).isNull();
//        DogRecord record = new DogRecord();

    }

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

//    public static Stream<Arguments> provideIDataForTest() {
//        return Stream.of(
//                Arguments.of(new DogRecord(1, "dogTest1", "5", "dogTest1Description", "/path", 0, "image/png", "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")),
//                Arguments.of()
//
//        );
//
//    }
}