package br.com.compasso.DesafioPauta.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class Handler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<EntryError> handle(MethodArgumentNotValidException e) {
        List<EntryError> errors = new ArrayList<>();

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        fieldErrors.forEach(error -> {
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            errors.add(new EntryError(error.getField(), message));
        });
        return errors;
    }

    @ExceptionHandler(EmailInUseException.class)
    public ResponseEntity handleEmailInUse(EmailInUseException e) {
        return new ResponseEntity(new EntryError("Email in use!", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
