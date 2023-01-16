package pro.sky.whiskerspawstailtelegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.whiskerspawstailtelegrambot.record.ReportRecord;
import pro.sky.whiskerspawstailtelegrambot.service.ValidateReportService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

/**
 * Контроллер для проверки отчетов
 */
@RestController
@RequestMapping("/validateReport")
@Slf4j
@Tag(name = "ValidateReportController",
        description = "The Admin API")
public class ValidateReportController {

    private final ValidateReportService validateReportService;

    public ValidateReportController(ValidateReportService validateReportService) {
        this.validateReportService = validateReportService;
    }

    @Operation(summary = "Получить список отчетов за последние сутки")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получен список некорректно заполненных отчетов за последние сутки",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ReportRecord.class)))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Нет таких отчетов",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = MethodArgumentNotValidException.class)))
                            }
                    )
            })
    @GetMapping(value = "/getReportsByLastDay")
    public ResponseEntity<Collection<ReportRecord>> getReportsByLastDay() {
        return ResponseEntity.ok(validateReportService.getReportsByLastDay());
    }

    @Operation(summary = "Проверить корректность заполнения отчетов")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получен список некорректно заполненных отчетов за последние сутки",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ReportRecord.class)))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Нет таких отчетов",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = MethodArgumentNotValidException.class)))
                            }
                    )
            })
    @GetMapping(value = "/getBadReportsByLastDay")
    public ResponseEntity<Collection<ReportRecord>> getBadReportsByLastDay() {
        return ResponseEntity.ok(validateReportService.getBadReportsByLastDay());
    }

    @Operation(summary = "Отправка сообщения усыновителям, некорректно заполнивших отчеты")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отправлены сообщения усыновителям"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Нет таких отчетов",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = MethodArgumentNotValidException.class)))
                            }
                    )
            })
    @GetMapping(value = "/sendMessageToAdoptiveParentsWithBadReport")
    public ResponseEntity sendMessageToAdoptiveParentsWithBadReport() {
        validateReportService.sendMessageToAdoptiveParentsWithBadReport();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Отправка сообщения усыновителям, некорректно заполнивших отчеты")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отправлены сообщения усыновителям"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Нет таких отчетов",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = MethodArgumentNotValidException.class)))
                            }
                    )
            })
    @GetMapping(value = "/sendMessageToAdoptiveParentAnyReportLastDays")
    public ResponseEntity sendMessageToAdoptiveParentAnyReportLastDays(@RequestParam
                                                                           @NotBlank(message = "Количество дней пусто")
                                                                           @Min(value = 1, message = "Срок " +
                                                                                   "должен быть больше 0")
                                                                           @Parameter(description = "Количество дней " +
                                                                                   "без отчетов",
                                                                                   example = "2")
                                                                           int anyReportDays

    ) {
        validateReportService.sendMessageToAdoptiveParentAnyReportLastDays(anyReportDays);
        return ResponseEntity.ok().build();
    }
}
