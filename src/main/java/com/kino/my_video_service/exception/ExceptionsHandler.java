package com.kino.my_video_service.exception;

import com.kino.my_video_service.dto.ExceptionHandlerResponse;
import com.kino.my_video_service.exception.movie.MovieNotFoundException;
import com.kino.my_video_service.exception.subscription.CostPlanNotFoundException;
import com.kino.my_video_service.exception.subscription.SubscriptionAlreadyExistException;
import com.kino.my_video_service.exception.user.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class ExceptionsHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionHandlerResponse> userNotFound(
            UserNotFoundException exception,
            HttpServletRequest httpServletRequest
    ){
        return toResponse(HttpStatus.NOT_FOUND, exception.getMessage(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(FailedAuthenticationException.class)
    public ResponseEntity<ExceptionHandlerResponse> failedAuthentication(
            FailedAuthenticationException exception,
            HttpServletRequest httpServletRequest
    ){
        return toResponse(HttpStatus.UNAUTHORIZED, exception.getMessage(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(LoginAlreadyTakenException.class)
    public ResponseEntity<ExceptionHandlerResponse> loginAlreadyTaken(
            LoginAlreadyTakenException exception,
            HttpServletRequest httpServletRequest
    ){
        return toResponse(HttpStatus.CONFLICT, exception.getMessage(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(SameLoginException.class)
    public ResponseEntity<ExceptionHandlerResponse> sameLogin(
            SameLoginException exception,
            HttpServletRequest httpServletRequest
    ){
        return toResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ExceptionHandlerResponse> wrongPassword(
            WrongPasswordException exception,
            HttpServletRequest httpServletRequest
    ){
        return toResponse(HttpStatus.FORBIDDEN, exception.getMessage(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionHandlerResponse> failedValidate(HttpServletRequest httpServletRequest){
        return toResponse(HttpStatus.BAD_REQUEST, "Failed validation", httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionHandlerResponse> dataConflict(
            DataIntegrityViolationException exception,
            HttpServletRequest httpServletRequest
    ){
        log.warn("Data conflict: {}", exception.getMostSpecificCause().getMessage());
        return toResponse(HttpStatus.CONFLICT, "Data conflict", httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ExceptionHandlerResponse> movieNotFound(
            MovieNotFoundException exception,
            HttpServletRequest httpServletRequest
    ){
        return toResponse(HttpStatus.NOT_FOUND, exception.getMessage(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(CostPlanNotFoundException.class)
    public ResponseEntity<ExceptionHandlerResponse> costPlanNotFound(
            CostPlanNotFoundException exception,
            HttpServletRequest httpServletRequest
    ){
        return toResponse(HttpStatus.NOT_FOUND, exception.getMessage(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(SubscriptionAlreadyExistException.class)
    public ResponseEntity<ExceptionHandlerResponse> subscriptionAlreadyExist(
            SubscriptionAlreadyExistException exception,
            HttpServletRequest httpServletRequest
    ){
        return toResponse(HttpStatus.CONFLICT, exception.getMessage(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionHandlerResponse> invalidPathVariable(HttpServletRequest httpServletRequest){
        return toResponse(HttpStatus.BAD_REQUEST, "Invalid path variable", httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionHandlerResponse> invalidBody(HttpServletRequest httpServletRequest){
        return toResponse(HttpStatus.BAD_REQUEST, "Invalid request body", httpServletRequest.getRequestURI());
    }

    private ResponseEntity<ExceptionHandlerResponse> toResponse(
            HttpStatus httpStatus, String message, String requestURI
    ){
        return ResponseEntity.status(httpStatus).body(toExceptionHandlerResponse(httpStatus, message, requestURI));
    }

    private ExceptionHandlerResponse toExceptionHandlerResponse(
            HttpStatus httpStatus, String message, String requestURI
    ){
        return new ExceptionHandlerResponse(
                ZonedDateTime.now(),
                httpStatus.value(),
                message,
                requestURI
        );
    }

}
