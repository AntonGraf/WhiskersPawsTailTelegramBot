package pro.sky.whiskerspawstailtelegrambot.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
/**
 * эксепш - класс обертка
 * {@link pro.sky.whiskerspawstailtelegrambot.exception.CustomExceptionHandler#handleUserNotFoundException(ElemNotFound)}
 */
public class ErrorResponse {
    private String message;

    private String exMessage;

}