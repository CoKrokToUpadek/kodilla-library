package com.kodillalibrary._resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FailedToFetchDataException.class)
    public ResponseEntity<Object>handleDataNotFoundException(FailedToFetchDataException exception){
        return new ResponseEntity<>("Unable to fetch data from DB. Check requested data correctness", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CorruptedDataException.class)
    public ResponseEntity<Object>handleWrongDataInputException(CorruptedDataException exception){
        return new ResponseEntity<>("Unable to perform action. Data in request invalid", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyListException.class)
    public ResponseEntity<Object>handleEmptyListException(EmptyListException exception){
        return new ResponseEntity<>("Request Data is valid, but no match has been found", HttpStatus.BAD_REQUEST);
    }
}
