package itmo.utils.dto;

public record ErrorResponse(
        String message,
        String messageCode
) {
}
