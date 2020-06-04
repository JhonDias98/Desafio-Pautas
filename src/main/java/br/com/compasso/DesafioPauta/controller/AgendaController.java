package br.com.compasso.DesafioPauta.controller;

import br.com.compasso.DesafioPauta.converter.AgendaConverter;
import br.com.compasso.DesafioPauta.dto.AgendaDto;
import br.com.compasso.DesafioPauta.dto.details.AgendaDtoDetails;
import br.com.compasso.DesafioPauta.dto.entry.AgendaEntry;
import br.com.compasso.DesafioPauta.entity.Agenda;
import br.com.compasso.DesafioPauta.enumeration.AgendaStatus;
import br.com.compasso.DesafioPauta.service.impl.AgendaServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "List all registered agendas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List meeting agendas", response = AgendaDto.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<AgendaDto>> listAgendas() {
        try {
            return new ResponseEntity<>(agendaConverter.listAgendaToListAgendaDto(agendaService.list()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Find agenda by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pick agenda by variable path", response = AgendaDto.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Agenda not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<AgendaDtoDetails> pickUpAgenda(@PathVariable String id) {
        try {
            return new ResponseEntity<>(agendaConverter.agendaToAgendaDtoDetails(agendaService.find(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/status/{status}")
    public ResponseEntity<List<AgendaDto>> pickUpAgendasByStatus(@PathVariable AgendaStatus status) {
        List<AgendaDto> agendaDtos = agendaConverter.listAgendaToListAgendaDto(agendaService.listAgendasByStatus(status));
        return new ResponseEntity<>(agendaDtos, HttpStatus.OK);
    }

    @GetMapping(path = "/result")
    @ApiOperation(value = "Return results of agendas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List results successful", response = AgendaDtoDetails.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<AgendaDtoDetails>> listAgendaDetails() {

        try {
            List<AgendaDtoDetails> agendaDtoDetails = agendaConverter.agendasToAgendasDtoDetails(agendaService.list());
            return new ResponseEntity<>(agendaDtoDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @ApiOperation(value = "Register a new agenda")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Agenda registered successful", response = AgendaDto.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal Sever Error")
    })
    public ResponseEntity<AgendaDto> createAgenda(@Valid @RequestBody AgendaEntry entry) {
        try {
            Agenda agenda = agendaConverter.entryToAgenda(entry);
            return new ResponseEntity<>(agendaConverter.agendaToAgendaDto(agendaService.register(agenda)), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete agenda by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Sever Error")
    })
    public void deleteAgenda(@PathVariable("id") String id) {
        agendaService.delete(id);
    }


}
