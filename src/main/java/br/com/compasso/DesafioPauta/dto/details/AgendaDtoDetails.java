package br.com.compasso.DesafioPauta.dto.details;

import br.com.compasso.DesafioPauta.enumeration.AgendaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendaDtoDetails {

    private String title;
    private String description;
    private int amountYes;
    private int amountNo;
    private AgendaStatus status;

}
