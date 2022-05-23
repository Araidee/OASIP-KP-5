package com.example.backend221.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handlerInvalidArgument(MethodArgumentNotValidException ex){
        Map<String , String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        return errorMap;
    }
}
//@ControllerAdvice
//@Slf4j
//public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
//    public ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex,
//            HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request) {
//        List<String> errorList =
//                ex
//                        .getBindingResult()
//                        .getFieldErrors()
//                        .stream()
//                        .map(fieldError -> fieldError.getDefaultMessage())
//                        .collect(Collectors.toList());
//        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorList);
//        return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
//    }
//}
