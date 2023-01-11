package pro.sky.whiskerspawstailtelegrambot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.HttpEntity;
import pro.sky.whiskerspawstailtelegrambot.controller.DogController;
import pro.sky.whiskerspawstailtelegrambot.entity.Dog;
import pro.sky.whiskerspawstailtelegrambot.mapper.DogMapper;
import pro.sky.whiskerspawstailtelegrambot.record.DogRecord;
import pro.sky.whiskerspawstailtelegrambot.repository.DogRepository;
import pro.sky.whiskerspawstailtelegrambot.service.DogService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class lupaevTelegramBotApplicationTestsDogSpringBootTest {

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
    public void testGet() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/dogs/all", String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/dogs/1", String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/dogs?id=3", String.class))
                .isNotNull();
    }

    @Test
    public void testPost () {
        Dog dog = new Dog();
        dog.setFullName("testName");
        dog.setDescription("testDog");
        assertThat(this.restTemplate.postForEntity("http://localhost:" + port + "/dogs/add", new HttpEntity<>(dog), Dog.class))
                .isNotNull();
    }


    @Test
    public void putTest() {
        DogRecord dogRecord = dogService.findDog(2);
        Dog dog = dogMapper.toEntity(dogRecord);
        dog.setFullName("testName");
        dog.setDescription("testDog");
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/dogs", PUT,new HttpEntity<>(dog), Dog.class))
                .isNotNull();
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/dogs/2?id=2", PUT,new HttpEntity<>(dog), Dog.class))
                .isNotNull();

    }

    @Test
    public void delTest() {
        DogRecord dogRecord = dogService.findDog(15);
        Dog dog = dogMapper.toEntity(dogRecord);
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/dogs/15", DELETE,new HttpEntity<>(dog), Dog.class))
            .isNotNull();
    }



}
