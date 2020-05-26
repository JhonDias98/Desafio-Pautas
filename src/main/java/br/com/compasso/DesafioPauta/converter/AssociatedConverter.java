package br.com.compasso.DesafioPauta.converter;

import br.com.compasso.DesafioPauta.dto.AssociatedDto;
import br.com.compasso.DesafioPauta.dto.entry.AssociatedEntry;
import br.com.compasso.DesafioPauta.entity.Associated;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssociatedConverter {
    public List<AssociatedDto> listToListAssociatedDto(List<Associated> list) {
        return list.stream().map(AssociatedDto::new).collect(Collectors.toList());
    }

    public AssociatedDto associatedToAssociatedDto(Associated associated) {
        return new AssociatedDto(associated);
    }

    public Associated entryToAssociated(AssociatedEntry entry) {
        return new Associated(entry);
    }
}
