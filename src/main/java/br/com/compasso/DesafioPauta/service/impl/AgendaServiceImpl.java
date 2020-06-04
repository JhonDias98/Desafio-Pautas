package br.com.compasso.DesafioPauta.service.impl;

import br.com.compasso.DesafioPauta.entity.Agenda;
import br.com.compasso.DesafioPauta.enumeration.AgendaStatus;
import br.com.compasso.DesafioPauta.repository.AgendaRepository;
import br.com.compasso.DesafioPauta.service.AgendaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaServiceImpl implements AgendaService {

    private final AgendaRepository agendaRepository;

    public AgendaServiceImpl(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    @Override
    public List<Agenda> list() {
        return agendaRepository.findAll();
    }

    @Override
    public Agenda find(String id) {
        return agendaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }

    @Override
    public Agenda register(Agenda agenda) {
        return agendaRepository.save(agenda);
    }

    @Override
    public void delete(String id) {
        agendaRepository.deleteById(id);
    }

    @Override
    public void update(Agenda agenda) {
        agendaRepository.save(agenda);
    }

    public List<Agenda> listAgendasByStatus(AgendaStatus status) {
        return agendaRepository.getAgendasWithStatus(status);
    }
}
