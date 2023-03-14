package com.practice.exeption;

import com.practice.entity.ApiError;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> handlerStudentNotFound( ResourceNotFound ex) {
        ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND,
                "Resource Not Found", ex.getMessage());
        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(ResourceBadRequest.class)
    public ResponseEntity<?> handlerStudentBadRequest( ResourceBadRequest ex){
        Map<String, String> details = new HashMap<>();
                ex.getBindingResult().getFieldErrors()
                        .forEach(e -> {
                            details.put(e.getField(),"El campo '" + e.getField() + "' " + e.getDefaultMessage());

                        });

        ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST
        ,"Resource Bad Request", details);
        return ResponseEntityBuilder.build(err);

    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handlerDataAccessException(DataAccessException ex) {
        ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR,
                "Data exception", ex.getMessage());
        return ResponseEntityBuilder.build(err);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException ( NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request ) {
        ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND,
                "Endpoint no encontrado", ex.getMessage());
        return ResponseEntityBuilder.build(err);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported ( HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request  ) {
        ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.METHOD_NOT_ALLOWED,
                "Metodo no soportado", ex.getMethod());
        return ResponseEntityBuilder.build(err);
    }




}
