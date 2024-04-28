package edu.miu.cs489.InsightHub.advice;

import edu.miu.cs489.InsightHub.exception.ContentNotFoundException;
import edu.miu.cs489.InsightHub.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class InsightHubWebExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFoundException(UserNotFoundException exception){
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("errorMessage", exception.getMessage());
        return errorMessage;
    }

    @ExceptionHandler(ContentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleContentNotFoundException(ContentNotFoundException exception){
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("errorMessage", exception.getMessage());
        return errorMessage;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String , String >handleDataValidationErrors(MethodArgumentNotValidException methodArgumentNotValidException){
        var errorMap = new HashMap<String, String>();
        methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .forEach(
                        error -> errorMap.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );
        return errorMap;
    }
}
