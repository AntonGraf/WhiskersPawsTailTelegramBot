package pro.sky.whiskerspawstailtelegrambot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.entity.Pet;
import pro.sky.whiskerspawstailtelegrambot.entity.Shelter;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.mapper.PetMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.PetMapperImpl;
import pro.sky.whiskerspawstailtelegrambot.record.PetRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.AdoptiveParentRepo;
import pro.sky.whiskerspawstailtelegrambot.repository.PetRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class TestPeriodServiceTest {

    @Mock
    PetRepository petRepository;
    @Mock
    AdoptiveParentRepo adoptiveParentRepo;
    @Mock
    PetMapper petMapper;
    @InjectMocks
    TestPeriodService out;
    PetMapper mapper = new PetMapperImpl();

    @Test
    public void getPetsDoesntHaveParentTest() {
        Pet pet = getTestPet();

        lenient().when(petRepository.findPetByAdoptiveParentIsNull()).thenReturn(List.of(pet));
        lenient().when(petMapper.toRecordList(anyCollection())).thenReturn(mapper.toRecordList(List.of(pet)));

        Collection<PetRecord> actual = out.getPetsDoesntHaveParent();
        Collection<PetRecord> excepted = mapper.toRecordList(List.of(pet));
        assertEquals(actual.size(), 1);
        assertTrue(actual.containsAll(excepted));
    }

    @Test
    public void getPetsDoesntHaveParentEmptyTest() {

        lenient().when(petRepository.findPetByAdoptiveParentIsNull()).thenReturn(List.of());
        lenient().when(petMapper.toRecordList(anyCollection())).thenReturn(List.of());

        Collection<PetRecord> actual = out.getPetsDoesntHaveParent();
        assertTrue(actual.isEmpty());
    }

    @Test
    public void addAdoptiveParentPositiveTest() {
        Pet pet = getTestPet();
        AdoptiveParent adoptiveParent = getTestAdoptiveParent();

        assertNull(pet.getAdoptiveParent());
        assertNull(pet.getTestPeriodTime());
        assertFalse(pet.isTestPeriod());
        assertNull(adoptiveParent.getPets());

        lenient().when(petRepository.findPetByIdAndAdoptiveParentIsNull(anyLong())).thenReturn(Optional.of(pet));
        lenient().when(adoptiveParentRepo.findById(anyLong())).thenReturn(Optional.of(adoptiveParent));

        Pet exceptedPet = getTestPet();
        exceptedPet.setAdoptiveParent(adoptiveParent);
        exceptedPet.setTestPeriodTime(LocalDateTime.now().plusDays(30));
        exceptedPet.setTestPeriod(true);

        lenient().when(petRepository.save(any(Pet.class))).thenReturn(exceptedPet);
        lenient().when(petMapper.toRecord(any(Pet.class))).thenReturn(mapper.toRecord(exceptedPet));
        PetRecord actual = out.addAdoptiveParent(pet.getId(), adoptiveParent.getId(), 30);

        assertEquals(mapper.toRecord(exceptedPet), actual);
    }

    @Test
    public void returnPetToShelterPositiveTest() {
        Pet pet = getTestPet();

        lenient().when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet));

        Pet exceptedPet = getTestPet();
        exceptedPet.setTestPeriod(false);
        exceptedPet.setTestPeriodTime(null);
        exceptedPet.setAdoptiveParent(null);
        lenient().when(petRepository.save(any(Pet.class))).thenReturn(exceptedPet);
        lenient().when(petMapper.toRecord(any(Pet.class))).thenReturn(mapper.toRecord(exceptedPet));

        assertEquals(mapper.toRecord(exceptedPet), out.returnPetToShelter(pet.getId()));
    }

    @Test
    public void returnPetToShelterNegativeTest() {

        lenient().when(petRepository.findById(anyLong())).thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.returnPetToShelter(1L));
    }

    @Test
    public void extendTestPeriodPositiveTest() {
        Pet pet = getTestPet();
        LocalDateTime oldTestPeriodDateTime = LocalDateTime.of(2023, 2, 16, 14, 30);
        pet.setTestPeriod(true);
        pet.setTestPeriodTime(oldTestPeriodDateTime);

        lenient().when(petRepository.findPetByAdoptiveParentIsNotNullAndIdAndTestPeriod(1L, true))
                .thenReturn(Optional.of(pet));

        Pet exceptedPet = getTestPet();
        exceptedPet.setTestPeriodTime(oldTestPeriodDateTime.plusDays(30));
        exceptedPet.setTestPeriod(true);

        lenient().when(petRepository.save(exceptedPet)).thenReturn(exceptedPet);
        lenient().when(petMapper.toRecord(exceptedPet)).thenReturn(mapper.toRecord(exceptedPet));

        PetRecord actual = out.extendTestPeriod(1L, 30);
        assertEquals(actual, mapper.toRecord(exceptedPet));

    }

    @Test
    public void extendTestPeriodNegativeTest() {
        lenient().when(petRepository.findPetByAdoptiveParentIsNotNullAndIdAndTestPeriod(1L, true))
                .thenThrow(ElemNotFound.class);

        assertThrows(ElemNotFound.class, () -> out.extendTestPeriod(1L, 30));

    }

    @Test
    public void completeTestPeriodPositiveTest() {
        Pet pet = getTestPet();
        AdoptiveParent adoptiveParent = getTestAdoptiveParent();
        pet.setTestPeriod(true);
        pet.setTestPeriodTime(LocalDateTime.of(2023, 2, 16, 14, 30));
        pet.setAdoptiveParent(adoptiveParent);

        lenient().when(petRepository.findPetByAdoptiveParentIsNotNullAndIdAndTestPeriod(1L, true))
                .thenReturn(Optional.of(pet));

        Pet exceptedPet = getTestPet();
        exceptedPet.setAdoptiveParent(adoptiveParent);
        lenient().when(petRepository.save(exceptedPet)).thenReturn(exceptedPet);
        lenient().when(petMapper.toRecord(exceptedPet)).thenReturn(mapper.toRecord(exceptedPet));
        assertEquals(mapper.toRecord(exceptedPet), out.completeTestPeriod(1L));
    }

    @Test
    public void completeTestPeriodNegativeTest() {
        lenient().when(petRepository.findPetByAdoptiveParentIsNotNullAndIdAndTestPeriod(1L, true))
                .thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.completeTestPeriod(1L));
    }

    @Test
    public void getPetsHaveTestPeriodPositiveTest() {
        Pet pet = getTestPet();
        pet.setTestPeriod(true);
        pet.setTestPeriodTime(LocalDateTime.of(2023, 2, 16, 14, 30));
        AdoptiveParent adoptiveParent = getTestAdoptiveParent();
        pet.setAdoptiveParent(adoptiveParent);
        List<Pet> pets = getTestPetList();
        pets.add(pet);
        lenient().when(petRepository.findPetByTestPeriod(true)).thenReturn(List.of(pet));
        lenient().when(petMapper.toRecordList(List.of(pet))).thenReturn(mapper.toRecordList(List.of(pet)));

        assertTrue(out.getPetsHaveTestPeriod().containsAll(mapper.toRecordList(List.of(pet))));
    }

    @Test
    public void getPetsHaveTestPeriodNegativeTest() {
        lenient().when(petRepository.findPetByTestPeriod(true)).thenReturn(List.of());
        assertTrue(out.getPetsHaveTestPeriod().isEmpty());
    }

    @Test
    public void getPetsWithEndedTestPeriodPositiveTest() {
        Pet pet = getTestPet();
        pet.setTestPeriod(true);
        pet.setTestPeriodTime(LocalDateTime.of(2023, 1, 17, 14, 30));
        AdoptiveParent adoptiveParent = getTestAdoptiveParent();
        pet.setAdoptiveParent(adoptiveParent);
        List<Pet> pets = getTestPetList();
        pets.add(pet);

        lenient().when(petRepository.findPetsByTestPeriodAndTestPeriodTimeBefore(anyBoolean(),
                any(LocalDateTime.class))).thenReturn(List.of(pet));
        lenient().when(petMapper.toRecordList(List.of(pet))).thenReturn(mapper.toRecordList(List.of(pet)));

        List<PetRecord> actual = List.copyOf(out.getPetsWithEndedTestPeriod(2));
        List<PetRecord> excepted = List.copyOf(mapper.toRecordList(List.of(pet)));
        assertTrue(actual.containsAll(excepted));
    }

    @Test
    public void getPetsWithEndedTestPeriodNegativeTest() {
        lenient().when(petRepository.findPetsByTestPeriodAndTestPeriodTimeBefore(anyBoolean(),
                any(LocalDateTime.class))).thenReturn(List.of());
        assertTrue(out.getPetsWithEndedTestPeriod(2).isEmpty());
    }

    /**
     * пет для теста
     *
     * @return - тестовый питомец
     */
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

    /**
     * коллекция петов
     *
     * @return - список тестовых питомцев
     */
    private List<Pet> getTestPetList() {
        List<Pet> pets = new ArrayList<>();

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
        pet.setAdoptiveParent(getTestAdoptiveParent());

        pets.add(pet);
        pets.add(getTestPet());
        return pets;
    }

    /**
     * шелтер для теста
     *
     * @return - тестовый приют
     */
    private static Shelter getTestShelter() {
        Shelter shelter = new Shelter();
        shelter.setId(1L);
        return shelter;
    }

    /**
     * усыновитель для теста
     *
     * @return - тестовый усыновитель
     */
    private AdoptiveParent getTestAdoptiveParent() {
        AdoptiveParent parent = new AdoptiveParent();
        parent.setId(1L);
        parent.setFullName("Иванов Иван Иванович");
        return parent;
    }
}