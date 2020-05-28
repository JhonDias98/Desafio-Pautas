package br.com.compasso.DesafioPauta.converter;

import br.com.compasso.DesafioPauta.dto.AgendaDto;
import br.com.compasso.DesafioPauta.dto.details.AgendaDtoDetails;
import br.com.compasso.DesafioPauta.dto.entry.AgendaEntry;
import br.com.compasso.DesafioPauta.entity.Agenda;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AgendaConverter {
    public List<AgendaDto> listAgendaToListAgendaDto(List<Agenda> listAgendas) {
        return listAgendas.stream().map(AgendaDto::new).collect(Collectors.toList());
    }

    public AgendaDto agendaToAgendaDto(Agenda agenda) {
        return new AgendaDto(agenda);
    }

    public Agenda entryToAgenda(AgendaEntry entry) {
        return new Agenda(entry);
    }

    public List<AgendaDtoDetails> agendasToAgendasDtoDetails(List<Agenda> list) {
        return list.stream().map(AgendaDtoDetails::new).collect(Collectors.toList());
    }

    public AgendaDtoDetails agendaToAgendaDtoDetails(Agenda agenda) {
        return new AgendaDtoDetails(agenda);
    }
}
