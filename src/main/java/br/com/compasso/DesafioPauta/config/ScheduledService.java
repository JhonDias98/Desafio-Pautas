package br.com.compasso.DesafioPauta.config;

import br.com.compasso.DesafioPauta.entity.Agenda;
import br.com.compasso.DesafioPauta.enumeration.AgendaStatus;
import br.com.compasso.DesafioPauta.service.impl.AgendaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
public class ScheduledService {

    @Autowired
    private AgendaServiceImpl agendaService;

    @Scheduled(fixedDelay = 10000)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void verify() {
        List<Agenda> allAgendas = agendaService.list();

        allAgendas.forEach(agenda -> {
            if(verifyStatus(agenda)){
                agenda.setStatus(AgendaStatus.CLOSE);
                agendaService.update(agenda);
            }
        });
    }

    private boolean verifyStatus(Agenda agenda) {
        return agenda.getEnd().isBefore(LocalDateTime.now()) && agenda.getStatus().equals(AgendaStatus.OPEN);
    }
}
