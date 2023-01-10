package pro.sky.whiskerspawstailtelegrambot;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.whiskerspawstailtelegrambot.controller.AdoptiveParentController;
import pro.sky.whiskerspawstailtelegrambot.controller.DogController;
import pro.sky.whiskerspawstailtelegrambot.controller.VolunteerController;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;
import pro.sky.whiskerspawstailtelegrambot.mapper.AdoptiveParentMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.DogMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.ReportMapper;
import pro.sky.whiskerspawstailtelegrambot.mapper.VolunteerMapper;
import pro.sky.whiskerspawstailtelegrambot.repository.AdoptiveParentRepo;
import pro.sky.whiskerspawstailtelegrambot.repository.DogRepository;
import pro.sky.whiskerspawstailtelegrambot.repository.ShelterRepo;
import pro.sky.whiskerspawstailtelegrambot.repository.VolunteerRepo;
import pro.sky.whiskerspawstailtelegrambot.service.AdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.service.DogService;
import pro.sky.whiskerspawstailtelegrambot.service.ShelterService;
import pro.sky.whiskerspawstailtelegrambot.service.VolunteerService;

@WebMvcTest
class LupaevTelegramBotApplicationTests_Dog2 {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private DogController dogController;

    @InjectMocks
    private AdoptiveParentController adoptiveParentController;

    @InjectMocks
    private VolunteerController volunteerController;

    @SpyBean
    private DogService dogService;

    @SpyBean
    private AdoptiveParentService adoptiveParentService;

    @SpyBean
    private VolunteerService volunteerService;

    @SpyBean
    private ShelterService shelterService;

    @MockBean
    private AdoptiveParentRepo adoptiveParentRepo;

    @MockBean
    private ShelterRepo shelterRepo;

    @MockBean
    private DogRepository dogRepository;

    @MockBean
    private VolunteerRepo volunteerRepo;

    @MockBean
    private AdoptiveParentMapper adoptiveParentMapper;

    @MockBean
    DogMapper dogMapper;

    @MockBean
    VolunteerMapper volunteerMapper;

    @MockBean
    ReportMapper reportMapper;

    @MockBean
    TelegramBotUpdatesListener telegramBotUpdatesListener;

    @Test
    public void dogTest ()throws Exception {
        final Long id = 1L;
        final String fullName = "Dog";
        final String age = "5";
        final String description = "DogDescription";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("fullName", fullName);
        jsonObject.put("age", age);
        jsonObject.put("description", description);
        Dog dog = new Dog();
        dog.setId(id);
        dog.setFullName(fullName);
        dog.setAge(age);
        dog.setDescription(description);

        when(dogRepository.save(any(Dog.class))).thenReturn(dog);
        when(dogRepository.findById(any(Long.class))).thenReturn(Optional.of(dog));
        when(dogRepository.findAll()).thenReturn(java.util.List.of());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/dogs/add")
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.fullName").value(fullName))
            .andExpect(jsonPath("$.age").value(age))
            .andExpect(jsonPath("$.description").value(description));
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/students/1")
//                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//            .andExpect(jsonPath("$.id").value(id))
//            .andExpect(jsonPath("$.name").value(name))
//            .andExpect(jsonPath("$.age").value(age));

    }







}
