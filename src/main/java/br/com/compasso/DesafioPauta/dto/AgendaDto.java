package br.com.compasso.DesafioPauta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendaDto extends RepresentationModel<AgendaDto> {

    private String title;
    private String description;
    private String status;

}
