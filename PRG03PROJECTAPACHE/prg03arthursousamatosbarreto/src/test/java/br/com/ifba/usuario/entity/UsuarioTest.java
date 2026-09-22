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
        Usuario usuario = new Usuario("Maria Santos", "12345678900", "maria.santos", "senha1234");
        assertTrue(usuario.autenticar("maria.santos", "senha1234"));
    }

    @Test
    public void autenticar_deveRetornarFalseParaSenhaIncorreta() {
        Usuario usuario = new Usuario("Maria Santos", "12345678900", "maria.santos", "senha1234");
        assertFalse(usuario.autenticar("maria.santos", "senhaErrada"));
    }
}
