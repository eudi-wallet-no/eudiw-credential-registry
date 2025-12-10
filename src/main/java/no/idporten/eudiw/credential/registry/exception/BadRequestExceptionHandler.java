package no.idporten.eudiw.credential.registry.exception;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class BadRequestExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(BadRequestExceptionHandler.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(500)
                .body(new ErrorResponse(ex.getMessage(), ex.getLocalizedMessage()));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(500)
                .body(new ErrorResponse(ex.getMessage(), ex.getLocalizedMessage()));
    }
}
