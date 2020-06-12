package br.com.compasso.DesafioPauta.controller;

import br.com.compasso.DesafioPauta.converter.AssociatedConverter;
import br.com.compasso.DesafioPauta.dto.AssociatedDto;
import br.com.compasso.DesafioPauta.dto.entry.AssociatedEntry;
import br.com.compasso.DesafioPauta.entity.Associated;
import br.com.compasso.DesafioPauta.service.impl.AssociatedServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AssociatedController.class)
class AssociatedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssociatedServiceImpl associatedService;

    @MockBean
    private AssociatedConverter associatedConverter;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void listAssociateds() throws Exception {

        var associated = generateAssociated();
        AssociatedDto associatedDto = generateAssociatedDto();

        when(associatedService.list()).thenReturn(Collections.singletonList(associated));
        when(associatedConverter.associatedToAssociatedDto(any(Associated.class))).thenReturn(associatedDto);
        
        mockMvc.perform(get("/associated"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(associated.getName()))
                .andExpect(jsonPath("$[0].email").value(associated.getEmail()));

    }

    @Test
    void pickUpAssociatedTest() throws Exception {

        var associated = generateAssociated();


        when(associatedService.find("!fd")).thenReturn(associated);
        when(associatedConverter.associatedToAssociatedDto(associated)).thenReturn(generateAssociatedDto());

        mockMvc.perform(get("/associated/{id}", "!fd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(associated.getName()))
                .andExpect(jsonPath("$.email").value(associated.getEmail()));

    }

    @Test
    void throw404WhenAssociatedIdIsUnregistered() throws Exception {

        when(associatedService.find("idIncorreto")).thenThrow(new ResourceNotFoundException());

        mockMvc.perform(get(("/associated/{id}"), "idIncorreto"))
                .andExpect(status().isNotFound());

    }

    @Test
    void registerAssociatedTest() throws Exception {

        var associated = generateAssociated();
        var associatedDTO = generateAssociatedDto();

        when(associatedService.register(associatedConverter.entryToAssociated(any(AssociatedEntry.class))))
                .thenReturn(associated);
        when(associatedConverter.associatedToAssociatedDto(associated)).thenReturn(associatedDTO);

        mockMvc.perform(post(("/associated"))
                .content(objectMapper.writeValueAsString(associatedDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(associated.getName()))
                .andExpect(jsonPath("$.email").value(associated.getEmail()));

    }

    @Test
    void deleteAssociatedTest() throws Exception {

        doNothing().when(associatedService).delete(any(String.class));

        mockMvc.perform(delete("/associated/{id}", "idQualquer"))
                .andExpect(status().isOk());

    }

    private Associated generateAssociated() {
        return Associated.builder()
                .id("1")
                .name("Matheus")
                .email("matheus@email.com")
                .build();
    }

    private AssociatedDto generateAssociatedDto() {
        AssociatedDto associatedDto = new AssociatedDto("Matheus", "matheus@email.com");
        associatedDto.add(Link.of("linkTeste"));

        return associatedDto;
    }
}