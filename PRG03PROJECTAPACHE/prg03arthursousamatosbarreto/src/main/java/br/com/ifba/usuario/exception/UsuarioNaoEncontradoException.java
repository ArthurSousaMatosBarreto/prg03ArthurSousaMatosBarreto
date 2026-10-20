/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException(Long id) {
        super("Usuário com id " + id + " não foi encontrado.");
    }

    public UsuarioNaoEncontradoException(String login) {
        super("Usuário com login \"" + login + "\" não foi encontrado.");
    }
}