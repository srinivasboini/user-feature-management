package com.moneylion.feature.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global exception handler for application wide exception handling
 *
 * @author srinivas
 * @version 1.0
 * @since 2021-02-06
 */
@ControllerAdvice
@Slf4j
public class AppExceptionHandler {

    /**
     * This exception handler will execute if we receive malformed request.
     * We are responding with HttpStatus.BAD_REQUEST
     * @param -HttpMessageNotReadableException
     * @param -HttpServletRequest
     * @return -ResponseEntity with HttpStatus.BAD_REQUEST and exception as response body.
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(HttpMessageNotReadableException exception, HttpServletRequest request){
        log.error(exception.getLocalizedMessage()) ;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getLocalizedMessage()) ;
    }


    /**
     * Ideally, we should send the response status as HttpStatus.BAD_REQUEST
     * But, In the user story it was written to respond back with HttpStatus.NOT_MODIFIED in case the user feature is not saved.
     * This will get executed if the constraints in UserFeatureRequest fails (in fact for all the constraints validation)
     * @param -MethodArgumentNotValidException
     * @return -ResponseEntity with response status 304.
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            log.error(String.format("%s -> %2s",fieldName,errorMessage));
        });
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(errors) ;
    }

    /**
     * constraints validation
     * @param -ConstraintViolationException
     * @return -ResponseEntity with response status 400.
     */
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleConstraintViolationExceptions(
            ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach((error) -> {
            String value = error.getPropertyPath().toString() ;
            String errorMessage = error.getMessage();
            errors.add(errorMessage);
            log.error(String.format("%s -> %2s",value,errorMessage));
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors) ;
    }

    /**
     * this exception handler will execute if we encounter any error while saving User feature relation
     * @param -InvalidUserFeatureException
     * @return -ResponseEntity with response status 304 .
     */
    @ExceptionHandler({InvalidUserFeatureException.class})
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    public ResponseEntity<?> handleInvalidUserFeatureException(InvalidUserFeatureException exception){
        log.error(exception.getLocalizedMessage()) ;
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(exception.getLocalizedMessage()) ;
    }


    /**
     * this exception handler will execute if we encounter any unknown error
     * @param -Exception
     * @return -ResponseEntity with error message.
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleGenericException(Exception exception){
        log.error(exception.getLocalizedMessage()) ;
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getLocalizedMessage()) ;
    }
}
