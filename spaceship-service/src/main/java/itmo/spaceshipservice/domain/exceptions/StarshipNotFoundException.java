package itmo.spaceshipservice.domain.exceptions;

public class StarshipNotFoundException extends RuntimeException {
    public StarshipNotFoundException(String message) {
        super(message);
    }
}
