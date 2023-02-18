package hexlet.code;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ResponseBody
@ControllerAdvice
public class BaseExceptionHandler {

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String generalExceptionHandler(Exception exception) {
        return exception.getMessage();
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String validationExceptionsHandler(MethodArgumentNotValidException exception) {
        List<ObjectError> errors = exception.getAllErrors();
        StringBuilder sb = new StringBuilder();
        for (ObjectError error: errors) {
            sb.append(error.getDefaultMessage()).append("\n");
        }
        return sb.toString();
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String validationExceptionsHandler(DataIntegrityViolationException exception) {
        return exception.getMessage();
    }
}
