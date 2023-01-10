package pro.sky.whiskerspawstailtelegrambot.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.repository.ShelterRepo;

/**
 * Сервис слой для приюта
 */
@Service
@Slf4j
public class ShelterService {

    private ShelterRepo shelterRepo;

    public ShelterService(ShelterRepo shelterRepo) {
        this.shelterRepo = shelterRepo;
    }

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
