package br.insper.provapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class projetoServiceTest {


    @InjectMocks
    private ProjetoService projetoService;

    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private UsuarioService usuarioService;

    @Test
    public void testListarTodosProjetosStatusNull() {

        Mockito.when(projetoRepository.findAll()).thenReturn(new ArrayList<>());

        List<Projeto> projetos = projetoService.listaProjetos(null);

        assert projetos.isEmpty();

    }

    @Test
    public void testCadastraProjetoNomeEmpty(){
        Projeto projeto = new Projeto();
        projeto.setNome("");
        projeto.setGerente("Gerente 1");
        projeto.setDescricao("Descricao 1");
        projeto.setStatus("Status 1");

        Assertions.assertThrows(RuntimeException.class, () -> {
            projetoService.CadastraProjeto(projeto);
        });
    }

    @Test
    public void testCadastraProjetoGerenteEmpty(){
        Projeto projeto = new Projeto();
        projeto.setNome("Nome 1");
        projeto.setGerente("");
        projeto.setDescricao("Descricao 1");
        projeto.setStatus("Status 1");

        Assertions.assertThrows(RuntimeException.class, () -> {
            projetoService.CadastraProjeto(projeto);
        });
    }

    @Test
    public void testCadastraProjetoDescricaoEmpty(){
        Projeto projeto = new Projeto();
        projeto.setNome("Nome 1");
        projeto.setGerente("Gerente 1");
        projeto.setDescricao("");
        projeto.setStatus("Status 1");

        Assertions.assertThrows(RuntimeException.class, () -> {
            projetoService.CadastraProjeto(projeto);
        });
    }

    @Test
    public void testCadastraProjetoStatusEmpty(){
        Projeto projeto = new Projeto();
        projeto.setNome("Nome 1");
        projeto.setGerente("Gerente 1");
        projeto.setDescricao("Descricao 1");
        projeto.setStatus("");

        Assertions.assertThrows(RuntimeException.class, () -> {
            projetoService.CadastraProjeto(projeto);
        });
    }

    @Test
    public void listarProjetoFiltroStatus(){
        Projeto projeto = new Projeto();
        projeto.setNome("Nome 1");
        projeto.setGerente("Gerente 1");
        projeto.setDescricao("Descricao 1");
        projeto.setStatus("Status 1");

        List<Projeto> projetos = new ArrayList<>();
        projetos.add(projeto);

        Mockito.when(projetoRepository.findByStatus("Status 1")).thenReturn(projetos);

        List<Projeto> projetosRetorno = projetoService.listaProjetos("Status 1");

        assert projetosRetorno.size() == 1;
        assert projetosRetorno.get(0).getNome().equals("Nome 1");
        assert projetosRetorno.get(0).getGerente().equals("Gerente 1");
        assert projetosRetorno.get(0).getDescricao().equals("Descricao 1");
        assert projetosRetorno.get(0).getStatus().equals("Status 1");
    }

    @Test
    public void testCadastroProjetoUnsucessfulCode(){
        ResponseEntity<Usuario> gerente = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Mockito.when(usuarioService.getUsuario("Gerente 1")).thenReturn(gerente);

        Assertions.assertThrows(RuntimeException.class, () -> {
            Projeto projeto = new Projeto();
            projeto.setNome("Nome 1");
            projeto.setGerente("Gerente 1");
            projeto.setDescricao("Descricao 1");
            projeto.setStatus("Status 1");

            projetoService.CadastraProjeto(projeto);
        });

    }

    @Test
    public void testCadastraProjetoValid(){
        Projeto projeto = new Projeto();
        projeto.setNome("Nome 1");
        projeto.setGerente("Gerente 1");
        projeto.setDescricao("Descricao 1");
        projeto.setStatus("Status 1");

        Usuario usuario = new Usuario();
        usuario.setCpf("Gerente 1");


        ResponseEntity<Usuario> gerente = new ResponseEntity<>(usuario, HttpStatus.OK);

        Mockito.when(usuarioService.getUsuario("Gerente 1")).thenReturn(gerente);
        Mockito.when(projetoRepository.save(projeto)).thenReturn(projeto);

        Projeto projetoRetorno = projetoService.CadastraProjeto(projeto);

        assert projetoRetorno.getNome().equals("Nome 1");
        assert projetoRetorno.getGerente().equals("Gerente 1");
        assert projetoRetorno.getDescricao().equals("Descricao 1");
        assert projetoRetorno.getStatus().equals("Status 1");
    }

    @Test
    public void testAddPessoaProjetoNull(){
        Mockito.when(projetoRepository.findByNome("Nome 1")).thenReturn(null);

        Assertions.assertThrows(RuntimeException.class, () -> {
            projetoService.addPessoas("Nome 1", "CPF 1");
        });
    }

    @Test
    public void testAddPessoasStatusFinalizado(){
        Projeto projeto = new Projeto();
        projeto.setNome("Nome 1");
        projeto.setGerente("Gerente 1");
        projeto.setDescricao("Descricao 1");
        projeto.setStatus("FINALIZADO");

        Mockito.when(projetoRepository.findByNome("Nome 1")).thenReturn(projeto);

        Assertions.assertThrows(RuntimeException.class, () -> {
            projetoService.addPessoas("Nome 1", "CPF 1");
        });
    }

    @Test
    public void testAddPessoasCodigoErrado(){
        Projeto projeto = new Projeto();
        projeto.setNome("Nome 1");
        projeto.setGerente("Gerente 1");
        projeto.setDescricao("Descricao 1");
        projeto.setStatus("PLANEJAMENTO");

        Mockito.when(projetoRepository.findByNome("Nome 1")).thenReturn(projeto);

        ResponseEntity<Usuario> pessoa = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Mockito.when(usuarioService.getUsuario("CPF 1")).thenReturn(pessoa);

        Assertions.assertThrows(RuntimeException.class, () -> {
            projetoService.addPessoas("Nome 1", "CPF 1");
        });

    }
}
