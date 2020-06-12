package br.com.compasso.DesafioPauta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociatedDto extends RepresentationModel<AssociatedDto> {

    private String name;
    private String email;

}
