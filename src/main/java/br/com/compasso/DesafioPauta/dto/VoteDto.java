package br.com.compasso.DesafioPauta.dto;

import br.com.compasso.DesafioPauta.entity.Agenda;
import br.com.compasso.DesafioPauta.entity.Associated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {

    private Agenda agenda;
    private Associated associated;

}
