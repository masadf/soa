package itmo.spacemarineservice.domain.exceptions;

public class FilterParamsInvalidException extends RuntimeException {
    public FilterParamsInvalidException(String message) {
        super(message);
    }
}
