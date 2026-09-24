package br.com.ifba.usuario.entity;

/**
 * Representa um curso que um usuario pode estar matriculado.
 * @author Arthur
 */
public class Curso {

    private String nome;
    private int cargaHoraria;

    public Curso() {
    }

    public Curso(String nome, int cargaHoraria) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
}