package br.com.compasso.DesafioPauta.controller;

import br.com.compasso.DesafioPauta.converter.AgendaConverter;
import br.com.compasso.DesafioPauta.dto.AgendaDto;
import br.com.compasso.DesafioPauta.dto.entry.AgendaEntry;
import br.com.compasso.DesafioPauta.entity.Agenda;
import br.com.compasso.DesafioPauta.service.impl.AgendaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    private final AgendaServiceImpl agendaService;
    private final AgendaConverter agendaConverter;

    public AgendaController(AgendaServiceImpl agendaService, AgendaConverter agendaConverter) {
        this.agendaService = agendaService;
        this.agendaConverter = agendaConverter;
    }


    @GetMapping
    public ResponseEntity<List<AgendaDto>> listAgendas() {
        try {
            return new ResponseEntity<>(agendaConverter.listAgendaToListAgendaDto(agendaService.list()), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AgendaDto> pickUpAgenda(@PathVariable String id) {
        try {
            return new ResponseEntity<>(agendaConverter.agendaToAgendaDto(agendaService.find(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<AgendaDto> createAgenda(@Valid @RequestBody AgendaEntry entry) {
        try {
            Agenda agenda = agendaConverter.entryToAgenda(entry);
            return new ResponseEntity<>(agendaConverter.agendaToAgendaDto(agendaService.register(agenda)), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public void deleteAgenda(@PathVariable("id") String id) {
        agendaService.delete(id);
    }


}
