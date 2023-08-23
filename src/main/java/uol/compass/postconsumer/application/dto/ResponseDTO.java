package uol.compass.postconsumer.application.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseDTO<T>{
    private final LocalDateTime timestamp;
    private final T body;

    public ResponseDTO(T data){
        this.timestamp = LocalDateTime.now();
        this.body = data;
    }

    public static <T> ResponseDTO<T> ok(T data){
        return new ResponseDTO<>(data);
    }
}
