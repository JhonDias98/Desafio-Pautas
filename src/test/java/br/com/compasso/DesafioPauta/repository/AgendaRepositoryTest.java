package br.com.compasso.DesafioPauta.repository;

import br.com.compasso.DesafioPauta.entity.Agenda;
import br.com.compasso.DesafioPauta.enumeration.AgendaStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class AgendaRepositoryTest {

    @Autowired
    private AgendaRepository agendaRepository;

    @Test
    void getAgendasWithStatusOPEN() {
        var OPEN = agendaRepository.getAgendasWithStatus(AgendaStatus.OPEN);
        assertTrue(OPEN.size() == 2);
    }

    @Test
    void getAgendasWithStatusCLOSE() {
        var CLOSE = agendaRepository.getAgendasWithStatus(AgendaStatus.CLOSE);
        assertTrue(CLOSE.size() == 1);
    }

    @BeforeAll
    private static void initMongo(@Autowired AgendaRepository preTestsAgendaRepository) {
        var agenda = generateAgenda(AgendaStatus.OPEN);
        var agenda2 = generateAgenda(AgendaStatus.OPEN);
        var agenda3 = generateAgenda(AgendaStatus.CLOSE);

        preTestsAgendaRepository.save(agenda);
        preTestsAgendaRepository.save(agenda2);
        preTestsAgendaRepository.save(agenda3);
    }

    private static Agenda generateAgenda(AgendaStatus status) {
        return Agenda.builder()
                .id(null)
                .title("title test")
                .description("description test")
                .status(status)
                .begin(LocalDateTime.now())
                .end(LocalDateTime.now().plusMinutes(1L))
                .amountYes(0)
                .amountNo(0)
                .build();
    }
}