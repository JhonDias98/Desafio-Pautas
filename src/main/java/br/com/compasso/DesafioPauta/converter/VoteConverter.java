package br.com.compasso.DesafioPauta.converter;

import br.com.compasso.DesafioPauta.dto.VoteDto;
import br.com.compasso.DesafioPauta.dto.entry.VoteEntry;
import br.com.compasso.DesafioPauta.entity.Vote;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VoteConverter {
    public List<VoteDto> listVotesToListVotesDto(List<Vote> list) {
        return list.stream().map(VoteDto::new).collect(Collectors.toList());
    }

    public Vote entryToVote(VoteEntry entry) {
        return new Vote(entry);
    }

    public VoteDto voteToVoteDto(Vote vote) {
        return new VoteDto(vote);
    }
}
