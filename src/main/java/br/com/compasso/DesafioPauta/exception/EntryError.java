package br.com.compasso.DesafioPauta.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EntryError {

    private String message;
    private String description;
}
