package mate.academy.mybookshop.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String TIMESTAMP = "timestamp";
    public static final String STATUS = "status";
    public static final String ERRORS = "errors";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST);
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessage)
                .toList();
        body.put(ERRORS, errors);
        return new ResponseEntity<>(body, headers, status);
    }

    private String getErrorMessage(ObjectError e) {
        if (e instanceof FieldError) {
            String field = ((FieldError) e).getField();
            String message = e.getDefaultMessage();
            return field + " " + message;
        }
        return e.getDefaultMessage();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(EntityNotFoundException ex) {
        return getResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({RegistrationException.class, EmptyCartException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            Exception ex) {
        return getResponseEntity(HttpStatus.CONFLICT, ex.getMessage());
    }

    private ResponseEntity<Object> getResponseEntity(HttpStatus status, Object error) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        Map<String, Object> detail = new LinkedHashMap<>();
        detail.put("error", error);
        detail.put("timestamp", LocalDateTime.now().toString());
        problemDetail.setProperties(detail);
        return ResponseEntity.of(problemDetail).build();
    }
}
