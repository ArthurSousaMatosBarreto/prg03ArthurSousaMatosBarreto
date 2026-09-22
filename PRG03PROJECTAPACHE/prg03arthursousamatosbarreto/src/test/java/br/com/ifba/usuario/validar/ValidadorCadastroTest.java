/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.validar;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author PC
 */
public class ValidadorCadastroTest {
    @Test
    public void cpfValido_deveRetornarTrueParaCpfComOnzeDigitos() {
        assertTrue(ValidadorCadastro.cpfValido("12345678900"));
    }

    @Test
    public void cpfValido_deveRetornarFalseParaCpfVazio() {
        assertFalse(ValidadorCadastro.cpfValido(""));
    }

    @Test
    public void cpfValido_deveRetornarFalseParaCpfComLetras() {
        assertFalse(ValidadorCadastro.cpfValido("abc.def.ghi-00"));
    }

    @Test
    public void cpfValido_deveRetornarFalseParaCpfNulo() {
        assertFalse(ValidadorCadastro.cpfValido(null));
    }

    @Test
    public void senhaForte_deveRetornarTrueParaSenhaValida() {
        assertTrue(ValidadorCadastro.senhaForte("senha123"));
    }

    @Test
    public void senhaForte_deveRetornarFalseParaSenhaMenorQueMinimo() {
        assertFalse(ValidadorCadastro.senhaForte("ab"));
    }

    @Test
    public void senhaForte_deveRetornarFalseParaSenhaNula() {
        assertFalse(ValidadorCadastro.senhaForte(null));
    }

    @Test
    public void camposPreenchidos_deveRetornarTrueQuandoTodosPreenchidos() {
        assertTrue(ValidadorCadastro.camposPreenchidos("Arthur", "12345678900"));
    }

    @Test
    public void camposPreenchidos_deveRetornarFalseQuandoAlgumVazio() {
        assertFalse(ValidadorCadastro.camposPreenchidos("Arthur", ""));
    }

    @Test
    public void camposPreenchidos_deveRetornarFalseQuandoAlgumNulo() {
        assertFalse(ValidadorCadastro.camposPreenchidos("Arthur", null));
    }
}
