package br.com.compasso.DesafioPauta.controller;

import br.com.compasso.DesafioPauta.converter.VoteConverter;
import br.com.compasso.DesafioPauta.dto.VoteDto;
import br.com.compasso.DesafioPauta.dto.entry.VoteEntry;
import br.com.compasso.DesafioPauta.entity.Vote;
import br.com.compasso.DesafioPauta.service.impl.VoteServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<List<VoteDto>> listVotes() {

        try{
            return new ResponseEntity<>(voteConverter.listVotesToListVotesDto(voteService.list()), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<VoteDto> registerVote(@Valid @RequestBody VoteEntry entry) {

        try{
            Vote vote = voteConverter.entryToVote(entry);
            return new ResponseEntity<>(voteConverter.voteToVoteDto(voteService.register(vote)), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }


}
