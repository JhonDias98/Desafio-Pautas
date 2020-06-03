package br.com.compasso.DesafioPauta.service;

import br.com.compasso.DesafioPauta.entity.Agenda;

import java.util.List;

public interface AgendaService {

    List<Agenda> list();
    Agenda find(String id);
    Agenda register(Agenda agenda);
    void delete(String id);
    void update(Agenda agenda);

}
