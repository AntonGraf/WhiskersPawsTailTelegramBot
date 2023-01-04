package pro.sky.whiskerspawstailtelegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.service.AdoptiveParentService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

/**
 * Котроллер для волонтеров.
 */
@RestController
@Getter
@Setter
@Slf4j
@Tag(name = "AdoptiveParentController",
        description = "The Admin API")
public class AdoptiveParentController {
    final AdoptiveParentService service;

    public AdoptiveParentController(AdoptiveParentService service) {
        this.service = service;
    }

    @Operation(summary = "Получить массив отчетов по идентификатору родитетеля и собаки")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Get list of Reports",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ReportRecord.class)))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Либо не введены парметры, либо нет объекта по указанному идентификатору" +
                            "и идентификаторы меньше 1"
            )
    })
    @RequestMapping(value = "/getReportByParentAndDog", method = RequestMethod.GET)
    public ResponseEntity<Collection<ReportRecord>> getReportByParentAndDog(
            @NotBlank(message = "parentId is empty")
            @Min(value = 1, message = "Идентификатор родителя должен быть больше 0")
            @RequestParam(name = "parentId")
            @Parameter(description = "Идентификатор родителя",
                    example = "1")
            long parentId,
            @NotBlank(message = "dogId is empty")
            @Min(value = 1, message = "Идентификатор собаки должен быть больше 0")
            @RequestParam(name = "dogId")
            @Parameter(description = "Идентификатор собаки",
                    example = "1")
            long dogId) {
        return ResponseEntity.ok(service.getReportByParentAndDog(parentId, dogId));
    }

    @Operation(summary = "Получить усыновителя по id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Get AdoptiveParent by current parentId,list of dog and reports",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = AdoptiveParentRecord.class)))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Либо не введены парметры, либо нет объекта по указанному идентификатору" +
                            "и идентификаторы меньше 1"
            )
    })
    @RequestMapping(value = "/getAdoptiveParentByID", method = RequestMethod.GET)
    public ResponseEntity<AdoptiveParentRecord> getAdoptiveParentByID
    (
            @NotBlank(message = "parentId is empty")
            @Min(value = 1, message = "Идентификатор родителя должен быть больше 0")
            @RequestParam(name = "parentId")
            @Parameter(description = "Идентификатор родителя",
                    example = "1")
            long parentId
    ) {
        return ResponseEntity.ok(service.getAdoptiveParentByID(parentId));
    }
}


