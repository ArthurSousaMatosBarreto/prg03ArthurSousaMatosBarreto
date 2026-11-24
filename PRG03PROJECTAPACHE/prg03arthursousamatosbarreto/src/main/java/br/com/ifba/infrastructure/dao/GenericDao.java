package br.com.ifba.infrastructure.dao;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.infrastructure.exception.PersistenciaException;
import br.com.ifba.infrastructure.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class GenericDao<Entity extends PersistenceEntity> implements GenericIDao<Entity> {

    private final Class<Entity> tipo;

    public GenericDao(Class<Entity> tipo) {
        this.tipo = tipo; // quem instancia diz qual entidade é
    }

    // único ponto que pega o EntityManager (o teste da Task 06 troca por um de mentira)
    protected EntityManager abrirEntityManager() {
        return JPAUtil.getEntityManager();
    }

    @Override
    public Entity save(Entity entity) {
        EntityManager em = abrirEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (RuntimeException e) {
            desfazer(em);
            throw new PersistenciaException("Erro ao salvar", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Entity update(Entity entity) {
        EntityManager em = abrirEntityManager();
        try {
            em.getTransaction().begin();
            Entity atualizada = em.merge(entity);
            em.getTransaction().commit();
            return atualizada;
        } catch (RuntimeException e) {
            desfazer(em);
            throw new PersistenciaException("Erro ao atualizar", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Entity entity) {
        EntityManager em = abrirEntityManager();
        try {
            em.getTransaction().begin();
            Entity existente = em.find(tipo, entity.getId());
            if (existente != null) {
                em.remove(existente);
            }
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            desfazer(em);
            throw new PersistenciaException("Erro ao excluir", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Entity findById(Long id) {
        EntityManager em = abrirEntityManager();
        try {
            return em.find(tipo, id);
        } catch (RuntimeException e) {
            throw new PersistenciaException("Erro ao buscar por id", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Entity> findAll() {
        EntityManager em = abrirEntityManager();
        try {
            // o nome da classe vem do tipo, nunca do usuário
            String jpql = "SELECT e FROM " + tipo.getSimpleName() + " e";
            return em.createQuery(jpql, tipo).getResultList();
        } catch (RuntimeException e) {
            throw new PersistenciaException("Erro ao listar", e);
        } finally {
            em.close();
        }
    }

    // só dá rollback se a transação chegou a abrir
    private void desfazer(EntityManager em) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}