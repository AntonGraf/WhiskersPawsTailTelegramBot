package pro.sky.whiskerspawstailtelegrambot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(ElemNotFound.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(ElemNotFound ex) {
        String incorrectRequest = "Такого элемента нет";
        ErrorResponse error = new ErrorResponse(incorrectRequest);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Отловка спринга
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException(MethodArgumentNotValidException ex) {
        String badRequest = "Какие-то данные были введены неправильно";
        ErrorResponse error = new ErrorResponse(badRequest);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException(MethodArgumentTypeMismatchException ex) {
        String badRequest = "Null ввести нельзя";
        ErrorResponse error = new ErrorResponse(badRequest);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException(ConstraintViolationException ex) {
        String badRequest = "Какие-то данные были введены неправильно";
        ErrorResponse error = new ErrorResponse(badRequest);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IFElementExist.class)
    public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException(IFElementExist ex) {
        String badRequest = "Элемент уже есть в базе";
        ErrorResponse error = new ErrorResponse(badRequest);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}