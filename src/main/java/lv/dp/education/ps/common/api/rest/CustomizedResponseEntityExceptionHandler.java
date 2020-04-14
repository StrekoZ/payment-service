package lv.dp.education.ps.common.api.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@RestController
@Slf4j
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ErrorRestModel> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorRestModel("IllegalArgumentException: " + e.getMessage(), null),
                BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorRestModel> handleAccessException(AccessDeniedException e, WebRequest request) {
        if (request.getUserPrincipal() == null) {
            return new ResponseEntity<>(
                    new ErrorRestModel("Please use Basic authentication", null),
                    UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(
                    new ErrorRestModel("You don't have necessary access", null),
                    FORBIDDEN);
        }
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //todo more detailed parsing
        return new ResponseEntity<>(
                new ErrorRestModel(
                        "Validation failed for argument",
                        e.getBindingResult().getAllErrors().stream().map(ObjectError::toString).collect(Collectors.toList())
                ),
                BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //todo more detailed parsing
        return new ResponseEntity<>(
                new ErrorRestModel(
                        e.getMessage(),
                        null
                ),
                BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorRestModel> handleUnrecognizedException(Exception e, WebRequest request) {
        logger.error("Unrecognized exception while service call", e);
        return new ResponseEntity<>(
                new ErrorRestModel(
                        "Unrecognized exception while service call: " + e.getMessage(),
                        null
                ),
                INTERNAL_SERVER_ERROR);
    }
}