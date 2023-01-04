package pro.sky.whiskerspawstailtelegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
                .orElseThrow(IllegalArgumentException::new)
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
        return adoptiveParentMapper.toRecord(adoptiveParentRepo.findById(parentId).orElseThrow(IllegalArgumentException::new));
    }


}
