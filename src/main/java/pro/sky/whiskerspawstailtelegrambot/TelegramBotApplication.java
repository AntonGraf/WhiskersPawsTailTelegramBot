package pro.sky.whiskerspawstailtelegrambot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * запуск без проверки бд
 */
@SpringBootApplication
@OpenAPIDefinition
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class TelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }

}
