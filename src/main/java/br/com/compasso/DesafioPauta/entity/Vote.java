package br.com.compasso.DesafioPauta.entity;

import br.com.compasso.DesafioPauta.dto.entry.VoteEntry;
import br.com.compasso.DesafioPauta.enumeration.VoteResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class Vote {

    @Id
    private String id;
    private Agenda agenda;
    private Associated associated;
    private VoteResponse response;


    public Vote(VoteEntry entry) {
        this.agenda = entry.getAgenda();
        this.associated = entry.getAssociated();
        this.response = entry.getResponse();
    }
}
