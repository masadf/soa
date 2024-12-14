package itmo.spacemarineservice.domain.exceptions;

public class SortParamsInvalidException extends RuntimeException {
    public SortParamsInvalidException(String message) {
        super(message);
    }
}
