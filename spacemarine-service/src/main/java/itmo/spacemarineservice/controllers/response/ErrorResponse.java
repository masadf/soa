package itmo.spacemarineservice.controllers.response;

public record ErrorResponse(
        String message,
        String messageCode
) {
}
