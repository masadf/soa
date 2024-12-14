package itmo.spacemarineservice.domain.exceptions;

public class SpaceMarineNotFoundException extends RuntimeException {
    public SpaceMarineNotFoundException(String message) {
        super(message);
    }
}
