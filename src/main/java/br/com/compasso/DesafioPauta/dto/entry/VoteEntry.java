package br.com.compasso.DesafioPauta.dto.entry;

import br.com.compasso.DesafioPauta.entity.Agenda;
import br.com.compasso.DesafioPauta.entity.Associated;
import br.com.compasso.DesafioPauta.enumeration.VoteResponse;
import lombok.Data;

@Data
public class VoteEntry {

    private Agenda agenda;
    private Associated associated;
    private VoteResponse response;

}
