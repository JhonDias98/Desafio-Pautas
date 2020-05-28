package br.com.compasso.DesafioPauta.entity;


import br.com.compasso.DesafioPauta.dto.entry.AssociatedEntry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class Associated {

    @Id
    private String id;
    private String name;
    private String email;

    public Associated(AssociatedEntry entry) {
        this.name = entry.getName();
        this.email = entry.getEmail();
    }

    public Associated(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
