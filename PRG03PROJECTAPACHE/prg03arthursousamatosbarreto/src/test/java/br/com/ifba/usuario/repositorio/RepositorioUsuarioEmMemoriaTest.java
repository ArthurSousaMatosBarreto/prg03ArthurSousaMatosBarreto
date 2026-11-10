package br.com.ifba.usuario.repositorio;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepositorioUsuarioEmMemoriaTest {

    @Test
    public void cadastrar_usuarioApareceEmListarTodos() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario usuario = new Usuario("Arthur Sousa", "5729589233", "arthur_sousa", "arthur0910");

        repositorio.cadastrar(usuario);

        assertTrue(repositorio.listarTodos().contains(usuario));
    }

    @Test
    public void buscarPorLogin_devolveOUsuarioCerto() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario arthur = new Usuario("Arthur Sousa", "5729589233", "arthur_sousa", "arthur0910");
        Usuario jonatas = new Usuario("Jonatas Bastos", "11122233344", "jonatas", "senha123");

        repositorio.cadastrar(arthur);
        repositorio.cadastrar(jonatas);

        assertEquals(arthur, repositorio.buscarPorLogin("arthur_sousa"));
  
    }@Test
    public void buscarPorLogin_comLoginInexistente_lancaExcecao() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        UsuarioNaoEncontradoException erro = assertThrows(UsuarioNaoEncontradoException.class,
                () -> repositorio.buscarPorLogin("nao_existe"));
        assertTrue(erro.getMessage().contains("nao_existe"));
    }

    @Test
    public void buscarPorLogin_comLoginExistente_naoLancaExcecao() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        repositorio.cadastrar(new Usuario("Arthur Sousa", "5729589233", "arthur_sousa", "arthur0910"));
        assertDoesNotThrow(() -> repositorio.buscarPorLogin("arthur_sousa"));
    }

    @Test
    public void buscarPorLoginComList_comLoginInexistente_lancaExcecao() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        assertThrows(UsuarioNaoEncontradoException.class,
                () -> repositorio.buscarPorLoginComList("nao_existe"));
    }

    @Test
    public void buscarPorLoginComList_comLoginExistente_naoLancaExcecao() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario arthur = new Usuario("Arthur Sousa", "5729589233", "arthur_sousa", "arthur0910");
        repositorio.cadastrar(arthur);
        assertEquals(arthur, repositorio.buscarPorLoginComList("arthur_sousa"));
    }

    @Test
    public void cadastrar_loginDuplicado_lancaExcecao() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario primeiro = new Usuario("Arthur Sousa", "5729589233", "arthur_sousa", "arthur0910");
        Usuario duplicado = new Usuario("Outro Nome", "99988877766", "arthur_sousa", "outraSenha");

        repositorio.cadastrar(primeiro);

        assertThrows(IllegalArgumentException.class, () -> repositorio.cadastrar(duplicado));
    }

    @Test
    public void equals_doisUsuariosComMesmoLogin_saoIguaisParaALista() {
        Usuario u1 = new Usuario("Arthur Sousa", "5729589233", "arthur_sousa", "arthur0910");
        Usuario u2 = new Usuario("Nome Diferente", "11111111111", "arthur_sousa", "senhaDiferente");

        assertEquals(u1, u2); // mesmo login = iguais, mesmo com todo o resto diferente
    }
}