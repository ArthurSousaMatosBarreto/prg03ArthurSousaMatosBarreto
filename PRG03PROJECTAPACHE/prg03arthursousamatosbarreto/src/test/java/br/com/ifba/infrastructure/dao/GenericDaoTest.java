package br.com.ifba.infrastructure.dao;

import br.com.ifba.infrastructure.exception.PersistenciaException;
import br.com.ifba.usuario.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GenericDaoTest {

    // EntityManager de mentira: se "falhar" for true, as operações lançam erro
    private static EntityManager emDeMentira(boolean falhar) {
        ClassLoader loader = GenericDaoTest.class.getClassLoader();
        EntityTransaction tx = (EntityTransaction) Proxy.newProxyInstance(loader,
                new Class<?>[]{EntityTransaction.class},
                (proxy, metodo, args) -> metodo.getName().equals("isActive") ? Boolean.TRUE : null);
        return (EntityManager) Proxy.newProxyInstance(loader,
                new Class<?>[]{EntityManager.class},
                (proxy, metodo, args) -> {
                    switch (metodo.getName()) {
                        case "getTransaction":
                            return tx;
                        case "persist":
                        case "merge":
                        case "remove":
                        case "find":
                            if (falhar) {
                                throw new IllegalStateException("falha simulada");
                            }
                            return null;
                        default:
                            return null; // begin/commit/close...
                    }
                });
    }

    private static GenericDao<Usuario> daoCom(boolean falhar) {
        return new GenericDao<Usuario>(Usuario.class) {
            @Override
            protected EntityManager abrirEntityManager() {
                return emDeMentira(falhar);
            }
        };
    }

    @Test
    public void save_quandoOBancoFalha_lancaPersistenciaException() {
        PersistenciaException erro = assertThrows(PersistenciaException.class,
                () -> daoCom(true).save(new Usuario()));
        assertTrue(erro.getCause() instanceof IllegalStateException);
    }

    @Test
    public void update_quandoOBancoFalha_lancaPersistenciaException() {
        assertThrows(PersistenciaException.class, () -> daoCom(true).update(new Usuario()));
    }

    @Test
    public void delete_quandoOBancoFalha_lancaPersistenciaException() {
        assertThrows(PersistenciaException.class, () -> daoCom(true).delete(new Usuario()));
    }

    @Test
    public void save_noCaminhoNormal_naoLancaExcecao() {
        Usuario usuario = new Usuario();
        assertDoesNotThrow(() -> daoCom(false).save(usuario));
    }
}