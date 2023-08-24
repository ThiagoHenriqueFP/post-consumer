package uol.compass.postconsumer.application.post.dto;

public record PostResponseDTO<T>(
        Integer pageSize,
        Integer pageNumber,
        Integer pages,
        Integer elements,
        Boolean isLast,
        T data
) { }
