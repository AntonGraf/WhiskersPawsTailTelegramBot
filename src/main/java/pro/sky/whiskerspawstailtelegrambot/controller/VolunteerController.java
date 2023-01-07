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
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.whiskerspawstailtelegrambot.exception.ErrorResponse;
import pro.sky.whiskerspawstailtelegrambot.record.VolunteerRecord;
import pro.sky.whiskerspawstailtelegrambot.service.VolunteerService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

/**
 * Контроллер для волонтеров
 */
@RestController
@Getter
@Setter
@Slf4j
@Tag(name = "VolunteerController",
        description = "The Admin API")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(summary = "Получить волонтера по id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Получен волонтер по текущему id",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = VolunteerRecord.class)))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Либо не введены парметры, либо нет объекта по указанному идентификатору" +
                            "и идентификаторы меньше 1",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))
                    }
            )
    })
    @GetMapping(value = "/getVolunteerById")
    public ResponseEntity<VolunteerRecord> getAdoptiveParentByID
            (
                    @NotBlank(message = "volunteerId пустой")
                    @Min(value = 1, message = "Идентификатор волонтера должен быть больше 0")
                    @RequestParam(name = "volunteerId")
                    @Parameter(description = "Идентификатор волонтера",
                            example = "1")
                    long volunteerId
            ) {
        return ResponseEntity.ok(volunteerService.getVolunteerById(volunteerId));
    }

    @Operation(summary = "Удалить волонтера по id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Волонтер удален из БД",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = VolunteerRecord.class)))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Либо не введены парметры, либо нет объекта по указанному идентификатору" +
                            "и идентификаторы меньше 1",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))
                    }
            )
    })
    @DeleteMapping(value = "/deleteVolunteerId")
    public ResponseEntity<VolunteerRecord> deleteVolunteerById
            (
                    @NotBlank(message = "volunteerId пустой")
                    @Min(value = 1, message = "Идентификатор волонтера должен быть больше 0")
                    @RequestParam(name = "volunteerId")
                    @Parameter(description = "Идентификатор волонтера",
                            example = "1")
                    long volunteerId
            ) {
        return ResponseEntity.ok(volunteerService.deleteVolunteerById(volunteerId));
    }


    @Operation(summary = "Получить всех волонтеров")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получен список волонтеров",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = VolunteerRecord.class)))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Нет такой сущности",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = MethodArgumentNotValidException.class)))
                            }
                    )
            })
    @GetMapping(value = "/getListOfVolunteer")
    public ResponseEntity<Collection<VolunteerRecord>> getListOfVolunteerRecord(){
        return ResponseEntity.ok(volunteerService.getAllVolunteers());
    }

}
