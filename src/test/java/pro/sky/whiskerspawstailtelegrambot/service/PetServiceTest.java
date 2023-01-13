package pro.sky.whiskerspawstailtelegrambot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.whiskerspawstailtelegrambot.entity.Pet;
import pro.sky.whiskerspawstailtelegrambot.entity.Shelter;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.mapper.PetMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.PetMapperImpl;
import pro.sky.whiskerspawstailtelegrambot.record.PetRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pro.sky.whiskerspawstailtelegrambot.repository.PetRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    PetRepository petRepository;
    @Mock
    PetMapper petMapper;
    @InjectMocks
    PetService out;
    PetMapper mapper = new PetMapperImpl();

    @Test
    void findDogPositiveTest() {
        Pet pet = getTestPet();
        lenient().when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        lenient().when(petMapper.toRecord(pet)).thenReturn(mapper.toRecord(pet));
        PetRecord exceptedRecord = mapper.toRecord(pet);
        PetRecord actual = out.findPet(1L);
        assertEquals(actual, exceptedRecord);
    }

    @Test
    void findDogNegativeTest() {
        lenient().when(petRepository.findById(anyLong())).thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.findPet(1L));
    }

    @Test
    void findAllDogEmptyListTest() {
        lenient().when(petRepository.findAll()).thenReturn(List.of());
        assertTrue(out.findAllPet().isEmpty());
    }
    @Test
    void findAllDogPositiveTest() {
        List<Pet> dogs = getTestPetList();
        List<PetRecord> dogRecords = getTestPetRecords();

        lenient().when(petRepository.findAll()).thenReturn(dogs);
        lenient().when(petMapper.toRecordList(dogs)).thenReturn(mapper.toRecordList(dogs));

        List<PetRecord> checkedDogRecords = List.copyOf(out.findAllPet());

        assertEquals(checkedDogRecords.size(), dogRecords.size());
        assertTrue(out.findAllPet().containsAll(dogRecords));
    }

    @Test
    void removeDogPositiveTest() {
        Pet pet = getTestPet();

        lenient().when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet));
        lenient().when(petMapper.toRecord(pet)).thenReturn(mapper.toRecord(pet));
        lenient().doNothing().when(petRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> out.removePet(1L));

    }

    @Test
    void removeDogNegativeTest() {

        lenient().when(petRepository.findById(anyLong())).thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.removePet(1L));

    }

    @Test
    void editDogPositiveTest() {
        Pet dog = getTestPet();
        PetRecord petRecord = mapper.toRecord(dog);

        lenient().when(petRepository.findById(anyLong())).thenReturn(Optional.of(dog));
        lenient().when(petMapper.toRecord(dog)).thenReturn(mapper.toRecord(dog));
        lenient().when(petMapper.toEntity(petRecord)).thenReturn(mapper.toEntity(petRecord));
        lenient().when(petRepository.save(any(Pet.class))).thenReturn(dog);

        assertDoesNotThrow(() -> out.editPet(1L, "", "1", "", getTestPhoto()));
    }

    @Test
    void editDogNegativeTest() {
        lenient().when(petRepository.findById(anyLong())).thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.editPet(1L, "", "1", "", getTestPhoto()));
    }

    @Test
    void uploadPhotoNegativeTest() {
        lenient().when(petRepository.findById(anyLong())).thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.uploadPhoto(1L, getTestPhoto()));
    }

    private Pet getTestPet() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setFullName("Шарик Бобикович");
        pet.setAge("3");
        pet.setDescription("двортерьер");
        pet.setFileSize(1024);
        pet.setShelter(getTestShelter());
        pet.setPhoto(null);
        pet.setMediaType("");
        pet.setReports(List.of());
        pet.setAdoptiveParent(null);
        return pet;
    }

    private Shelter getTestShelter() {
        Shelter shelter = new Shelter();
        shelter.setId(1L);
        return shelter;
    }

    private List<Pet> getTestPetList() {
        List<Pet> dogs = new ArrayList<>();

        Pet pet = new Pet();
        pet.setId(2L);
        pet.setFullName("Бобик");
        pet.setAge("5");
        pet.setDescription("двортерьер");
        pet.setFileSize(1024);
        pet.setShelter(getTestShelter());
        pet.setPhoto(null);
        pet.setMediaType("");
        pet.setReports(List.of());
        pet.setAdoptiveParent(null);

        dogs.add(pet);
        dogs.add(getTestPet());
        return dogs;
    }

    private List<PetRecord> getTestPetRecords() {
        List<PetRecord> dogRecords = new ArrayList<>();
        for (Pet pet : getTestPetList()) {
            dogRecords.add(mapper.toRecord(pet));
        }
        return dogRecords;
    }

    private MultipartFile getTestPhoto() {
        byte[] photoByte = {123,123,0,6};
        return new MockMultipartFile("dog.png",photoByte);
    }


}