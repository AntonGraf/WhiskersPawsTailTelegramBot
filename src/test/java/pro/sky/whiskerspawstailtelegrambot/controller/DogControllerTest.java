package pro.sky.whiskerspawstailtelegrambot.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pro.sky.whiskerspawstailtelegrambot.TelegramBotUpdatesListener;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;
import pro.sky.whiskerspawstailtelegrambot.mapper.AdoptiveParentMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.DogMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.ReportMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.VolunteerMapper;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.AdoptiveParentRepo;
import pro.sky.whiskerspawstailtelegrambot.repository.DogRepository;
import pro.sky.whiskerspawstailtelegrambot.repository.ShelterRepo;
import pro.sky.whiskerspawstailtelegrambot.repository.VolunteerRepo;
import pro.sky.whiskerspawstailtelegrambot.service.AdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.service.DogService;
import pro.sky.whiskerspawstailtelegrambot.service.ShelterService;
import pro.sky.whiskerspawstailtelegrambot.service.VolunteerService;

@WebMvcTest(DogController.class)
@AutoConfigureMockMvc
class DogControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;
  @InjectMocks
  private DogController dogController;
//  @InjectMocks
//  private AdoptiveParentController adoptiveParentController;
//  @InjectMocks
//  private VolunteerController volunteerController;
  @MockBean
  private DogService dogService;
//  @MockBean
//  private AdoptiveParentService adoptiveParentService;
//  @MockBean
//  private VolunteerService volunteerService;
//  @MockBean
//  private ShelterService shelterService;
//  @MockBean
//  private AdoptiveParentRepo adoptiveParentRepo;
//  @MockBean
//  private ShelterRepo shelterRepo;
  @MockBean
  private DogRepository dogRepository;
//  @MockBean
//  private VolunteerRepo volunteerRepo;
//  @Autowired
//  private AdoptiveParentMapper adoptiveParentMapper;
  @MockBean
  private DogMapper dogMapper;
//  @Autowired
//  private VolunteerMapper volunteerMapper;
//  @Autowired
//  private ReportMapper reportMapper;
  @MockBean
  DogRecord dogRecord;
//  @MockBean
//  TelegramBotUpdatesListener telegramBotUpdatesListener;


  @Test
  public void dogTest1() throws Exception {
    final Long id = 1L;
    final Long dogId = 1L;
    final String fullName = "Dog";
    final String age = "5";
    final String description = "DogDescription";
//
//    JSONObject jsonObject = new JSONObject();
//    jsonObject.put("id", id);
//    jsonObject.put("fullName", fullName);
//    jsonObject.put("age", age);
//    jsonObject.put("description", description);

    MockMultipartFile file = new MockMultipartFile("data", "photo.jpeg",
        MediaType.MULTIPART_FORM_DATA_VALUE, "photo.jpeg".getBytes());
    Dog dog = new Dog();
    dog.setId(id);
    dog.setFullName(fullName);
    dog.setAge(age);
    dog.setDescription(description);


//    DogRecord record = dogMapper.toRecord(dog);
    DogRecord record = new DogRecord();
    record.setId(id);
    record.setFullName(fullName);
    record.setAge(age);
    record.setDescription(description);
    record.setPhoto(file.getBytes());
    record.setFileSize(file.getSize());
    record.setMediaType("image/jpeg");


//    when(dogRepository.save(any(Dog.class))).thenReturn(dog);
//    when(dogRepository.findById(any(Long.class))).thenReturn(Optional.of(dog));
//    when(dogRepository.findAll()).thenReturn(List.of());
//    when(dogService.findDog(any(Long.class))).thenReturn(record);
    when(dogService.findDog(id)).thenReturn(record);



    MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    mockMvc.perform(multipart("/dogs/add").file(file)//post
        .param("name", "dogName")
        .param("age", "1111")
        .param("des", "DogDescription")
        .accept(MediaType.MULTIPART_FORM_DATA_VALUE))
        .andDo(print())
        .andExpect(status().isOk());
    mockMvc.perform(multipart(HttpMethod.PUT,"/dogs").file(file)
            .param("id", "1")//put изменение
            .param("name", fullName)
            .param("age", age)
            .param("des", description)
            .accept(MediaType.MULTIPART_FORM_DATA_VALUE))
        .andDo(print())
        .andExpect(status().isOk());
    mockMvc.perform(MockMvcRequestBuilders//put изменение усыновителя
        .put("/dogs/parent")
            .param("dogId", "1")
            .param("id", "2")
            .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.MULTIPART_FORM_DATA_VALUE))
        .andDo(print())
        .andExpect(status().isOk());
    mockMvc.perform(MockMvcRequestBuilders//del
            .delete("/dogs")
            .param("id", "2")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.MULTIPART_FORM_DATA_VALUE))
        .andDo(print())
        .andExpect(status().isOk());
    mockMvc.perform(MockMvcRequestBuilders//get
            .get("/dogs")
            .param("id", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
    mockMvc.perform(MockMvcRequestBuilders//getAll
            .get("/dogs/all")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
    mockMvc.perform(multipart(HttpMethod.GET,"/dogs/photo").file(file)
            .param("id", "1")//get фото
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .accept(MediaType.MULTIPART_FORM_DATA_VALUE))
        .andDo(print())
        .andExpect(status().isOk());






  }
}