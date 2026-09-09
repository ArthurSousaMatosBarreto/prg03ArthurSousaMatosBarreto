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
    public static boolean contemPalavraProibida(String texto) {
        String[] palavrasProibidas = {"admin", "teste", "root", "senha123"}; //array com as palavras proibidas.
        for (String palavra : palavrasProibidas) { // vai rodar um loop ate comparar o login com todas as palavras do array
            if (texto.equalsIgnoreCase(palavra)) {
                return true; //se for igual vai retornar erro
            }
        }
        return false; //se nao achar, passa.
    }
}