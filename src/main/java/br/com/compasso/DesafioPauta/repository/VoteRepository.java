package br.com.compasso.DesafioPauta.repository;

import br.com.compasso.DesafioPauta.entity.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {

}
