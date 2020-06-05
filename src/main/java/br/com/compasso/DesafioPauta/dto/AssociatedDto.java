package br.com.compasso.DesafioPauta.dto;

import br.com.compasso.DesafioPauta.controller.AssociatedController;
import br.com.compasso.DesafioPauta.entity.Associated;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Data
public class AssociatedDto extends RepresentationModel<AssociatedDto> {

    private String name;
    private String email;

    public AssociatedDto(Associated associated) {
        this.name = associated.getName();
        this.email = associated.getEmail();
        this.add(WebMvcLinkBuilder.linkTo(AssociatedController.class).slash(associated.getId()).withSelfRel());
    }

}
