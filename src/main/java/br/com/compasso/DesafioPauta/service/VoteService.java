package br.com.compasso.DesafioPauta.service;

import br.com.compasso.DesafioPauta.entity.Vote;

import java.util.List;

public interface VoteService {

    List<Vote> list();
    Vote register(Vote vote);
}
