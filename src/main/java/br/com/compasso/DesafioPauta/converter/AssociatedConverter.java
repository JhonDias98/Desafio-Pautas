package br.com.compasso.DesafioPauta.converter;

import br.com.compasso.DesafioPauta.dto.AssociatedDto;
import br.com.compasso.DesafioPauta.dto.entry.AssociatedEntry;
import br.com.compasso.DesafioPauta.entity.Associated;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.stereotype.Component;

@Component
public class AssociatedConverter {

    private final EntityLinks entityLinks;
    private final ModelMapper modelMapper;

    public AssociatedConverter(EntityLinks entityLinks, ModelMapper modelMapper) {
        this.entityLinks = entityLinks;
        this.modelMapper = modelMapper;
    }

    public AssociatedDto associatedToAssociatedDto(Associated associated) {

        AssociatedDto associatedDto = modelMapper.map(associated, AssociatedDto.class);
        associatedDto.add(entityLinks.linkToItemResource(Associated.class, associated.getId()).withSelfRel());

        return associatedDto;
    }

    public Associated entryToAssociated(AssociatedEntry entry) {
        return Associated.builder()
                .name(entry.getName())
                .email(entry.getEmail())
                .build();
    }
}
