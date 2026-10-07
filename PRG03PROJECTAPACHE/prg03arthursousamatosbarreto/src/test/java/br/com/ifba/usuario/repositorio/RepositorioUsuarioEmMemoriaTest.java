package br.com.ifba.usuario.repositorio;

import br.com.ifba.usuario.entity.Usuario;
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
    }

    @Test
    public void buscarPorLogin_comLoginInexistente_devolveNull() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();

        assertNull(repositorio.buscarPorLogin("nao_existe"));
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