package br.com.ifba.usuario.dao;

import br.com.ifba.infrastructure.dao.GenericDao;
import br.com.ifba.infrastructure.exception.PersistenciaException;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UsuarioDao extends GenericDao<Usuario> implements UsuarioIDao {

    public UsuarioDao() {
        super(Usuario.class);
    }
    // continua lançando a exceção própria em vez de devolver null
    @Override
    public Usuario findById(Long id) {
        return exigirEncontrado(super.findById(id), id);
    }

    @Override
    public Usuario findByLogin(String login) {
        EntityManager em = abrirEntityManager();
        try {
            List<Usuario> resultado = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class)
                    .setParameter("login", login)
                    .getResultList();
            if (resultado.isEmpty()) {
                throw new UsuarioNaoEncontradoException(login);
            }
            return resultado.get(0);
        } catch (UsuarioNaoEncontradoException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new PersistenciaException("Erro ao buscar por login", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Usuario> findByNome(String termo) {
        EntityManager em = abrirEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(:termo) ORDER BY u.nome",
                    Usuario.class)
                    .setParameter("termo", "%" + termo + "%")
                    .getResultList();
        } catch (RuntimeException e) {
            throw new PersistenciaException("Erro ao buscar por nome", e);
        } finally {
            em.close();
        }
    }

    // true se já existe OUTRO usuário com esse login
    @Override
    public boolean existsByLogin(String login, Long idIgnorado) {
        EntityManager em = abrirEntityManager();
        try {
            String jpql = "SELECT COUNT(u) FROM Usuario u WHERE u.login = :login";
            if (idIgnorado != null) {
                jpql += " AND u.id <> :id";
            }
            TypedQuery<Long> consulta = em.createQuery(jpql, Long.class).setParameter("login", login);
            if (idIgnorado != null) {
                consulta.setParameter("id", idIgnorado);
            }
            return consulta.getSingleResult() > 0;
        } catch (RuntimeException e) {
            throw new PersistenciaException("Erro ao verificar login", e);
        } finally {
            em.close();
        }
    }

    // regra que dispara a exceção, isolada para testar sem banco
    static Usuario exigirEncontrado(Usuario usuario, Long id) {
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException(id);
        }
        return usuario;
    }
}