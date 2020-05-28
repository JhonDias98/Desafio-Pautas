package br.com.compasso.DesafioPauta.dto.details;

import br.com.compasso.DesafioPauta.entity.Agenda;
import br.com.compasso.DesafioPauta.enumeration.AgendaStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgendaDtoDetails {

    private String title;
    private String description;
    private int amountYes;
    private int amountNo;
    private AgendaStatus status;

    public AgendaDtoDetails(Agenda agenda) {
        this.title = agenda.getTitle();
        this.description = agenda.getDescription();
        this.amountYes = agenda.getAmountYes();
        this.amountNo = agenda.getAmountNo();
        this.status = agenda.getStatus();
    }

}
