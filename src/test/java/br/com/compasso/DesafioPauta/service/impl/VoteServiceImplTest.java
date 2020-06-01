package br.com.compasso.DesafioPauta.service.impl;

import br.com.compasso.DesafioPauta.entity.Agenda;
import br.com.compasso.DesafioPauta.entity.Associated;
import br.com.compasso.DesafioPauta.entity.Vote;
import br.com.compasso.DesafioPauta.enumeration.AgendaStatus;
import br.com.compasso.DesafioPauta.enumeration.VoteResponse;
import br.com.compasso.DesafioPauta.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class VoteServiceImplTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private AgendaServiceImpl agendaService;

    @Mock
    private AssociatedServiceImpl associatedService;

    @InjectMocks
    private VoteServiceImpl voteService;

    @Test
    void listTest() {

        var vote = generateVote();
        var votes = Collections.singletonList(vote);
        when(voteRepository.findAll()).thenReturn(votes);
        var voteList = voteService.list();
        assertEquals(votes, voteList);
        verify(voteRepository).findAll();
    }

    @Test
    void registerTest() {
        var vote = generateVote();

        var vote2 = Vote.builder()
                .id("2")
                .agenda(Agenda.builder().id("2").status(AgendaStatus.OPEN).build())
                .associated(Associated.builder().id("2").build())
                .response(VoteResponse.YES)
                .build();

        when(voteRepository.save(vote)).thenReturn(vote);
        when(agendaService.find(eq(vote.getAgenda().getId()))).thenReturn(vote.getAgenda());
        when(associatedService.find(eq(vote.getAssociated().getId()))).thenReturn(vote.getAssociated());
        when(voteService.list()).thenReturn(Collections.singletonList(vote2));
        Vote register = voteService.register(vote);
        assertEquals(vote, register);
        verify(voteRepository).save(vote);
    }

    @Test
    public void registerFailTest() {

        var vote = generateVote();

        when(voteRepository.save(vote)).thenReturn(vote);
        when(agendaService.find(eq(vote.getAgenda().getId()))).thenReturn(vote.getAgenda());
        when(associatedService.find(eq(vote.getAssociated().getId()))).thenReturn(vote.getAssociated());
        when(voteService.list()).thenReturn(Collections.singletonList(vote));

        assertThrows(IllegalArgumentException.class, () -> voteService.register(vote));

    }

    private Vote generateVote() {

        Agenda agenda = Agenda.builder()
                .id("1")
                .title("title")
                .description("description")
                .status(AgendaStatus.OPEN)
                .begin(LocalDateTime.now())
                .build();

        Associated associated = Associated.builder()
                .id("1")
                .name("Matheus")
                .email("matheus@mail.com")
                .build();

        return Vote.builder()
                .id("1")
                .agenda(agenda)
                .associated(associated)
                .response(VoteResponse.YES)
                .build();
    }
}