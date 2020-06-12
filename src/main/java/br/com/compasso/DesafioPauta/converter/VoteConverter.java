package br.com.compasso.DesafioPauta.converter;

import br.com.compasso.DesafioPauta.dto.VoteDto;
import br.com.compasso.DesafioPauta.dto.entry.VoteEntry;
import br.com.compasso.DesafioPauta.entity.Vote;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VoteConverter {
    
    private final ModelMapper modelMapper;

    public VoteConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Vote entryToVote(VoteEntry entry) {

        return Vote.builder()
                .agenda(entry.getAgenda())
                .associated(entry.getAssociated())
                .response(entry.getResponse())
                .build();

    }

    public VoteDto voteToVoteDto(Vote vote) {

        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);

        return voteDto;
    }
}
