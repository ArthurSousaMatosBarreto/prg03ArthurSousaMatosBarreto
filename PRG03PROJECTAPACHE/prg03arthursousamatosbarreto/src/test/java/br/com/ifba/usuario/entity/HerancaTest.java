package br.com.ifba.usuario.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HerancaTest {

    @Test
    public void aluno_deveUsarGetNomeHerdadoDeUsuario() {
        Aluno aluno = new Aluno("Arthur Sousa", "5729589233", "arthur_sousa", "arthur0910", "2024001");
        assertEquals("Arthur Sousa", aluno.getNome());
    }

    @Test
    public void professor_deveUsarAutenticarHerdadoDeUsuario() {
        Professor professor = new Professor("Jonatas Bastos", "11122233344", "jonatas", "senha123", "POO");
        assertTrue(professor.autenticar("jonatas", "senha123"));
    }

    @Test
    public void aluno_deveDevolverDescricaoPropriaDoPapel() {
        Aluno aluno = new Aluno("Arthur Sousa", "5729589233", "arthur_sousa", "arthur0910", "2024001");
        assertEquals("Aluno matriculado sob o numero 2024001", aluno.descreverPapel());
    }

    @Test
    public void professor_deveDevolverDescricaoPropriaDoPapel() {
        Professor professor = new Professor("Jonatas Bastos", "11122233344", "jonatas", "senha123", "POO");
        assertEquals("Professor responsavel pela disciplina de POO", professor.descreverPapel());
    }

    @Test
    public void aluno_eProfessor_devemTerDescricoesDiferentes() {
        Aluno aluno = new Aluno("Arthur Sousa", "5729589233", "arthur_sousa", "arthur0910", "2024001");
        Professor professor = new Professor("Jonatas Bastos", "11122233344", "jonatas", "senha123", "POO");

        assertNotEquals(aluno.descreverPapel(), professor.descreverPapel());
    }
}