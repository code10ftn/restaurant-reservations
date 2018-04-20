package com.code10.isa.controller.exception.resolver;

import com.code10.isa.controller.exception.AuthorizationException;
import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.ForbiddenException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.dto.ErrorDto;
import org.hibernate.StaleStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity authorizationException(AuthorizationException exception) {
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity badRequestException(BadRequestException exception) {
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity forbiddenException(ForbiddenException exception) {
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFoundException(NotFoundException exception) {
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity classCastException(ClassCastException exception) {
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StaleStateException.class)
    public ResponseEntity staleStateException() {
        return new ResponseEntity<>(new ErrorDto("Transaction error, please try again."), HttpStatus.I_AM_A_TEAPOT);
    }
}
