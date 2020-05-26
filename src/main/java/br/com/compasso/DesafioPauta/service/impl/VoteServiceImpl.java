package br.com.compasso.DesafioPauta.service.impl;

import br.com.compasso.DesafioPauta.entity.Agenda;
import br.com.compasso.DesafioPauta.entity.Associated;
import br.com.compasso.DesafioPauta.entity.Vote;
import br.com.compasso.DesafioPauta.enumeration.AgendaStatus;
import br.com.compasso.DesafioPauta.repository.AgendaRepository;
import br.com.compasso.DesafioPauta.repository.VoteRepository;
import br.com.compasso.DesafioPauta.service.VoteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final AgendaServiceImpl agendaService;
    private final AssociatedServiceImpl associatedService;

    public VoteServiceImpl(VoteRepository voteRepository, AgendaRepository agendaRepository, AgendaServiceImpl agendaService, AssociatedServiceImpl associatedService) {
        this.voteRepository = voteRepository;
        this.agendaService = agendaService;
        this.associatedService = associatedService;
    }

    @Override
    public List<Vote> list() {
        return voteRepository.findAll();
    }

    @Override
    public Vote register(Vote vote) {

        Agenda agenda = agendaService.find(vote.getAgenda().getId());
        Associated associated = associatedService.find(vote.getAssociated().getId());

        if (agenda.getStatus().equals(AgendaStatus.OPEN) && !verifyVoteAssociate(this.list(), agenda, associated)) {

            vote.setAgenda(agenda);
            agenda.voteIn(vote.getResponse());
            agendaService.update(vote.getAgenda());

            vote.setAssociated(associated);

            return voteRepository.save(vote);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean verifyVoteAssociate(List<Vote> list, Agenda agenda, Associated associated) {

        return list.stream().anyMatch(vote -> vote.getAgenda().getId().equals(agenda.getId()) && vote.getAssociated().getId().equals(associated.getId()));
    }
}
