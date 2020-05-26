package br.com.compasso.DesafioPauta.service;

import br.com.compasso.DesafioPauta.entity.Associated;

import java.util.List;

public interface AssociatedService {

    List<Associated> list();
    Associated find(String id);
    Associated register(Associated associated);
    void delete(String id);

}
