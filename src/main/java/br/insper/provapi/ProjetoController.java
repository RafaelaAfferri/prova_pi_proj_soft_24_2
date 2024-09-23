package br.insper.provapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjetoController {

    @Autowired ProjetoService projetoService;


    @PostMapping("/projeto")
    public Projeto CadastraProjeto(@RequestBody Projeto projeto){
        return projetoService.CadastraProjeto(projeto);
    }

    @GetMapping("/projeto")
    public List<Projeto> listaProjetos(@RequestParam(required = false) String status){
        return projetoService.listaProjetos(status);
    }

    @PostMapping("/projeto/{nome}/pessoa")
    public Projeto addPessoas(@PathVariable String nome, @RequestParam String cpf){
        return projetoService.addPessoas(nome, cpf);
    }
}
