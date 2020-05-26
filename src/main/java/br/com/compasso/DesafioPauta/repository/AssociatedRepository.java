package br.com.compasso.DesafioPauta.repository;

import br.com.compasso.DesafioPauta.entity.Associated;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociatedRepository extends MongoRepository<Associated, String> {
}
