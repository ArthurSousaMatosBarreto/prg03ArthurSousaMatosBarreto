package br.com.ifba.usuario.entity;

/**
 * @author Arthur
 */
public class Aluno extends Usuario {

    private String matricula;

    public Aluno() {
        super();
        setTipoUsuario(TipoUsuario.ALUNO);
    }

    public Aluno(String nome, String cpf, String login, String senha, String matricula) {
        super(nome, cpf, login, senha);
        setTipoUsuario(TipoUsuario.ALUNO);
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String descreverPapel() {
        return "Aluno matriculado sob o numero " + matricula;
    }
}