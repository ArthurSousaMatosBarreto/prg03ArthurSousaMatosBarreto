package br.com.ifba.usuario.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioRelacionamentoTest {

    @Test
    public void getEndereco_deveDevolverOEnderecoDefinido() {
        Usuario usuario = new Usuario();
        Endereco endereco = new Endereco("Rua A", "Irece", "44900-000");
        usuario.setEndereco(endereco);

        assertEquals(endereco, usuario.getEndereco());
        assertEquals("Irece", usuario.getEndereco().getCidade());
    }

    @Test
    public void adicionarCurso_deveAumentarOTamanhoDaLista() {
        Usuario usuario = new Usuario();
        assertEquals(0, usuario.getCursos().size());

        usuario.adicionarCurso(new Curso("Programacao Java", 80));

        assertEquals(1, usuario.getCursos().size());
    }

    @Test
    public void adicionarDoisCursos_listaDeveTerDoisItens() {
        Usuario usuario = new Usuario();
        usuario.adicionarCurso(new Curso("Programacao Java", 80));
        usuario.adicionarCurso(new Curso("Banco de Dados", 60));

        assertEquals(2, usuario.getCursos().size());
    }

    @Test
    public void usuarioRecemCriado_deveNascerComTipoAluno() {
        Usuario usuario = new Usuario();
        assertEquals(TipoUsuario.ALUNO, usuario.getTipoUsuario());
    }

    @Test
    public void setTipoUsuario_deveAlterarOTipo() {
        Usuario usuario = new Usuario();
        usuario.setTipoUsuario(TipoUsuario.PROFESSOR);
        assertEquals(TipoUsuario.PROFESSOR, usuario.getTipoUsuario());
    }
}