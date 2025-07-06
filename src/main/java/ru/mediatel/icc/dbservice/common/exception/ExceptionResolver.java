package ru.mediatel.icc.dbservice.common.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.SQLStateClass;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mediatel.icc.dbservice.common.response.ErrorApiResponse;

import java.util.Locale;
import java.util.regex.Pattern;

@ControllerAdvice
public class ExceptionResolver extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    private static final Pattern DTO_NAME_PATTERN = Pattern.compile("fulldto$|dto$", Pattern.CASE_INSENSITIVE);

    public ExceptionResolver(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> resolveException(Exception ex, WebRequest request) {
        if (ex instanceof PlainException e) {
            return resolvePlainException(e, request);
        }
        if (ex instanceof DataAccessException e) {
            return resolveDataAccessException(e, request);
        }
        if (ex instanceof IllegalArgumentException e) {
            return resolveIllegalArgumentException(e, request);
        }
        if (ex instanceof ObjectNotFoundException e) {
            return resolveObjectNotFoundException(e, request);
        }
        if (ex instanceof DataConsistencyException e) {
            return resolveDataConsistencyError(e, request);
        }
        if (ex instanceof LoginBlockedException e) {
            return resolveLoginBlockedException(e, request);
        }
        if (ex instanceof DomainException e) {
            return resolveDomainExceptionError(e, request);
        }

        return resolveUnknownException(ex, request);
    }


    private ResponseEntity<Object> resolveUnknownException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        logger.error("Internal server error", ex);
        return handleExceptionInternal(ex, new ErrorApiResponse(status.value(), "Unprocessed internal server error"), new HttpHeaders(), status, request);
    }

    private ResponseEntity<?> resolvePlainException(PlainException ex, WebRequest request) {
        logger.info("Plain exception " + ex.getMessage());
        return handleExceptionInternal(ex, new ErrorApiResponse(ex.getStatus().value(), ex.getMessage()), new HttpHeaders(), ex.getStatus(), request);
    }

    private ResponseEntity<?> resolveDataAccessException(DataAccessException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        SQLStateClass sqlStateClass = ex.sqlStateClass();
        logger.info(ex.getMessage());
        return handleExceptionInternal(ex, new ErrorApiResponse(status.value(), "Data access error"), new HttpHeaders(), status, request);
    }

    private ResponseEntity<?> resolveIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        logger.info(ex.getMessage());
        return handleExceptionInternal(ex, new ErrorApiResponse(status.value(), ex.getMessage()), new HttpHeaders(), status, request);
    }

    private ResponseEntity<?> resolveObjectNotFoundException(ObjectNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        logger.info(ex.getMessage());
        return handleExceptionInternal(ex, new ErrorApiResponse(status.value(), ex.getMessage()), new HttpHeaders(), status, request);
    }

    private ResponseEntity<?> resolveDataConsistencyError(DataConsistencyException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        logger.info(ex.getMessage());
        return handleExceptionInternal(ex, new ErrorApiResponse(status.value(), ex.getMessage()), new HttpHeaders(), status, request);
    }

    private ResponseEntity<?> resolveDomainExceptionError(DomainException ex, WebRequest request) {
        logger.info(ex.getMessage());
        return handleExceptionInternal(ex, new ErrorApiResponse(ex.getStatus(), ex.getMessage()), new HttpHeaders(), HttpStatusCode.valueOf(ex.getStatus()), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        for (var error : ex.getAllErrors()) {
            if (error instanceof FieldError fieldError) {
                builder.append(fieldError.getField()).append(" ").append(fieldError.getDefaultMessage()).append("\n");
            } else {
                builder.append(error).append("\n");
            }
        }
        String body = builder.length() > 0 ? builder.toString() : ex.getMessage();
        return handleExceptionInternal(ex, new ErrorApiResponse(status.value(), body), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (ex.getCause() != null) {
            if (ex.getCause() instanceof UnrecognizedPropertyException e) {
                return resolveUnrecognizedPropertyException(e, request);
            }
            if (ex.getCause() instanceof InvalidFormatException e) {
                return resolveInvalidFormatException(e, request);
            }
            if (ex.getCause() instanceof JsonMappingException e) {
                return resolveJsonMappingException(e, request);
            }
        }
        return resolveUnknownException(ex, request);
    }

    private ResponseEntity<Object> resolveUnrecognizedPropertyException(UnrecognizedPropertyException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Locale locale = request.getLocale();
        String message = messageSource.getMessage("error.unrecognizedProperty", new Object[]{ex.getPropertyName()}, locale);
        return handleExceptionInternal(ex, new ErrorApiResponse(status.value(), message), new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> resolveLoginBlockedException(LoginBlockedException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(ex, new ErrorApiResponse(status.value(), "LOGIN_IS_NOT_ALLOWED"), new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> resolveInvalidFormatException(InvalidFormatException ex, WebRequest request) {
        return resolveClassFormatException(ex, ex.getTargetType(), request);
    }

    private ResponseEntity<Object> resolveJsonMappingException(JsonMappingException ex, WebRequest request) {
        return resolveClassFormatException(ex, null, request);
    }

    private ResponseEntity<Object> resolveClassFormatException(JsonMappingException ex, Class<?> targetClass, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorApiResponse(400, "Invalid value for " + targetClass.getSimpleName()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }
}
