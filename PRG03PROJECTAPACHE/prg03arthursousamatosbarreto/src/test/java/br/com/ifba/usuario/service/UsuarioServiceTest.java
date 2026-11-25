package br.com.ifba.usuario.service;

import br.com.ifba.infrastructure.exception.NegocioException;
import br.com.ifba.usuario.dao.FakeUsuarioDao;
import br.com.ifba.usuario.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioServiceTest {

    private FakeUsuarioDao dao;
    private UsuarioIService service;

    @BeforeEach
    public void preparar() {
        dao = new FakeUsuarioDao();
        service = new UsuarioService(dao); // entrega o dublê: nenhum teste toca no banco
    }

    private Usuario usuarioValido(String login) {
        return new Usuario("Arthur Sousa", "12345678900", login, "Senha1234");
    }

    @Test
    public void save_comDadosValidos_gravaEDevolveComId() {
        Usuario salvo = service.save(usuarioValido("arthur_sousa"));
        assertNotNull(salvo.getId());
        assertEquals(1, dao.findAll().size());
    }

    @Test
    public void save_comSenhaCurta_lancaNegocioExceptionENaoGrava() {
        Usuario usuario = usuarioValido("arthur_sousa");
        usuario.setSenha("12");
        assertThrows(NegocioException.class, () -> service.save(usuario));
        assertTrue(dao.findAll().isEmpty());
    }

    @Test
    public void save_comCpfInvalido_lancaNegocioException() {
        Usuario usuario = usuarioValido("arthur_sousa");
        usuario.setCpf("123");
        assertThrows(NegocioException.class, () -> service.save(usuario));
    }

    @Test
    public void save_comLoginProibido_lancaNegocioException() {
        assertThrows(NegocioException.class, () -> service.save(usuarioValido("admin")));
    }

    @Test
    public void save_comLoginRepetido_lancaNegocioException() {
        service.save(usuarioValido("arthur_sousa"));
        assertThrows(NegocioException.class, () -> service.save(usuarioValido("arthur_sousa")));
    }

    @Test
    public void update_comLoginDeOutroUsuario_lancaNegocioException() {
        service.save(usuarioValido("primeiro"));
        Usuario segundo = service.save(usuarioValido("segundo"));
        segundo.setLogin("primeiro");
        assertThrows(NegocioException.class, () -> service.update(segundo));
    }

    @Test
    public void update_mantendoOProprioLogin_naoLancaExcecao() {
        Usuario salvo = service.save(usuarioValido("arthur_sousa"));
        salvo.setNome("Arthur Barreto");
        assertDoesNotThrow(() -> service.update(salvo));
    }

    @Test
    public void autenticar_comSenhaCorreta_devolveOUsuario() {
        service.save(usuarioValido("arthur_sousa"));
        assertEquals("arthur_sousa", service.autenticar("arthur_sousa", "Senha1234").getLogin());
    }

    @Test
    public void autenticar_comSenhaErrada_lancaNegocioException() {
        service.save(usuarioValido("arthur_sousa"));
        assertThrows(NegocioException.class, () -> service.autenticar("arthur_sousa", "errada"));
    }

    @Test
    public void autenticar_comLoginInexistente_lancaNegocioException() {
        assertThrows(NegocioException.class, () -> service.autenticar("ninguem", "Senha1234"));
    }

    @Test
    public void autenticar_comUsuarioInativo_lancaNegocioException() {
        Usuario usuario = usuarioValido("arthur_sousa");
        usuario.setAtivo(false);
        service.save(usuario);
        assertThrows(NegocioException.class, () -> service.autenticar("arthur_sousa", "Senha1234"));
    }
}