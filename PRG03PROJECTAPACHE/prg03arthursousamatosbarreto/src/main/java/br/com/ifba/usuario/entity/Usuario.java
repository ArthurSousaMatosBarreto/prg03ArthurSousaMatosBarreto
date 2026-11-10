/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.entity;

import br.com.ifba.usuario.interfaces.Autenticavel;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
 
/**
 *
 * @author PC
 */
@Entity
@Table(name = "usuarios")
public class Usuario implements Autenticavel{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "txtnome", nullable = false)
    private String nome;

    @Column(name = "txtcpf", nullable = false, length = 14)
    private String cpf;

    @Column(name = "combgen")
    private String genero;

    @Column(name = "data_nascimento")
    private String dataNascimento;

    @Column(name = "txt_telefone")
    private String telefone;

    @Column(name = "txtemail")
    private String email;

    @Column(name = "txtlogin", nullable = false, unique = true)
    private String login;

    @Column(name = "txtsenha", nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    private TipoUsuario tipoUsuario;

    @Transient
    private Endereco endereco; //posteriormente vai virar relacionamento mapeado
    
    @Override
 
    public boolean autenticar(String login, String senha) { //compara os parametros recebidos com os internos
        if (this.login == null || this.senha == null) {
            return false;
        }
        return this.login.equals(login) && this.senha.equals(senha);
    }
    public Usuario() { //construtor vazio
        this.tipoUsuario = TipoUsuario.ALUNO; //valor padrao p tipoUsuario
    }

    public Usuario(String nome, String cpf, String login, String senha) { //construtor com atributos
        this.nome = nome;
        this.cpf = cpf;
        this.login = login;
        this.senha = senha;
        this.tipoUsuario = TipoUsuario.ALUNO;
    }
    public String getNome() { //getters e setters
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    @Transient
    private List<Curso> cursos = new ArrayList<>();

         // nao expoe a lista diretamente devolvendo copia
    public List<Curso> getCursos() {
        return new ArrayList<>(cursos);
    }

         // metodo que adiciona um curso sem deixar lista exposta, pois se retornar normal a pessoa pode alterar a lista original
    public void adicionarCurso(Curso curso) {
        this.cursos.add(curso);
    }
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
    protected void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    public String descreverPapel() {
    return "Usuario do sistema academico";
}
    @Override
public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
        return false;
    }
    Usuario outro = (Usuario) obj;
    return Objects.equals(this.login, outro.login);
}

@Override
public int hashCode() {
    return Objects.hash(login);
}

 public Long getId() { return id; }
}
