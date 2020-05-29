package br.com.compasso.DesafioPauta.service.impl;

import br.com.compasso.DesafioPauta.entity.Agenda;
import br.com.compasso.DesafioPauta.repository.AgendaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AgendaServiceImplTest {

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private AgendaServiceImpl agendaService;


    @Test
    public void testListAllAgendas() {

        var agenda = generateAgenda();
        List<Agenda> agendas = Arrays.asList(agenda);
        when(agendaRepository.findAll()).thenReturn(agendas);
        var agendasMock = agendaService.list();
        assertEquals(agendas, agendasMock);
        verify(agendaRepository).findAll();

    }

    @Test
    public void testFindAgenda() {
        var agenda = generateAgenda();
        Optional<Agenda> agendaOptional = Optional.ofNullable(agenda);
        when(agendaRepository.findById(eq("1"))).thenReturn(agendaOptional);
        Agenda agendaMock = agendaService.find("1");
        assertEquals(agenda, agendaMock);
        verify(agendaRepository).findById("1");
    }

    @Test
    public void throwsExceptionWhenFindAgenda() {
        assertThrows(IllegalArgumentException.class, () -> {
            agendaService.find("*");
        });
    }

    @Test
    public void registerTest() {
        var agenda = generateAgenda();
        when(agendaRepository.save(agenda)).thenReturn(agenda);
        Agenda register = agendaService.register(agenda);
        assertEquals(register, agenda);
        verify(agendaRepository).save(agenda);
    }

    @Test
    public void deleteTest() {

        doNothing().when(agendaRepository).deleteById("1");
        agendaService.delete("1");
        verify(agendaRepository).deleteById("1");

    }

    private Agenda generateAgenda() {
        return Agenda.builder()
                .id("1")
                .title("title")
                .description("description")
                .build();
    }

}