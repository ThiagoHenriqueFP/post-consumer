package uol.compass.postconsumer.infrastructure.errorHandling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.postconsumer.application.dto.ErrorHandlerDTO;

import java.util.List;
import java.util.Objects;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<Object> handlerResponseStatusException(
            ResponseStatusException ex
    ){
        ErrorHandlerDTO response = new ErrorHandlerDTO(List.of(Objects.requireNonNull(ex.getReason())));
        return new ResponseEntity<>(response, ex.getStatusCode());
    }
}
