package br.com.ifba.usuario.entity;

/**
 * @author Arthur
 */
public class Professor extends Usuario {

    private String disciplina;

    public Professor() {
        super();
        setTipoUsuario(TipoUsuario.PROFESSOR);
    }

    public Professor(String nome, String cpf, String login, String senha, String disciplina) {
        super(nome, cpf, login, senha);
        setTipoUsuario(TipoUsuario.PROFESSOR);
        this.disciplina = disciplina;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }
    
    public String descreverPapel() {
        return "Professor responsavel pela disciplina de " + disciplina;
    }
}