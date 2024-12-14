package itmo.spaceshipservice.controllers.response;

public record ErrorResponse(
        String message,
        String messageCode
) {
}
