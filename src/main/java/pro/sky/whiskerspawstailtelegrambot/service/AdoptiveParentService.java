package pro.sky.whiskerspawstailtelegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
import pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound;
import pro.sky.whiskerspawstailtelegrambot.mapper.AdoptiveParentMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.ReportMapper;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.AdoptiveParentRepo;

import java.util.Collection;

/**
 * Сервис слой для усыновителя
 */
@Service
@Slf4j
public class AdoptiveParentService {
    AdoptiveParentMapper adoptiveParentMapper;
    ReportMapper reportMapper;
    AdoptiveParentRepo adoptiveParentRepo;

    public AdoptiveParentService(AdoptiveParentMapper adoptiveParentMapper, ReportMapper reportMapper, AdoptiveParentRepo adoptiveParentRepo) {
        this.adoptiveParentMapper = adoptiveParentMapper;
        this.reportMapper = reportMapper;
        this.adoptiveParentRepo = adoptiveParentRepo;
    }

    /**
     * Получить отчет по идентификатору усыновителя и собаки
     *
     * @param parentId идентификатор усыновителя
     * @param dogId    идентификатор собаки
     * @return коллекция отчетов
     */
    public Collection<ReportRecord> getReportByParentAndDog(long parentId, long dogId) {
        log.info("Was invoked method for get list of report by current parentId and dogId");
        return getAdoptiveParentByID(parentId).getDogs()
                .stream()
                .filter(x -> x.getId() == dogId)
                .findFirst()
                .orElseThrow(ElemNotFound::new)
                .getReports();
    }

    /**
     * Получить усыновителя по id
     *
     * @param parentId идентификатор усыновителя
     * @return усыновитель
     */
    public AdoptiveParentRecord getAdoptiveParentByID(long parentId) {
        log.info("Was invoked method for get AdoptiveParent by current parentId");
        return adoptiveParentMapper.toRecord(adoptiveParentRepo.findById(parentId).orElseThrow(ElemNotFound::new));
    }

    /**
     * Удалить усыновителя по id
     *
     * @param parentId идентификатор усыновителя
     * @return если все успешно то сам усыновитель, либо ошибка
     */
    public AdoptiveParentRecord deleteAdoptiveParentByID(long parentId) {
        log.info("Was invoked method for delete AdoptiveParent by current parentId");
        AdoptiveParentRecord adoptiveParentRecord = getAdoptiveParentByID(parentId);
        adoptiveParentRepo.deleteById(parentId);
        return adoptiveParentRecord;
    }

    /**
     * Добавить усыновителя
     *
     * @param adoptiveParentRecord, сущность которую нужно сохранить
     * @return если все успешно то сам усыновитель, либо ошибка
     */
    public AdoptiveParentRecord addAdoptiveParent(AdoptiveParentRecord adoptiveParentRecord) {
        log.info("Was invoked method for add AdoptiveParent to DB");
        if(adoptiveParentRecord == null) throw new ElemNotFound();
        return adoptiveParentMapper.toRecord(
                adoptiveParentRepo.save(adoptiveParentMapper.toEntity(adoptiveParentRecord)));
    }

    /**
     * Метод, который показывает всех усыновителей в бд
     *
     * @return список всех усыновителей
     */
    public Collection<AdoptiveParentRecord> getListOfAdoptiveParent() {
        log.info("Was invoked method for get list of AdoptiveParent from DB");
        Collection<AdoptiveParentRecord> collection = adoptiveParentMapper.toRecordList(adoptiveParentRepo.findAll());
        if(collection == null || collection.isEmpty()) throw new ElemNotFound();
        return collection;
    }

    /**
     * Метод, который делает адейт полей усыновителя
     *
     * @return обновленный усыновитель
     */
    public AdoptiveParentRecord updateAdoptiveParent(long parentId, AdoptiveParentRecord adoptiveParentRecord) {
        log.info("Was invoked method for updateAdoptiveParent");
        if(adoptiveParentRecord == null || parentId < 1) throw new ElemNotFound();
        AdoptiveParentRecord oldParent = getAdoptiveParentByID(parentId);
        oldParent.setFullName(adoptiveParentRecord.getFullName());
        oldParent.setPhone(adoptiveParentRecord.getPhone());
        oldParent.setParent(adoptiveParentRecord.isParent());
        oldParent.setState(adoptiveParentRecord.getState());
        oldParent.setDogs(adoptiveParentRecord.getDogs());
        log.debug("check before save {}", oldParent);
        return adoptiveParentMapper.toRecord(adoptiveParentRepo.save(adoptiveParentMapper.toEntity(oldParent)));
    }

    /**
     * Метод по поиску усыновителя по 3 параметрам, если их передали
     * @param fullName Поспелов Дмитрий александрови (необязательный параметр)
     * @param phone Телефон усыновителя (необязательный параметр)
     * @param chatId chatId (необязательный параметр)
     * @return либо id усыновителя, либо эксепш {@link pro.sky.whiskerspawstailtelegrambot.exception.ElemNotFound}
     */
    public Long getParentIdByNameAndPhoneAndChatId(String fullName, String phone, Long chatId) {
        log.info("Was invoked method for getParentIdByNameAndPhoneAndChatId");
        long result;
        if(fullName!=null && !fullName.isEmpty()){
            AdoptiveParent adoptiveParent = adoptiveParentRepo.getAdoptiveParentByFullName(fullName);
            if (adoptiveParent == null) throw new ElemNotFound();
            result = adoptiveParent.getId();
            return result;
        } else if (phone!=null && !phone.isEmpty()) {
            AdoptiveParent adoptiveParent = adoptiveParentRepo.getAdoptiveParentByPhone(phone);
            if (adoptiveParent == null) throw new ElemNotFound();
            result = adoptiveParent.getId();
            return result;
        }else if (chatId!=null && chatId > 0){
            AdoptiveParent adoptiveParent = adoptiveParentRepo.getAdoptiveParentByChatId(chatId);
            if (adoptiveParent == null) throw new ElemNotFound();
            result = adoptiveParent.getId();
            return result;
        }
        throw new ElemNotFound();
    }

    /**
     * Поиск усыновителя по chatId
     * @param chatId
     * @return рекордер усыновителя если есть, null если нет
     */
    public AdoptiveParentRecord findAdoptiveParentByChatId(Long chatId){
        log.info("Was invoked method for findAdoptiveParentByChatId");
        return adoptiveParentMapper
        .toRecord(adoptiveParentRepo.getAdoptiveParentByChatId(chatId));
    }
}
