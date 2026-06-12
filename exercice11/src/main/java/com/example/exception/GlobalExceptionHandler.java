package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/** Traduit les exceptions métier en réponses HTTP cohérentes (400 / 404 / 409). */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Données invalides (validation des DTO) → 400 Bad Request. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
    }

    /** Ticket inexistant → 404 Not Found. */
    @ExceptionHandler(TicketNotFoundException.class)
    public ProblemDetail handleNotFound(TicketNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /** Règle métier violée (transition interdite) → 409 Conflict. */
    @ExceptionHandler(InvalidStatusTransitionException.class)
    public ProblemDetail handleConflict(InvalidStatusTransitionException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }
}
