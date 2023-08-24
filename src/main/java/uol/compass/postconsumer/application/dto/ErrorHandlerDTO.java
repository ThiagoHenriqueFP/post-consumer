package uol.compass.postconsumer.application.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorHandlerDTO(
        LocalDateTime timestamp,
        List<String> data
) {
    public ErrorHandlerDTO(List<String> data) {
        this(LocalDateTime.now(), data);
    }
}
