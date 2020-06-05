package br.com.compasso.DesafioPauta.entity;

import br.com.compasso.DesafioPauta.dto.entry.AgendaEntry;
import br.com.compasso.DesafioPauta.enumeration.AgendaStatus;
import br.com.compasso.DesafioPauta.enumeration.VoteResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Agenda {

    @Id
    private String id;
    private String title;
    private String description;
    private LocalDateTime begin = LocalDateTime.now();
    private LocalDateTime end = begin.plusMinutes(1);
    private int amountYes = 0;
    private int amountNo = 0;
    private AgendaStatus status = AgendaStatus.OPEN;

    public Agenda(AgendaEntry entry) {
        this.title = entry.getTitle();
        this.description = entry.getDescription();

        entry.getDuration().ifPresent(integer -> this.end = begin.plusMinutes(integer));
    }

    public void voteIn(VoteResponse vote) {
        if (vote.equals(VoteResponse.YES))
            amountYes++;
        else
            amountNo++;
    }
}
