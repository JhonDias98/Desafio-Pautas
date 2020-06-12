package br.com.compasso.DesafioPauta.controller;

import br.com.compasso.DesafioPauta.converter.VoteConverter;
import br.com.compasso.DesafioPauta.dto.VoteDto;
import br.com.compasso.DesafioPauta.dto.entry.VoteEntry;
import br.com.compasso.DesafioPauta.entity.Vote;
import br.com.compasso.DesafioPauta.service.impl.VoteServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vote")
@Validated
public class VoteController {

    private final VoteServiceImpl voteService;
    private final VoteConverter voteConverter;

    public VoteController(VoteServiceImpl voteService, VoteConverter voteConverter) {
        this.voteService = voteService;
        this.voteConverter = voteConverter;
    }

    @GetMapping
    @ApiOperation(value = "List all votes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful listing", response = VoteDto.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<VoteDto>> listVotes() {

        List<VoteDto> voteDtos = voteService.list().stream()
                .map(voteConverter::voteToVoteDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(voteDtos, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Register a vote")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful registering vote", response = VoteDto.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public ResponseEntity<VoteDto> registerVote(@Valid @RequestBody VoteEntry entry) {

        Vote vote = voteConverter.entryToVote(entry);
        return new ResponseEntity<>(voteConverter.voteToVoteDto(voteService.register(vote)), HttpStatus.CREATED);
    }


}
