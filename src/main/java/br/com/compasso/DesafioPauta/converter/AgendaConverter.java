package br.com.compasso.DesafioPauta.converter;

import br.com.compasso.DesafioPauta.dto.AgendaDto;
import br.com.compasso.DesafioPauta.dto.details.AgendaDtoDetails;
import br.com.compasso.DesafioPauta.dto.entry.AgendaEntry;
import br.com.compasso.DesafioPauta.entity.Agenda;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.stereotype.Component;

@Component
public class AgendaConverter {

    private final EntityLinks entityLinks;
    private final ModelMapper modelMapper;

    public AgendaConverter(EntityLinks entityLinks, ModelMapper modelMapper) {
        this.entityLinks = entityLinks;
        this.modelMapper = modelMapper;
    }

    public AgendaDto agendaToAgendaDto(Agenda agenda) {

        AgendaDto agendaDto = modelMapper.map(agenda, AgendaDto.class);
        agendaDto.add(entityLinks.linkToItemResource(Agenda.class, agenda.getId()).withSelfRel());

        return agendaDto;
    }

    public Agenda entryToAgenda(AgendaEntry entry) {

        Agenda agenda = Agenda.builder()
                .title(entry.getTitle())
                .description(entry.getDescription())
                .build();

        entry.getDuration().ifPresent(integer -> {
            agenda.setEnd(agenda.getBegin().plusMinutes(integer));
        });

        return agenda;
        //return new Agenda(entry);
    }

    public AgendaDtoDetails agendaToAgendaDtoDetails(Agenda agenda) {

        AgendaDtoDetails agendaDtoDetails = AgendaDtoDetails.builder()
                .title(agenda.getTitle())
                .description(agenda.getDescription())
                .amountYes(agenda.getAmountYes())
                .amountNo(agenda.getAmountNo())
                .status(agenda.getStatus())
                .build();

        return agendaDtoDetails;

    }
}
