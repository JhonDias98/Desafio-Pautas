package br.com.compasso.DesafioPauta.dto.entry;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AssociatedEntry {

    @NotBlank
    private String name;
    @Email
    private String email;

}
