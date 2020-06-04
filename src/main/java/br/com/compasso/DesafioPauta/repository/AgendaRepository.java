package br.com.compasso.DesafioPauta.repository;

import br.com.compasso.DesafioPauta.entity.Agenda;
import br.com.compasso.DesafioPauta.enumeration.AgendaStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendaRepository extends MongoRepository<Agenda, String> {

    @Query("{ 'status': {$eq: ?0 } }")
    public List<Agenda> getAgendasWithStatus(AgendaStatus status);

}
