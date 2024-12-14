package itmo.spacemarineservice.domain.exceptions;

public class UnknownFieldException extends RuntimeException {
    public UnknownFieldException(String message) {
        super(message);
    }
}
