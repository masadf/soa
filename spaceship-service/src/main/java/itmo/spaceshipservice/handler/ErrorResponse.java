package itmo.spaceshipservice.handler;

public record ErrorResponse(
        String message,
        String messageCode
) {
}
