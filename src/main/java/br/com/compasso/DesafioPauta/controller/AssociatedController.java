package br.com.compasso.DesafioPauta.controller;

import br.com.compasso.DesafioPauta.converter.AssociatedConverter;
import br.com.compasso.DesafioPauta.dto.AssociatedDto;
import br.com.compasso.DesafioPauta.dto.entry.AssociatedEntry;
import br.com.compasso.DesafioPauta.entity.Associated;
import br.com.compasso.DesafioPauta.service.impl.AssociatedServiceImpl;
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
    public ResponseEntity<List<AssociatedDto>> listAssociateds() {
        try {
            return new ResponseEntity<>(associatedConverter.listToListAssociatedDto(associatedService.list()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AssociatedDto> pickUpAssociated(@PathVariable("id") String id) {

        try{
            return new ResponseEntity<>(associatedConverter.associatedToAssociatedDto(associatedService.find(id)), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<AssociatedDto> registerAssociated(@Valid @RequestBody AssociatedEntry entry) {

        try {
            Associated associated = associatedConverter.entryToAssociated(entry);
            return new ResponseEntity<>(associatedConverter.associatedToAssociatedDto(associatedService.register(associated)), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public void deleteAssociated(@PathVariable String id) {
        associatedService.delete(id);
    }
}
