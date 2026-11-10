/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.validar;

/**
 *
 * @author PC
 */

public class ValidadorCadastro {
    public static final int TAMANHO_SENHA_MINIMO = 3;
    public static final int TAMANHO_SENHA_MAXIMO = 20;
    public static boolean cpfValido(String cpf) { //validacao de cpf se tem exatamente 11 digitos ignorando pontuacao e hifen
        if (cpf == null) {
            return false;
        }
        String somenteNumeros = cpf.replaceAll("[^0-9]", "");
        return somenteNumeros.length() == 11;
    }

    public static boolean senhaForte(String senha) { 
        if (senha == null) {
            return false;
        }
        return senha.length() >= TAMANHO_SENHA_MINIMO && senha.length() <= TAMANHO_SENHA_MAXIMO;
    }

    public static boolean camposPreenchidos(String... campos) { //validacao se todos os campos estao preenchidos
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean contemPalavraProibida(String texto) { //validacao se tem alguma palavra proibida do array de logins proibidos
        if (texto == null) {
            return false;
        }
        String[] palavrasProibidas = {"admin", "teste", "root", "senha123"};
        for (String palavra : palavrasProibidas) {
            if (texto.toLowerCase().contains(palavra)) {
                return true;
            }
        }
        return false;
    }
}