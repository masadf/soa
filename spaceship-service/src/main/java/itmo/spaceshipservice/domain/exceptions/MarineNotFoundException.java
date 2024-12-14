package itmo.spaceshipservice.domain.exceptions;

public class MarineNotFoundException extends RuntimeException {
    public MarineNotFoundException(String message) {
        super(message);
    }
}
