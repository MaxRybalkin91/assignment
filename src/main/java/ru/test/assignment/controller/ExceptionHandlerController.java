package ru.test.assignment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.test.assignment.model.rest.ErrorRs;
import ru.test.assignment.model.rest.exception.AssignmentLogicException;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorRs getErrorResponse(Exception ex) {
        log.error("Internal error: {}", ex.getMessage(), ex);
        return new ErrorRs("Unexpected error");
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ErrorRs getConstraintErrorResponse(DataIntegrityViolationException ex) {
        log.error("Unique constraint error: {}", ex.getMessage(), ex);
        return new ErrorRs("Error saving to DB: the entity might have already exist");
    }

    @ExceptionHandler
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ErrorRs getConstraintErrorResponse(AssignmentLogicException ex) {
        log.error("Logical error: {}", ex.getMessage(), ex);
        return new ErrorRs(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ErrorRs errors(BindException ex) {
        log.warn("Validation error: {}", ex.getMessage(), ex);
        final List<String> fieldErrors = getFields(ex);
        return new ErrorRs(String.format("Incorrect fields values: %s",
                fieldErrors.size() > 1 ? String.join(", ", fieldErrors) : fieldErrors.get(0)));
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ErrorRs errors(EmptyResultDataAccessException ex) {
        log.warn("No such entity in db", ex);
        return new ErrorRs("The entity wasn't found in DB");
    }

    private List<String> getFields(BindException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getField)
                .sorted()
                .collect(toList());
    }
}
