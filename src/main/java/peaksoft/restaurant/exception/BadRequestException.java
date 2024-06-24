package peaksoft.restaurant.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }

    @Builder
    @Getter
    public static class ExceptionResponse {
        private HttpStatus httpStatus;
        private String message;
        private String exceptionClass;
    }
}