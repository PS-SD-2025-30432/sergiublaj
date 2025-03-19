package en.sd.chefmgmt.exception;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionBody handleValidationErrors(MethodArgumentNotValidException exception) {
        Map<String, String> errors = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> Objects.requireNonNullElse(error.getDefaultMessage(), "Invalid value"),
                        (existing, replacement) -> existing
                ));

        return ExceptionBody.builder()
                .timestamp(ZonedDateTime.now())
                .code(ExceptionCode.VALIDATION_ERROR.getCode())
                .message("Validation failed")
                .details(errors)
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionBody handleConstraintViolation(ConstraintViolationException exception) {
        return ExceptionBody.builder()
                .timestamp(ZonedDateTime.now())
                .code(ExceptionCode.CONSTRAINT_VIOLATION.getCode())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionBody handleDataNotFound(DataNotFoundException exception) {
        return ExceptionBody.builder()
                .timestamp(ZonedDateTime.now())
                .code(exception.getCode())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(DuplicateDataException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ExceptionBody handleDuplicateData(DuplicateDataException exception) {
        return ExceptionBody.builder()
                .timestamp(ZonedDateTime.now())
                .code(exception.getCode())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleGlobalException(Exception exception) {
        return ExceptionBody.builder()
                .timestamp(ZonedDateTime.now())
                .code(ExceptionCode.SERVER_ERROR.getCode())
                .message(exception.getMessage())
                .build();
    }
}
