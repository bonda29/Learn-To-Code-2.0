package com.example.learntocode.errors;

import com.example.learntocode.payload.messages.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.logging.Logger;

@RestControllerAdvice
public class GlobalErrorHandler {

    private final Logger logger = Logger.getLogger(GlobalErrorHandler.class.getName());

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorMessage handleNotFound(final HttpServletRequest request, final Exception error) {
        logger.severe(error.getMessage() + " " + request.getRequestURI() + " " + request.getMethod());
        return ErrorMessage.from("Not Found");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorMessage handleNotFoundException(final HttpServletRequest request, final Exception error) {
        logger.warning(error.getMessage() + " " + request.getRequestURI() + " " + request.getMethod());
        return ErrorMessage.from(error.getMessage(), "", "404", request.getRequestURI());

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorMessage handleResourceNotFoundException(final HttpServletRequest request, final Exception error) {
        logger.warning(error.getMessage() + " " + request.getRequestURI() + " " + request.getMethod());
        return ErrorMessage.from(error.getMessage(), "", "404", request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorMessage handleIllegalArgumentException(final HttpServletRequest request, final Exception error) {
        logger.warning(error.getMessage() + " " + request.getRequestURI() + " " + request.getMethod());
        return ErrorMessage.from(error.getMessage(), "", "400", request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidReplyException.class)
    public ErrorMessage handleInvalidReplyException(final HttpServletRequest request, final Exception error) {
        logger.warning(error.getMessage() + " " + request.getRequestURI() + " " + request.getMethod());
        return ErrorMessage.from(error.getMessage(), "", "400", request.getRequestURI());
    }


/*    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorMessage handleInternalError(final HttpServletRequest request, final Exception error) {
        return ErrorMessage.from(error.getMessage());
    }*/


}
