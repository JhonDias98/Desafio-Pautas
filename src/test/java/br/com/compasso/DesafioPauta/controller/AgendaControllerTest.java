package br.com.compasso.DesafioPauta.controller;

import br.com.compasso.DesafioPauta.converter.AgendaConverter;
import br.com.compasso.DesafioPauta.dto.AgendaDto;
import br.com.compasso.DesafioPauta.dto.details.AgendaDtoDetails;
import br.com.compasso.DesafioPauta.entity.Agenda;
import br.com.compasso.DesafioPauta.enumeration.AgendaStatus;
import br.com.compasso.DesafioPauta.service.impl.AgendaServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AgendaController.class)
class AgendaControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgendaServiceImpl agendaService;

    @MockBean
    private AgendaConverter agendaConverter;

    private static ObjectMapper objectMapper;


    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void listAgendas() throws Exception {

        var agenda = generateAgenda();
        AgendaDto agendaDto = new AgendaDto(agenda);
        when(agendaConverter.listAgendaToListAgendaDto(agendaService.list())).thenReturn(Arrays.asList(agendaDto));

        mockMvc.perform(get(("/agenda")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].title").value(agenda.getTitle()))
                .andExpect(jsonPath("$[0].description").value(agenda.getDescription()))
                .andExpect(jsonPath("$[0].status").value(agenda.getStatus().toString()));

    }

    @Test
    void pickUpAgenda() throws Exception {
        var agenda = generateAgenda();
        when(agendaService.find("1")).thenReturn(agenda);
        when(agendaConverter.agendaToAgendaDtoDetails(agendaService.find("1"))).thenReturn(new AgendaDtoDetails(agenda));

        mockMvc.perform(get(("/agenda/{id}"), "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title").value(agenda.getTitle()))
                .andExpect(jsonPath("$.description").value(agenda.getDescription()))
                .andExpect(jsonPath("$.amountYes").value(agenda.getAmountYes()))
                .andExpect(jsonPath("$.amountNo").value(agenda.getAmountNo()))
                .andExpect(jsonPath("$.status").value(agenda.getStatus().toString()));

    }

    @Test
    void pickUpAgendaWithIdUnregistered() throws Exception {

        var Agenda = generateAgenda();
        when(agendaConverter.agendaToAgendaDtoDetails(agendaService.find(any(String.class)))).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get(("/agenda/{id}"), "hsu"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .equals(new IllegalArgumentException());


    }

    @Test
    void listAgendaDetails() throws Exception {
        var agenda = generateAgenda();
        when(agendaService.list()).thenReturn(Arrays.asList(agenda));
        when(agendaConverter.agendasToAgendasDtoDetails(agendaService.list())).thenReturn(Arrays.asList(new AgendaDtoDetails(agenda)));

        mockMvc.perform(get(("/agenda/result")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].title").value(agenda.getTitle()))
                .andExpect(jsonPath("$[0].description").value(agenda.getDescription()))
                .andExpect(jsonPath("$[0].amountYes").value(agenda.getAmountYes()))
                .andExpect(jsonPath("$[0].amountNo").value(agenda.getAmountNo()))
                .andExpect(jsonPath("$[0].status").value(agenda.getStatus().toString()));
    }

    @Test
    void createAgenda() throws Exception {

        var agenda = generateAgenda();
        when(agendaConverter.agendaToAgendaDto(agendaService.register(any(Agenda.class))))
                .thenReturn(new AgendaDto(agenda));

        mockMvc.perform(post(("/agenda"))
                .content(asJsonString(agenda))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(agenda.getTitle()))
                .andExpect(jsonPath("$.description").value(agenda.getDescription()))
                .andExpect(jsonPath("$.status").value(agenda.getStatus().toString()))
                .andDo(print());
    }

    @Test
    void deleteAgenda() throws Exception {

        doNothing().when(agendaService).delete(any(String.class));

        mockMvc.perform(delete("/agenda/{id}", "hdus"))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }

    private static String asJsonString(Object object) throws JsonProcessingException {

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private Agenda generateAgenda() {
        return Agenda.builder()
                .id("1")
                .title("title")
                .description("description")
                .status(AgendaStatus.OPEN)
                .begin(LocalDateTime.now())
                .end(LocalDateTime.now().plusMinutes(1))
                .amountYes(0)
                .amountNo(0)
                .build();
    }
}