package com.kino.my_video_service.exception;

import com.kino.my_video_service.dto.ExceptionHandlerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class ExceptionsHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionHandlerResponse userNotFound(
            UserNotFoundException exception,
            HttpServletRequest httpServletRequest)
    {
        return new ExceptionHandlerResponse(
                ZonedDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                httpServletRequest.getRequestURI()
        );
    }

    @ExceptionHandler(FailedAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionHandlerResponse failedAuthentication(
            FailedAuthenticationException exception,
            HttpServletRequest httpServletRequest)
    {
        return new ExceptionHandlerResponse(
                ZonedDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                exception.getMessage(),
                httpServletRequest.getRequestURI()
        );
    }

    @ExceptionHandler(LoginAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionHandlerResponse loginAlreadyTaken(
            LoginAlreadyTakenException exception,
            HttpServletRequest httpServletRequest)
    {
        return new ExceptionHandlerResponse(
                ZonedDateTime.now(),
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                httpServletRequest.getRequestURI()
        );
    }

    @ExceptionHandler(SameLoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionHandlerResponse sameLogin(
            SameLoginException exception,
            HttpServletRequest httpServletRequest)
    {
        return new ExceptionHandlerResponse(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                httpServletRequest.getRequestURI()
        );
    }

    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionHandlerResponse wrongPassword(
            WrongPasswordException exception,
            HttpServletRequest httpServletRequest)
    {
        return new ExceptionHandlerResponse(
                ZonedDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                exception.getMessage(),
                httpServletRequest.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionHandlerResponse unexpectedError(
            Exception exception,
            HttpServletRequest httpServletRequest
    ) {
        log.error("Server error", exception);
        return new ExceptionHandlerResponse(
                ZonedDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "server error",
                httpServletRequest.getRequestURI()
        );
    }
}
