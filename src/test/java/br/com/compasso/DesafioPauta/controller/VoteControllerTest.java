package br.com.compasso.DesafioPauta.controller;

import br.com.compasso.DesafioPauta.converter.VoteConverter;
import br.com.compasso.DesafioPauta.dto.VoteDto;
import br.com.compasso.DesafioPauta.dto.entry.VoteEntry;
import br.com.compasso.DesafioPauta.entity.Agenda;
import br.com.compasso.DesafioPauta.entity.Associated;
import br.com.compasso.DesafioPauta.entity.Vote;
import br.com.compasso.DesafioPauta.enumeration.VoteResponse;
import br.com.compasso.DesafioPauta.service.impl.VoteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VoteController.class)
class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoteServiceImpl voteService;

    @MockBean
    private VoteConverter voteConverter;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void listVotes() throws Exception {

        var vote = generateVote();
        var voteDto = generateVoteDto(vote);

        //when(voteService.list()).thenReturn(Collections.singletonList(vote));
        when(voteService.list()).thenReturn(Collections.singletonList(vote));
        when(voteConverter.voteToVoteDto(any(Vote.class))).thenReturn(voteDto);

        mockMvc.perform(get("/vote"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].agenda").value(vote.getAgenda()))
                .andExpect(jsonPath("$[0].associated").value(vote.getAssociated()))
                .andDo(print());

        verify(voteService).list();
        verify(voteConverter).voteToVoteDto(any(Vote.class));

    }

    @Test
    void registerVote() throws Exception {

        var vote = generateVote();
        var voteDto = generateVoteDto(vote);

        when(voteConverter.entryToVote(any(VoteEntry.class))).thenReturn(vote);
        when(voteService.register(vote)).thenReturn(vote);
        when(voteConverter.voteToVoteDto(any(Vote.class))).thenReturn(voteDto);

        mockMvc.perform(post("/vote")
                .content(objectMapper.writeValueAsString(vote))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.agenda").value(vote.getAgenda()))
                .andExpect(jsonPath("$.associated").value(vote.getAssociated()))
                .andDo(print());

    }


    private VoteDto generateVoteDto(Vote vote) {
        return new VoteDto(vote.getAgenda(), vote.getAssociated());
    }

    private Vote generateVote() {

        return Vote.builder()
                .id("1")
                .agenda(generateAgenda())
                .associated(generateAssociated())
                .response(VoteResponse.YES)
                .build();
    }

    private Agenda generateAgenda() {
        return Agenda.builder()
                .id("1")
                .build();
    }

    private Associated generateAssociated() {
        return Associated.builder().id("1").build();
    }
}