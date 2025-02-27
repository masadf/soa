package itmo.service.handler;


import itmo.utils.dto.ErrorResponse;
import itmo.utils.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({FilterParamsInvalidException.class})
    public ResponseEntity<ErrorResponse> handleException(FilterParamsInvalidException exception) {
        return ResponseEntity.status(422).body(new ErrorResponse(exception.getMessage(), "filter.param.invalid"));
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({SortParamsInvalidException.class})
    public ResponseEntity<ErrorResponse> handleException(SortParamsInvalidException exception) {
        return ResponseEntity.status(422).body(new ErrorResponse(exception.getMessage(), "sort.param.invalid"));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({SpaceMarineNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleException(SpaceMarineNotFoundException exception) {
        return ResponseEntity.status(404).body(new ErrorResponse(exception.getMessage(), "not.found.marine"));
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({SpaceMarineValidationException.class})
    public ResponseEntity<ErrorResponse> handleException(SpaceMarineValidationException exception) {
        return ResponseEntity.status(422).body(new ErrorResponse(exception.getMessage(), "marine.invalid"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({RequestParamInvalidException.class})
    public ResponseEntity<ErrorResponse> handleException(RequestParamInvalidException exception) {
        return ResponseEntity.status(400).body(new ErrorResponse(exception.getMessage(), "request.invalid"));
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({UnknownFieldException.class})
    public ResponseEntity<ErrorResponse> handleException(UnknownFieldException exception) {
        return ResponseEntity.status(422).body(new ErrorResponse(exception.getMessage(), "field.invalid"));
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ErrorResponse> handle(NoHandlerFoundException exception) {
        return switch (exception.getBody().getStatus()) {
            case 404 -> ResponseEntity.status(404).body(new ErrorResponse("Неизвестный путь", "unknown.path"));
            case 400 ->
                    ResponseEntity.status(400).body(new ErrorResponse("Некорректные входящие параметры", "incorrect.inputParams"));
            default -> ResponseEntity.status(500).body(new ErrorResponse("Внутренняя ошибка", "internal.error"));
        };
    }


    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<ErrorResponse> handle(HttpClientErrorException exception) {
        return ResponseEntity.status(400).body(new ErrorResponse("Некорректные входящие параметры", "incorrect.inputParams"));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(400).body(new ErrorResponse("Некорректные входящие параметры", "incorrect.inputParams"));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(400).body(new ErrorResponse("Некорректные входящие параметры", "incorrect.inputParams"));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handle(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity.status(400).body(new ErrorResponse("Некорректные входящие параметры", "incorrect.inputParams"));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse(e.toString(), "internal.error"));
    }
}
