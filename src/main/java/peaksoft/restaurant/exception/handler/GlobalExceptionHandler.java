package peaksoft.restaurant.exception.handler;

import peaksoft.restaurant.exception.AlreadyException;
import peaksoft.restaurant.exception.BadCredentialException;
import peaksoft.restaurant.exception.BadRequestException;
import peaksoft.restaurant.exception.NotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BadRequestException.ExceptionResponse notFoundException(NotFoundException e) {
        return BadRequestException.ExceptionResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .exceptionClass(e.getClass().getSimpleName())
                .message(e.getMessage())
                .build();

    }

    @ExceptionHandler(AlreadyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BadRequestException.ExceptionResponse alreadyException(AlreadyException e) {
        return BadRequestException.ExceptionResponse.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .exceptionClass(e.getClass().getSimpleName())
                .message(e.getMessage())
                .build();

    }

    @ExceptionHandler(BadCredentialException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BadRequestException.ExceptionResponse badCredentialException(BadCredentialException e) {
        return BadRequestException.ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .exceptionClass(e.getClass().getSimpleName())
                .message(e.getMessage())
                .build();

    }


 @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BadRequestException.ExceptionResponse badRequest(BadRequestException e) {
        return BadRequestException.ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .exceptionClass(e.getClass().getSimpleName())
                .message(e.getMessage())
                .build();

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BadRequestException.ExceptionResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e){
        List<String> errors = e
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return BadRequestException.ExceptionResponse
                .builder()
                .message(errors.toString())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .exceptionClass(e.getClass().getSimpleName())
                .build();
    }
}