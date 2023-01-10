package pro.sky.whiskerspawstailtelegrambot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import pro.sky.whiskerspawstailtelegrambot.controller.DogController;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;
import pro.sky.whiskerspawstailtelegrambot.mapper.DogMapper;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.DogRepository;
import pro.sky.whiskerspawstailtelegrambot.service.DogService;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Lupaev_TelegramBotApplicationTests_Dog {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DogController dogController;
    @Autowired
    private DogRepository dogRepository;
    @Autowired
    private DogService dogService;
    @Autowired
    private DogMapper dogMapper;


    @Test
    void contextLoads() {
        assertNotNull(dogController);
    }

    @Test
    public void testGet() throws Exception {
        assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/dogs/all", DogRecord.class))
                .isNotNull();
        assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/dogs/1", DogRecord.class))
                .isNotNull();
    }

    @Test
    public void testPost () throws Exception {
        Dog dog = new Dog();
        dog.setFullName("testName");
        dog.setAge(1);
        dog.setDescription("testDog");
        dog.setFileSize(1024);
        assertThat(this.restTemplate.postForEntity("http://localhost:" + port + "/dogs", dog, Dog.class))
                .isNotNull();
    }


    @Test
    public void putTest() throws  Exception {
        DogRecord dogRecord = dogService.findDog(1);
        Dog dog = new Dog();
//        dog.setId(1);
//        dog.setFullName("testName");
//        dog.setAge(1);

        dog = dogMapper.toEntity(dogRecord);
        dog.setFullName("testName");
        dog.setDescription("testDog");
//        dog.setFileSize(1024);
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/dogs", HttpMethod.PUT,new HttpEntity<>(dog), Dog.class))
                .isNotNull();
    }



}
