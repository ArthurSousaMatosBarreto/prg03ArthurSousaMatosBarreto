/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.entity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author PC
 */
public class UsuarioTest {
    @Test
    public void autenticar_deveRetornarTrueParaCredenciaisCorretas() {
        Usuario usuario = new Usuario("Arthur Sousa", "5729589233", "arthur_sousa", "arthur0910");
        assertTrue(usuario.autenticar("arthur_sousa", "arthur0910"));
    }

    @Test
    public void autenticar_deveRetornarFalseParaSenhaIncorreta() {
        Usuario usuario = new Usuario("Arthur Sousa", "5729589233", "arthur_sousa", "arthur0910");
        assertFalse(usuario.autenticar("arthur_sousa", "senhaErrada"));
    }
}