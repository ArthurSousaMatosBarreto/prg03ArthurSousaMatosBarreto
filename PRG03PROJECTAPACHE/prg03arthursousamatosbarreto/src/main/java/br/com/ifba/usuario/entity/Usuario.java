/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.entity;
import br.com.ifba.usuario.interfaces.Autenticavel;
import java.util.List; //add de lista
import java.util.ArrayList; //add de array
 
/**
 *
 * @author PC
 */
public class Usuario implements Autenticavel{
    
    private String txtnome;
    private String txtcpf;
    private String combgen;
    private String txtdata;
    private String txt_telefone;
    private String txtemail;
    private String txtlogin;
    private String txtsenha;
    private Endereco endereco;
    private TipoUsuario tipoUsuario;
    
    @Override
 
    public boolean autenticar(String login, String senha) { //compara os parametros recebidos com os internos
        return this.txtlogin.equals(login) && this.txtsenha.equals(senha); //sem acessar de fora, ja que login e senha continua private
    }
    public Usuario() { //construtor vazio
    }

    public Usuario(String nome, String cpf, String login, String senha) { //construtor com atributos
        this.txtnome = nome;
        this.txtcpf = cpf;
        this.txtlogin = login;
        this.txtsenha = senha;
    }
    public String getNome() { //getters e setters
        return txtnome;
    }
    public void setNome(String nome) {
        this.txtnome = nome;
    }
    public String getCpf() {
        return txtcpf;
    }
    public void setCpf(String cpf) {
        this.txtcpf = cpf;
    }
    public String getGenero() {
        return combgen;
    }
    public void setGenero(String genero) {
        this.combgen = genero;
    }
    public String getDataNascimento() {
        return txtdata;
    }
    public void setDataNascimento(String dataNascimento) {
        this.txtdata = dataNascimento;
    }
    public String getTelefone() {
        return txt_telefone;
    }
    public void setTelefone(String telefone) {
        this.txt_telefone = telefone;
    }
    public String getEmail() {
        return txtemail;
    }
    public void setEmail(String email) {
        this.txtemail = email;
    }
    public String getLogin() {
        return txtlogin;
    }
    public void setLogin(String login) {
        this.txtlogin = login;
    }
    public String getSenha() {
        return txtsenha;
    }
    public void setSenha(String senha) {
        this.txtsenha = senha;
    }
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
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
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
