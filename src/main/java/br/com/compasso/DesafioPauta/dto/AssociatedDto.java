package br.com.compasso.DesafioPauta.dto;

import br.com.compasso.DesafioPauta.entity.Associated;
import lombok.Data;

@Data
public class AssociatedDto {

    private String name;
    private String email;

    public AssociatedDto(Associated associated) {
        this.name = associated.getName();
        this.email = associated.getEmail();
    }

}
