package br.com.ifba.usuario.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PolimorfismoTest {

    @Test
    public void descrever_comAluno_devolveMensagemDoAluno() {
        Usuario usuario = new Aluno("Arthur Sousa", "5729589233", "arthur_sousa", "arthur0910", "2024001");

        String resultado = descreverPapel.descrever(usuario);

        assertTrue(resultado.contains("2024001"));
    }

    @Test
    public void descrever_comProfessor_devolveMensagemDoProfessor() {
        Usuario usuario = new Professor("Jonatas Bastos", "11122233344", "jonatas", "senha123", "POO");

        String resultado = descreverPapel.descrever(usuario);

        assertTrue(resultado.contains("POO"));
    }

    @Test
    public void descrever_alunoEProfessor_devolvemMensagensDiferentes() {
        Usuario aluno = new Aluno("Arthur Sousa", "5729589233", "arthur_sousa", "arthur0910", "2024001");
        Usuario professor = new Professor("Jonatas Bastos", "11122233344", "jonatas", "senha123", "POO");

        assertNotEquals(descreverPapel.descrever(aluno), descreverPapel.descrever(professor));
    }
}