package br.com.compasso.DesafioPauta.repository;

import br.com.compasso.DesafioPauta.entity.Agenda;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends MongoRepository<Agenda, String> {
}
