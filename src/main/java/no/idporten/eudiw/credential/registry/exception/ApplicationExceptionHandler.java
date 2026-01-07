package no.idporten.eudiw.credential.registry.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(HttpClientErrorException.class)
    public void handleHttpClientErrorException(HttpClientErrorException e) {
        log.error("http client error exception: {}", e.getMessage(), e);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public void handleHttpServerErrorException(HttpServerErrorException e) {
        log.error("Http server error exception : " + e.getMessage(), e);

    }
}