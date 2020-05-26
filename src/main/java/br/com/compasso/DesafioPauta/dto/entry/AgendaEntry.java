package br.com.compasso.DesafioPauta.dto.entry;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Data
public class AgendaEntry {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private Optional<Integer> duration;


}
