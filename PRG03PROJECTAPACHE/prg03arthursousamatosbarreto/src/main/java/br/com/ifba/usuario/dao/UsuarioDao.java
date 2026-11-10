/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.dao;

import br.com.ifba.infrastructure.util.JPAUtil;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UsuarioDao {

    // cadastrar: persist dentro de transação
    public void salvar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            if (loginJaExiste(em, usuario.getLogin(), null)) {
                throw new IllegalArgumentException("Já existe um usuário com o login: " + usuario.getLogin());
            }
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // editar: merge dentro de transação
    public Usuario atualizar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            exigirEncontrado(em.find(Usuario.class, usuario.getId()), usuario.getId());
            if (loginJaExiste(em, usuario.getLogin(), usuario.getId())) {
                throw new IllegalArgumentException("Já existe um usuário com o login: " + usuario.getLogin());
            }
            Usuario atualizado = em.merge(usuario);
            em.getTransaction().commit();
            return atualizado;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // excluir: remove dentro de transação
    public void remover(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Usuario usuario = exigirEncontrado(em.find(Usuario.class, id), id);
            em.remove(usuario);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Usuario buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return exigirEncontrado(em.find(Usuario.class, id), id);
        } finally {
            em.close();
        }
    }

    public List<Usuario> listar() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u ORDER BY u.txtnome", Usuario.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Task 07: JPQL com where, parâmetro nomeado (nunca concatenar texto na consulta)
    public List<Usuario> buscarPorNome(String termo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM Usuario u WHERE LOWER(u.txtnome) LIKE LOWER(:termo) ORDER BY u.txtnome",
                    Usuario.class)
                    .setParameter("termo", "%" + termo + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // true se já existe OUTRO usuário com esse login
    private boolean loginJaExiste(EntityManager em, String login, Long idIgnorado) {
        String jpql = "SELECT COUNT(u) FROM Usuario u WHERE u.txtlogin = :login";
        if (idIgnorado != null) {
            jpql += " AND u.id <> :id";
        }
        TypedQuery<Long> consulta = em.createQuery(jpql, Long.class).setParameter("login", login);
        if (idIgnorado != null) {
            consulta.setParameter("id", idIgnorado);
        }
        return consulta.getSingleResult() > 0;
    }

    // regra que dispara a exceção, isolada para testar sem banco (Task 08)
    static Usuario exigirEncontrado(Usuario usuario, Long id) {
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException(id);
        }
        return usuario;
    }
}