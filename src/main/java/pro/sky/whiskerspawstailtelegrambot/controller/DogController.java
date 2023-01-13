package pro.sky.whiskerspawstailtelegrambot.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.whiskerspawstailtelegrambot.exception.ErrorResponse;
import pro.sky.whiskerspawstailtelegrambot.mapper.DogMapper;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;
import pro.sky.whiskerspawstailtelegrambot.service.DogService;
import java.io.IOException;
import java.util.Collection;

@RestController
@Getter
@Setter
@Slf4j
@RequestMapping("/dogs")
public class DogController {
  private final DogService dogService;
  private final DogMapper dogMapper;


  public DogController(DogService dogService, DogMapper dogMapper) {
    this.dogService = dogService;
    this.dogMapper = dogMapper;
  }


  @Operation(summary = "Получение собаки по id")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Получена сущность собаки",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = DogRecord.class)))
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
  @GetMapping
  public ResponseEntity<?> findDog(@RequestParam(name = "id") Long id) {
    DogRecord dogRecord = dogService.findDog(id);
    return ResponseEntity.ok().body(dogRecord);
  }

  @Operation(summary = "Получение списка всех собак в приюте")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Получен список всех собак из БД",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = DogRecord.class)))
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
  @GetMapping("/all")
  public ResponseEntity<?> findAllDog() {
    Collection<DogRecord> recordCollection = dogService.findAllDog();
    return ResponseEntity.ok().body(recordCollection);
  }


  @Operation(summary = "Получение фото собаки по id")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Получена фото собаки",
          content = {
              @Content(
                  mediaType = "multipart/form-data",
                  array = @ArraySchema(schema = @Schema(implementation = DogRecord.class)))
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Либо не введены парметры, либо нет объекта по указанному идентификатору" +
              "и идентификаторы меньше 1",
          content = {
              @Content(
                  mediaType = "multipart/form-data",
                  array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))
          }
      )
  })
  @GetMapping(value = "/photo", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<byte[]> getDogPhoto(@RequestParam(name = "id") Long id) {
    DogRecord dog = dogService.findDog(id);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType(dog.getMediaType()));
    headers.setContentLength(dog.getPhoto().length);
    return ResponseEntity.status(HttpStatus.OK).headers(headers).body(dog.getPhoto());
  }

  @Operation(summary = "Добавление новой собаки в БД")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Добавлена новая собака",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = DogRecord.class)))
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Не введены парметры",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))
          }
      )
  })
  @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> addDog(
      @RequestParam(required = false, name = "name") String fullName,
      @RequestParam(required = false, name = "age") String age,
      @RequestParam(required = false, name = "des") String description,
      @RequestParam(required = false, name = "photo") MultipartFile photo) throws IOException {
    dogService.addDog(fullName, age, description, photo);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Изменение данных собаки")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Изменены данные собаки",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = DogRecord.class)))
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
  @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> editDog(
      @RequestParam(name ="id") Long id,
      @RequestParam(required = false, name = "name") String fullName,
      @RequestParam(required = false, name = "age") String age,
      @RequestParam(required = false, name = "des") String description,
      @RequestParam(required = false) MultipartFile photo) throws IOException {
    dogService.editDog(id, fullName, age, description, photo);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Добавление id усыновителя в БД в таблицу Dog")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Добавлено id усыновителя",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = DogRecord.class)))
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
  @PutMapping(value = "/parent")
  public ResponseEntity<DogRecord> addIdAdoptiveParent(@RequestParam(name = "dogId") Long dogId, @RequestParam(name = "id") Long adoptiveParentId) {
    dogService.addIdAdoptiveParent(dogId, adoptiveParentId);
    return ResponseEntity.ok().build();
  }


  @Operation(summary = "Удаление собаки по id")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Удалена собака из БД",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = DogRecord.class)))
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
  @DeleteMapping
  public ResponseEntity<DogRecord> removeDog(@RequestParam(name = "id") Long id) {
    dogService.removeDog(id);
    return ResponseEntity.ok().build();
  }

}
