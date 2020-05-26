package br.com.compasso.DesafioPauta.dto;

import br.com.compasso.DesafioPauta.entity.Agenda;
import br.com.compasso.DesafioPauta.entity.Associated;
import br.com.compasso.DesafioPauta.entity.Vote;
import lombok.Data;

@Data
public class VoteDto {

    Agenda agenda;
    Associated associated;

    public VoteDto(Vote vote) {
        this.agenda = vote.getAgenda();
        this.associated = vote.getAssociated();
    }

}
