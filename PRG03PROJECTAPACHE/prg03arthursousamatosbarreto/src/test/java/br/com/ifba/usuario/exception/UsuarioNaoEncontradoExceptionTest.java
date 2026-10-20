/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioNaoEncontradoExceptionTest {

    @Test
    public void construtorComId_montaMensagemExplicativa() {
        UsuarioNaoEncontradoException erro = new UsuarioNaoEncontradoException(7L);
        assertEquals("Usuário com id 7 não foi encontrado.", erro.getMessage());
    }

    @Test
    public void construtorComLogin_montaMensagemExplicativa() {
        UsuarioNaoEncontradoException erro = new UsuarioNaoEncontradoException("arthur_sousa");
        assertTrue(erro.getMessage().contains("arthur_sousa"));
    }

    @Test
    public void ehUmaRuntimeException_naoExigeTryCatchObrigatorio() {
        assertTrue(RuntimeException.class.isAssignableFrom(UsuarioNaoEncontradoException.class));
    }
}