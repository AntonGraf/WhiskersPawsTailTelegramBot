package pro.sky.whiskerspawstailtelegrambot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.whiskerspawstailtelegrambot.entity.Volunteer;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.mapper.VolunteerMapper;
import pro.sky.whiskerspawstailtelegrambot.record.VolunteerRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.VolunteerRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {

    //Мок репозитория с волонтерами
    @Mock
    private VolunteerRepo volunteerRepo;
    //Мок маппера волонтеров Entity/Record
    @Mock
    private VolunteerMapper volunteerMapper;
    //Тестируемый сервис волонтеров
    @InjectMocks
    private VolunteerService out;

    /**
     * Параметры для проверки добавления в репозиторий
     * @return  - аргументы для с различными VolunteerRecord для теста
     */
    public static Stream<Arguments> provideAddParamsPositiveTest() {
        return Stream.of(
                Arguments.of(new VolunteerRecord(1, "Ивченко Валентин Генадьевич", "89146667454",
                        "Дорогой посетитель, наши волонтеры с удовольствием окажут вам помощь, вам будет " +
                                "предложен списокнаших волонтеров и их номера. Пожалуста уитывайте их расписание и они" +
                                " будут рады вам помочь.", "рабочие дни 14-22ч")),
                Arguments.of(new VolunteerRecord(2, "Селезнева Лариса Игоревна", "89246554324",
                        "Дорогой посетитель, наши волонтеры с удовольствием окажут вам помощь, вам будет " +
                                "предложен списокнаших волонтеров и их номера. Пожалуста уитывайте их расписание и они" +
                                " будут рады вам помочь.", "каждый день 8-16ч")),
                Arguments.of(new VolunteerRecord(3, "Фаер Людмила Анатольевна", "89045647676",
                        "Дорогой посетитель, наши волонтеры с удовольствием окажут вам помощь, вам будет " +
                                "предложен списокнаших волонтеров и их номера. Пожалуста уитывайте их расписание и они" +
                                " будут рады вам помочь.", "с четверга по воскресенье 8-18ч"))
        );
    }


    /**
     * Тест возврата всех волонтеров из пустого репозитория
     */
    @Test
    void getAllVolunteerEmptyTest() {
        lenient().when(volunteerRepo.findAll()).thenReturn(List.of());
        lenient().when(volunteerMapper.toRecordList(List.of())).thenReturn(List.of());
        assertTrue(out.getAllVolunteers().isEmpty());
    }

    /**
     * Тест получения списка всех волонтеров из репозитория
     */
    @Test
    void getAllVolunteerTest() {
        List<Volunteer> volunteers = List.copyOf(getTestVolunteerList());
        Collection<VolunteerRecord> volunteerRecords = getTestVolunteerRecordList(volunteers);

        lenient().when(volunteerRepo.findAll()).thenReturn(volunteers);
        lenient().doReturn(volunteerRecords).when(volunteerMapper).toRecordList(volunteers);

        assertEquals(out.getAllVolunteers().size(),3);
        assertTrue(out.getAllVolunteers().containsAll(volunteerRecords));
    }

    /**
     * Тест добавления волонтеров в репозиторий
     * @param volunteerRecord   - Record волонтера
     */
    @ParameterizedTest
    @MethodSource("provideAddParamsPositiveTest")
    void addVolunteerPositiveTest(VolunteerRecord volunteerRecord) {
        lenient().when(volunteerRepo.findAll()).thenReturn(List.of());
        lenient().when(volunteerMapper.toRecordList(List.of())).thenReturn(List.of());
        assertTrue(out.getAllVolunteers().isEmpty());

        Volunteer volunteer = new Volunteer();
        volunteer.setId(volunteerRecord.getId());
        volunteer.setPhone(volunteerRecord.getPhone());
        volunteer.setFullName(volunteerRecord.getFullName());
        volunteer.setInfo_volunteer(volunteerRecord.getInfo_volunteer());
        volunteer.setSchedule(volunteerRecord.getSchedule());

        lenient().when(volunteerMapper.toEntity(volunteerRecord)).thenReturn(volunteer);
        lenient().when(volunteerRepo.save(volunteer)).thenReturn(volunteer);
        lenient().when(volunteerMapper.toRecord(volunteer)).thenReturn(volunteerRecord);
        assertEquals(out.addVolunteer(volunteerRecord), volunteerRecord);
    }

    /**
     * Тест на некорректное добавление волонтера, добавление null
     */
    @Test
    void addVolunteerNegativeTest() {
        lenient().when(volunteerMapper.toEntity(null)).thenReturn(null);
        lenient().when(volunteerRepo.save(null)).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> out.addVolunteer(null));
    }

    /**
     * Тест на удаление волонтера из репозитория
     */
    @Test
    void deleteVolunteerPositiveTest() {
        VolunteerRecord volunteerRecord = new VolunteerRecord(1, "Ивченко Валентин Генадьевич", "89146667454",
                "Дорогой посетитель, наши волонтеры с удовольствием окажут вам помощь, вам будет " +
                        "предложен списокнаших волонтеров и их номера. Пожалуста уитывайте их расписание и они" +
                        " будут рады вам помочь.", "рабочие дни 14-22ч");

        Volunteer volunteer = new Volunteer();
        volunteer.setId(volunteerRecord.getId());
        volunteer.setFullName(volunteerRecord.getFullName());
        volunteer.setPhone(volunteerRecord.getPhone());
        volunteer.setInfo_volunteer(volunteerRecord.getInfo_volunteer());
        volunteer.setSchedule(volunteerRecord.getSchedule());

        lenient().when(volunteerRepo.findById(1L)).thenReturn(Optional.of(volunteer));
        lenient().when(volunteerMapper.toRecord(volunteer)).thenReturn(volunteerRecord);
        assertDoesNotThrow(() -> out.deleteVolunteerById(1L));
    }

    /**
     * Тест на удаление несуществующего волонтера
     */
    @Test
    void deleteVolunteerNegativeTest() {
        lenient().when(volunteerRepo.findById(1L)).thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.deleteVolunteerById(1L));
    }

    /**
     * Тест на поиск волонтера по полному имени
     */
    @Test
    void getVolunteerByFullNamePositiveTest() {
        VolunteerRecord volunteerRecord = new VolunteerRecord(1, "Ивченко Валентин Генадьевич", "89146667454",
                "Дорогой посетитель, наши волонтеры с удовольствием окажут вам помощь, вам будет " +
                        "предложен списокнаших волонтеров и их номера. Пожалуста уитывайте их расписание и они" +
                        " будут рады вам помочь.", "рабочие дни 14-22ч");

        Volunteer volunteer = new Volunteer();
        volunteer.setId(volunteerRecord.getId());
        volunteer.setFullName(volunteerRecord.getFullName());
        volunteer.setPhone(volunteerRecord.getPhone());
        volunteer.setInfo_volunteer(volunteerRecord.getInfo_volunteer());
        volunteer.setSchedule(volunteerRecord.getSchedule());

        lenient().when(volunteerRepo.findVolunteerByFullName("Ивченко Валентин Генадьевич"))
                .thenReturn(Optional.of(volunteer));
        lenient().when(volunteerMapper.toRecord(volunteer)).thenReturn(volunteerRecord);
        assertEquals(out.getVolunteerByFullName("Ивченко Валентин Генадьевич"), volunteerRecord);
    }

    /**
     * Тест на поиск по полному имени несуществующего волонтера
     */
    @Test
    void getVolunteerByFullNameNegativeTest() {
        lenient().when(volunteerRepo.findVolunteerByFullName("Ивченко Валентин Генадьевич"))
                .thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.getVolunteerByFullName("Ивченко Валентин Генадьевич"));
    }

    /**
     * Тест на обновление волонтера
     */
    @Test
    void updateVolunteerPositiveTest() {
        VolunteerRecord volunteerRecord = new VolunteerRecord(1, "Ивченко Валентин Генадьевич", "89146667454",
                "Дорогой посетитель, наши волонтеры с удовольствием окажут вам помощь, вам будет " +
                        "предложен списокнаших волонтеров и их номера. Пожалуста уитывайте их расписание и они" +
                        " будут рады вам помочь.", "рабочие дни 14-22ч");

        Volunteer volunteer = new Volunteer();
        volunteer.setId(1);
        volunteer.setFullName("Ивченко Валентин Генадьевич");
        volunteer.setPhone("89146667454");
        volunteer.setInfo_volunteer("Дорогой посетитель, наши волонтеры с удовольствием окажут вам помощь, вам будет " +
                "предложен списокнаших волонтеров и их номера. Пожалуста уитывайте их расписание и они" +
                " будут рады вам помочь.");
        volunteer.setSchedule("рабочие дни 14-22ч");

        lenient().when(volunteerRepo.findById(1L)).thenReturn(Optional.of(volunteer));
        lenient().when(volunteerMapper.toRecord(volunteer)).thenReturn(volunteerRecord);
        lenient().when(volunteerMapper.toEntity(volunteerRecord)).thenReturn(volunteer);

        Volunteer newVolunteer = new Volunteer();
        newVolunteer.setId(1);
        newVolunteer.setFullName("Селезнева Лариса Игоревна");
        newVolunteer.setPhone("89146667454");
        newVolunteer.setInfo_volunteer("Дорогой посетитель, наши волонтеры с удовольствием окажут вам помощь, вам будет " +
                "предложен списокнаших волонтеров и их номера. Пожалуста уитывайте их расписание и они" +
                " будут рады вам помочь.");
        newVolunteer.setSchedule("рабочие дни 14-22ч");

        VolunteerRecord newVolunteerRecord = new VolunteerRecord(1, "Селезнева Лариса Игоревна", "89146667454",
                "Дорогой посетитель, наши волонтеры с удовольствием окажут вам помощь, вам будет " +
                        "предложен списокнаших волонтеров и их номера. Пожалуста уитывайте их расписание и они" +
                        " будут рады вам помочь.", "рабочие дни 14-22ч");

        lenient().when(volunteerRepo.save(any(Volunteer.class))).thenReturn(newVolunteer);
        lenient().when(volunteerMapper.toRecord(newVolunteer)).thenReturn(newVolunteerRecord);

        VolunteerRecord exceptet = out.updateVolunteer(1L, newVolunteerRecord);
        assertEquals(exceptet, newVolunteerRecord);
    }

    /**
     * Тест на обновление несуществующего волонтера
     */
    @Test
    void updateVolunteerNegativeTest() {
        VolunteerRecord volunteerRecord = new VolunteerRecord(1, "Ивченко Валентин Генадьевич", "89146667454",
                "Дорогой посетитель, наши волонтеры с удовольствием окажут вам помощь, вам будет " +
                        "предложен списокнаших волонтеров и их номера. Пожалуста уитывайте их расписание и они" +
                        " будут рады вам помочь.", "рабочие дни 14-22ч");

        lenient().when(volunteerRepo.findById(anyLong())).thenThrow(ElemNotFound.class);
        assertThrows(ElemNotFound.class, () -> out.updateVolunteer(2L, volunteerRecord));
    }

    /**
     * Выполняет функцию маппера из списка Volunteer в список VolunteerRecord
     * @param volunteers    - список сущностей волонтеров
     * @return              - список Record волонтеров
     */
    private Collection<VolunteerRecord> getTestVolunteerRecordList(List<Volunteer> volunteers) {

        List<VolunteerRecord> volunteerRecords = new ArrayList<>();

        volunteers.forEach(volunteer -> {
            VolunteerRecord record = new VolunteerRecord(volunteer.getId(),volunteer.getFullName(),volunteer.getPhone(),
                    volunteer.getInfo_volunteer(), volunteer.getSchedule());
            volunteerRecords.add(record);
        });

        return volunteerRecords;
    }

    /**
     * Формирует тестовый список волонтеров
     * @return  - список волонтеров для тестов
     */
    private Collection<Volunteer> getTestVolunteerList() {

        List<Volunteer> volunteerList = new ArrayList<>();

        Volunteer volunteer = new Volunteer();
        volunteer.setPhone("896503042");
        volunteer.setFullName("Ивченко Валентин Генадьевич");
        volunteer.setSchedule("рабочие дни 14-22ч");
        volunteer.setId(1);
        volunteer.setInfo_volunteer("Дорогой посетитель, наши волонтеры с удовольствием окажут вам помощь, вам будет " +
                "предложен списокнаших волонтеров и их номера. Пожалуста уитывайте их расписание и они будут рады вам " +
                "помочь.");

        volunteerList.add(volunteer);

        Volunteer volunteer1 = new Volunteer();
        volunteer1.setPhone("89246554324");
        volunteer1.setFullName("Селезнева Лариса Игоревна");
        volunteer1.setSchedule("каждый день 8-16ч");
        volunteer1.setId(2);
        volunteer1.setInfo_volunteer("Дорогой посетитель, наши волонтеры с удовольствием окажут вам помощь, вам будет " +
                "предложен списокнаших волонтеров и их номера. Пожалуста уитывайте их расписание и они будут рады вам " +
                "помочь.");

        volunteerList.add(volunteer1);

        Volunteer volunteer2 = new Volunteer();
        volunteer2.setPhone("89045647676");
        volunteer2.setFullName("Фаер Людмила Анатольевна");
        volunteer2.setSchedule("с четверга по воскресенье 8-18ч");
        volunteer2.setId(3);
        volunteer2.setInfo_volunteer("Дорогой посетитель, наши волонтеры с удовольствием окажут вам помощь, вам будет " +
                "предложен списокнаших волонтеров и их номера. Пожалуста уитывайте их расписание и они будут рады вам " +
                "помочь.");

        volunteerList.add(volunteer2);

        return volunteerList;
    }
}