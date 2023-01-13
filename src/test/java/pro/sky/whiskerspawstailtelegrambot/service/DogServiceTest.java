package pro.sky.whiskerspawstailtelegrambot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;
import pro.sky.whiskerspawstailtelegrambot.entity.Shelter;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.mapper.DogMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.DogMapperImpl;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.DogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class DogServiceTest {

    @Mock
    DogRepository dogRepository;
    @Mock
    DogMapper dogMapper;
    @InjectMocks
    DogService out;
    DogMapper mapper = new DogMapperImpl();

    @Test
    void findDogPositiveTest() {
        Dog dog = getTestDog();
        lenient().when(dogRepository.findById(1L)).thenReturn(Optional.of(dog));
        lenient().when(dogMapper.toRecord(dog)).thenReturn(mapper.toRecord(dog));
        DogRecord exceptedRecord = mapper.toRecord(dog);
        DogRecord actual = out.findDog(1L);
        assertEquals(actual, exceptedRecord);
    }

    @Test
    void findDogNegativeTest() {
        lenient().when(dogRepository.findById(anyLong())).thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.findDog(1L));
    }

    @Test
    void findAllDogEmptyListTest() {
        lenient().when(dogRepository.findAll()).thenReturn(List.of());
        assertTrue(out.findAllDog().isEmpty());
    }
    @Test
    void findAllDogPositiveTest() {
        List<Dog> dogs = getTestDogList();
        List<DogRecord> dogRecords = getTestDogRecords();

        lenient().when(dogRepository.findAll()).thenReturn(dogs);
        lenient().when(dogMapper.toRecordList(dogs)).thenReturn(mapper.toRecordList(dogs));

        List<DogRecord> checkedDogRecords = List.copyOf(out.findAllDog());

        assertEquals(checkedDogRecords.size(), dogRecords.size());
        assertTrue(out.findAllDog().containsAll(dogRecords));
    }

    @Test
    void removeDogPositiveTest() {
        Dog dog = getTestDog();

        lenient().when(dogRepository.findById(anyLong())).thenReturn(Optional.of(dog));
        lenient().when(dogMapper.toRecord(dog)).thenReturn(mapper.toRecord(dog));
        lenient().doNothing().when(dogRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> out.removeDog(1L));

    }

    @Test
    void removeDogNegativeTest() {

        lenient().when(dogRepository.findById(anyLong())).thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.removeDog(1L));

    }

    @Test
    void editDogPositiveTest() {
        Dog dog = getTestDog();
        DogRecord dogRecord = mapper.toRecord(dog);

        lenient().when(dogRepository.findById(anyLong())).thenReturn(Optional.of(dog));
        lenient().when(dogMapper.toRecord(dog)).thenReturn(mapper.toRecord(dog));
        lenient().when(dogMapper.toEntity(dogRecord)).thenReturn(mapper.toEntity(dogRecord));
        lenient().when(dogRepository.save(any(Dog.class))).thenReturn(dog);

        assertDoesNotThrow(() -> out.editDog(1L, "", 1, "", getTestPhoto()));
    }

    @Test
    void editDogNegativeTest() {
        lenient().when(dogRepository.findById(anyLong())).thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.editDog(1L, "", 1, "", getTestPhoto()));
    }

    @Test
    void uploadPhotoNegativeTest() {
        lenient().when(dogRepository.findById(anyLong())).thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.uploadPhoto(1L, getTestPhoto()));
    }

    private Dog getTestDog() {
        Dog dog = new Dog();
        dog.setId(1L);
        dog.setFullName("Шарик Бобикович");
        dog.setAge(3);
        dog.setDescription("двортерьер");
        dog.setFileSize(1024);
        dog.setShelter(getTestShelter());
        dog.setPhoto(null);
        dog.setMediaType("");
        dog.setReports(List.of());
        dog.setAdoptiveParent(null);
        return dog;
    }

    private Shelter getTestShelter() {
        Shelter shelter = new Shelter();
        shelter.setId(1L);
        return shelter;
    }

    private List<Dog> getTestDogList() {
        List<Dog> dogs = new ArrayList<>();

        Dog dog = new Dog();
        dog.setId(2L);
        dog.setFullName("Бобик");
        dog.setAge(5);
        dog.setDescription("двортерьер");
        dog.setFileSize(1024);
        dog.setShelter(getTestShelter());
        dog.setPhoto(null);
        dog.setMediaType("");
        dog.setReports(List.of());
        dog.setAdoptiveParent(null);

        dogs.add(dog);
        dogs.add(getTestDog());
        return dogs;
    }

    private List<DogRecord> getTestDogRecords() {
        List<DogRecord> dogRecords = new ArrayList<>();
        for (Dog dog : getTestDogList()) {
            dogRecords.add(mapper.toRecord(dog));
        }
        return dogRecords;
    }

    private MultipartFile getTestPhoto() {
        byte[] photoByte = {123,123,0,6};
        return new MockMultipartFile("dog.png",photoByte);
    }

}