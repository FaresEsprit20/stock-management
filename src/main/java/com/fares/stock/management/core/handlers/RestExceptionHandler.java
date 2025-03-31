package com.fares.stock.management.core.handlers;


import com.fares.stock.management.core.exception.CustomErrorMsg;
import com.fares.stock.management.core.exception.EntityNotFoundException;
import com.fares.stock.management.core.exception.InvalidEntityException;
import com.fares.stock.management.core.exception.InvalidOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomErrorMsg> handleException(EntityNotFoundException exception, WebRequest webRequest) {

        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final CustomErrorMsg errorDto = new CustomErrorMsg();
                errorDto.setCode(exception.getErrorCode());
                errorDto.setHttpCode(notFound.value());
                errorDto.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorDto, notFound);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<CustomErrorMsg> handleException(InvalidOperationException exception, WebRequest webRequest) {

        final HttpStatus notFound = HttpStatus.BAD_REQUEST;
        final CustomErrorMsg errorDto = new CustomErrorMsg();
        errorDto.setCode(exception.getErrorCode());
        errorDto.setHttpCode(notFound.value());
        errorDto.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorDto, notFound);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<CustomErrorMsg> handleException(InvalidEntityException exception, WebRequest webRequest) {

        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        final CustomErrorMsg errorDto = new CustomErrorMsg();
        errorDto.setCode(exception.getErrorCode());
        errorDto.setHttpCode(badRequest.value());
        errorDto.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorDto, badRequest);
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ErrorDto> handleException(BadCredentialsException exception, WebRequest webRequest) {
//        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
//
//        final ErrorDto errorDto = ErrorDto.builder()
//                .code(ErrorCodes.BAD_CREDENTIALS)
//                .httpCode(badRequest.value())
//                .message(exception.getMessage())
//                .errors(Collections.singletonList("Login or / Password is incorrect "))
//                .build();
//
//        return new ResponseEntity<>(errorDto, badRequest);
//    }


}
