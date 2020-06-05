package br.com.compasso.DesafioPauta.dto;

import br.com.compasso.DesafioPauta.controller.AgendaController;
import br.com.compasso.DesafioPauta.entity.Agenda;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Data
public class AgendaDto extends RepresentationModel<AgendaDto> {

    private String title;
    private String description;
    private String status;

    public AgendaDto(Agenda agenda){
        this.title = agenda.getTitle();
        this.description = agenda.getDescription();
        this.status = agenda.getStatus().toString();
        this.add(WebMvcLinkBuilder.linkTo(AgendaController.class).slash(agenda.getId()).withSelfRel());
    }

}
