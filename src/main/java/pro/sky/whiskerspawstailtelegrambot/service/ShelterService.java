package pro.sky.whiskerspawstailtelegrambot.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.mapper.ShelterMapper;
import pro.sky.whiskerspawstailtelegrambot.record.ShelterRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.ShelterRepository;

/**
 * Сервис для Приюта
 */
@Service
@Slf4j
public  class ShelterService {
    private final ShelterRepository shelterRepository;

    private final ShelterMapper shelterMapper;

    public ShelterService(ShelterRepository shelterRepository, ShelterMapper shelterMapper) {
        this.shelterRepository = shelterRepository;
        this.shelterMapper = shelterMapper;
    }

    public String aboutShelter () {
        log.info("Поиск информации о приюте в БД");
        Long shelterId = 1L;
        ShelterRecord shelterRecord = shelterMapper.toRecord(shelterRepository.findById(shelterId).orElseThrow(ElemNotFound::new));
        return shelterRecord.getAboutShelter();
    }

}
