package br.com.compasso.DesafioPauta.controller;

import br.com.compasso.DesafioPauta.converter.AssociatedConverter;
import br.com.compasso.DesafioPauta.dto.AssociatedDto;
import br.com.compasso.DesafioPauta.dto.entry.AssociatedEntry;
import br.com.compasso.DesafioPauta.entity.Associated;
import br.com.compasso.DesafioPauta.service.impl.AssociatedServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/associated")
@ExposesResourceFor(Associated.class)
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

        List<AssociatedDto> associatedDtos = associatedService.list().stream()
                .map(associatedConverter::associatedToAssociatedDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(associatedDtos, HttpStatus.OK);

    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Find associated by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Associated encountered", response = AssociatedDto.class),
            @ApiResponse(code = 204, message = "Associated not found")
    })
    public ResponseEntity<AssociatedDto> pickUpAssociated(@PathVariable("id") String id) {

        AssociatedDto associatedDto = associatedConverter
                .associatedToAssociatedDto(associatedService.find(id));

        return new ResponseEntity<>(associatedDto, HttpStatus.OK);

    }

    @PostMapping
    @ApiOperation(value = "Register associated")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Associated registered", response = AssociatedDto.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public ResponseEntity<AssociatedDto> registerAssociated(@Valid @RequestBody AssociatedEntry entry) {

        Associated associated = associatedService.register(associatedConverter.entryToAssociated(entry));
        AssociatedDto associatedDto = associatedConverter.associatedToAssociatedDto(associated);

        return new ResponseEntity<>(associatedDto, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete associate by ID")
    public void deleteAssociated(@PathVariable String id) {
        associatedService.delete(id);
    }
}
