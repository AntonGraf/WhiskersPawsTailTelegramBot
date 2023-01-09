package pro.sky.whiskerspawstailtelegrambot.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.whiskerspawstailtelegrambot.entity.Shelter;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.record.ShelterRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.ShelterRepo;

import java.util.Collection;

/**
 * Сервис слой для приюта
 */
@Service
@Slf4j
public class ShelterService {

    private ShelterRepo shelterRepo;
    /**
     * Метод, который показывает все сообщения приюта
     *
     * @return список всех сообщений
     */
    public String getOfShelterMessage(Long id) {
        log.info("Was invoked method for get list of MessageShelter from DB");
        return shelterRepo.findById(id).orElseThrow(ElemNotFound::new).getAboutShelter();
    }
}
