package br.insper.provapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjetoService {

    @Autowired ProjetoRepository projetoRepository;

    @Autowired UsuarioService usuarioService;

    public Projeto CadastraProjeto(Projeto projeto){
        if(projeto.getNome().isEmpty() || projeto.getGerente().isEmpty() || projeto.getDescricao().isEmpty() || projeto.getStatus().isEmpty()){
            throw new RuntimeException("Dados invalidos");
        }
        ResponseEntity<Usuario> gerente = usuarioService.getUsuario(projeto.getGerente());
        if(gerente.getStatusCode().is2xxSuccessful()) {
            return projetoRepository.save(projeto);
        }
        else {
            throw new RuntimeException("Gerente não encontrado");
        }
    }

    public List<Projeto> listaProjetos(String status) {
        if (status != null) {
            return projetoRepository.findByStatus(status);
        }
        return projetoRepository.findAll();
    }

    public Projeto addPessoas(String nome, String cpf) {
        Projeto projeto = projetoRepository.findByNome(nome);
        if (projeto == null) {
            throw new RuntimeException("Projeto não encontrado");
        }
        if(projeto.getStatus().equals("FINALIZADO")){
            throw new RuntimeException("Projeto finalizado");

        }
        ResponseEntity<Usuario> pessoa = usuarioService.getUsuario(cpf);
        if (pessoa.getStatusCode().is2xxSuccessful()) {
            ArrayList<String> pessoas = projeto.getPessoas();
            if (pessoa.getBody().getCpf() != null) {
                pessoas.add(pessoa.getBody().getCpf());
                projeto.setPessoas(pessoas);
                return projetoRepository.save(projeto);
            } else {
                throw new RuntimeException("Pessoa " + cpf + " não tem cpf");
            }
        }
        else {
            throw new RuntimeException("Pessoa " + cpf + " não encontrada");
        }
    }
}
