package itmo.spacemarineservice.domain.exceptions;

public class SpaceMarineValidationException extends RuntimeException {
    public SpaceMarineValidationException(String message) {
        super(message);
    }
}
