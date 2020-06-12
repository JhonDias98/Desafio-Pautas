package br.com.compasso.DesafioPauta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailInUseException extends RuntimeException {

    public EmailInUseException() {
        super("Email is already in use!");
    }
}
