/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.infrastructure.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

public final class JPAUtil {

    // mesmo nome da persistence-unit do persistence.xml
    private static final String UNIDADE = "sistemaAcademicoPU";

    private static EntityManagerFactory fabrica;

    private JPAUtil() { }

    // cria a fábrica só na primeira chamada e reaproveita depois
    private static synchronized EntityManagerFactory getFabrica() {
        if (fabrica == null) {
            try {
                fabrica = Persistence.createEntityManagerFactory(UNIDADE);
            } catch (PersistenceException e) {
                throw e;
            } catch (RuntimeException e) {
                throw new PersistenceException("Não foi possível conectar ao banco de dados.", e);
            }
        }
        return fabrica;
    }

    // cada operação pega o seu EntityManager e fecha no finally
    public static EntityManager getEntityManager() {
        return getFabrica().createEntityManager();
    }

    public static synchronized void fechar() {
        if (fabrica != null && fabrica.isOpen()) {
            fabrica.close();
        }
        fabrica = null;
    }
}