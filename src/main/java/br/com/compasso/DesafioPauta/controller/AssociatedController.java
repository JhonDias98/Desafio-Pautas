package br.com.compasso.DesafioPauta.controller;

import br.com.compasso.DesafioPauta.converter.AssociatedConverter;
import br.com.compasso.DesafioPauta.dto.AssociatedDto;
import br.com.compasso.DesafioPauta.dto.entry.AssociatedEntry;
import br.com.compasso.DesafioPauta.entity.Associated;
import br.com.compasso.DesafioPauta.service.impl.AssociatedServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/associated")
public class AssociatedController {

    private final AssociatedServiceImpl associatedService;
    private final AssociatedConverter associatedConverter;

    public AssociatedController(AssociatedServiceImpl associatedService, AssociatedConverter associatedConverter) {
        this.associatedService = associatedService;
        this.associatedConverter = associatedConverter;
    }

    @GetMapping
    @ApiOperation(value = "List all associates")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful listin associates", response = AssociatedDto.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<AssociatedDto>> listAssociateds() {
        try {
            return new ResponseEntity<>(associatedConverter.listToListAssociatedDto(associatedService.list()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Find associated by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Associated encountered", response = AssociatedDto.class),
            @ApiResponse(code = 204, message = "Associated not found")
    })
    public ResponseEntity<AssociatedDto> pickUpAssociated(@PathVariable("id") String id) {

        try{
            return new ResponseEntity<>(associatedConverter.associatedToAssociatedDto(associatedService.find(id)), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ApiOperation(value = "Register associated")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Associated registered", response = AssociatedDto.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public ResponseEntity<AssociatedDto> registerAssociated(@Valid @RequestBody AssociatedEntry entry) {

        try {
            Associated associated = associatedConverter.entryToAssociated(entry);
            return new ResponseEntity<>(associatedConverter.associatedToAssociatedDto(associatedService.register(associated)), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete associate by ID")
    public void deleteAssociated(@PathVariable String id) {
        associatedService.delete(id);
    }
}
