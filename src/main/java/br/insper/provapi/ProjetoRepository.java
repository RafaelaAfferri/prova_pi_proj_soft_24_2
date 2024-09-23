package br.insper.provapi;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoRepository extends MongoRepository<Projeto, String>{
    public List<Projeto> findByStatus(String status);
    public Projeto findByNome(String Nome);
}
