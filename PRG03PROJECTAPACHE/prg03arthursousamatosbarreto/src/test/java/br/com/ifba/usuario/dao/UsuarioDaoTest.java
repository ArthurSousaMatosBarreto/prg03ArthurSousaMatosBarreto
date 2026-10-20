/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.dao;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDaoTest {

    @Test
    public void exigirEncontrado_comUsuarioNull_lancaExcecao() {
        UsuarioNaoEncontradoException erro = assertThrows(UsuarioNaoEncontradoException.class,
                () -> UsuarioDao.exigirEncontrado(null, 99L));
        assertTrue(erro.getMessage().contains("99"));
    }

    @Test
    public void exigirEncontrado_comUsuarioExistente_naoLancaExcecao() {
        Usuario usuario = new Usuario("Arthur Sousa", "5729589233", "arthur_sousa", "arthur0910");
        assertDoesNotThrow(() -> UsuarioDao.exigirEncontrado(usuario, 1L));
    }

    @Test
    public void exigirEncontrado_comUsuarioExistente_devolveOMesmoUsuario() {
        Usuario usuario = new Usuario("Arthur Sousa", "5729589233", "arthur_sousa", "arthur0910");
        assertSame(usuario, UsuarioDao.exigirEncontrado(usuario, 1L));
    }
}