package pro.sky.whiskerspawstailtelegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.whiskerspawstailtelegrambot.entity.Volunteer;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.mapper.VolunteerMapper;
import pro.sky.whiskerspawstailtelegrambot.record.VolunteerRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.VolunteerRepo;

import java.util.Collection;

/**
 * Сервис для волонтеров
 */
@Service
@Slf4j
public class VolunteerService {

    private final VolunteerRepo volunteerRepo;
    private final VolunteerMapper volunteerMapper;

    public VolunteerService(VolunteerRepo volunteerRepo, VolunteerMapper volunteerMapper) {
        this.volunteerRepo = volunteerRepo;
        this.volunteerMapper = volunteerMapper;
    }


    public Collection<VolunteerRecord> getAllVolunteers() {
        log.info("Получаем список всех волонтеров");
        return volunteerMapper.toRecordList(volunteerRepo.findAll());
    }
    /**
     * Добавление волонтера вБД
     * @param volunteerRecord   - сущность для сохранения
     * @return                  - сохраненную сущность
     */
    public VolunteerRecord addVolunteer(VolunteerRecord volunteerRecord) {
        log.info("Добавляем волонтера" + volunteerRecord);
        return volunteerMapper.toRecord(volunteerRepo.save(volunteerMapper.toEntity(volunteerRecord)));
    }

    /**
     * Удаление волонтера по Id
     * @param volunteerId   - id волонтера
     * @return              - удаленная сущность
     */
    public VolunteerRecord deleteVolunteerById(long volunteerId) {
        log.info("Удаляем волонтера" + volunteerId);
        VolunteerRecord volunteerRecord = getVolunteerById(volunteerId);
        volunteerRepo.deleteById(volunteerId);
        return volunteerRecord;
    }

    /**
     * Возвращает сущность волонтер по Id
     * @param volunteerId   - id для поиска
     * @return              - найденная сущность
     */
    public VolunteerRecord getVolunteerById(long volunteerId) {
        log.info("Ищем волонтера по id" + volunteerId);
        return volunteerMapper.toRecord(volunteerRepo.findById(volunteerId).orElseThrow(ElemNotFound::new));
    }

    /**
     * Поиск волонтера по имени
     * @param fullName  - полное имя волонтера
     * @return          - возвращает найденного волонтера
     */
    public VolunteerRecord getVolunteerByFullName(String fullName) {
        log.info("Ищем волонтера по имени " + fullName);
        return volunteerMapper.toRecord(volunteerRepo.findVolunteerByFullName(fullName).orElseThrow(ElemNotFound::new));
    }

    /**
     * Обновляет волонтера по Id
     * @param volunteerId       - id волонтера, которого необходимо обновить
     * @param volunteerRecord   - данные волонтера для обновления
     * @return                  - обновленного волонтера
     */
    public VolunteerRecord updateVolunteer(long volunteerId, VolunteerRecord volunteerRecord) {
        log.info("Обновление волонтера" + volunteerId);

        Volunteer oldVolunteer = volunteerMapper.toEntity(getVolunteerById(volunteerId));
        oldVolunteer.setInfo_volunteer(volunteerRecord.getInfo_volunteer());
        oldVolunteer.setFullName(volunteerRecord.getFullName());
        oldVolunteer.setPhone(volunteerRecord.getPhone());
        oldVolunteer.setSchedule(volunteerRecord.getSchedule());
        return volunteerMapper.toRecord(volunteerRepo.save(oldVolunteer));
    }
}
