package br.com.ifba.usuario.entity;

/**
 * demonstra polimorfismo: recebe tipo geral usuario sem saber se e aluno ou professor
 *
 */
public class descreverPapel {
    public static String descrever(Usuario usuario) { //recebe tipo abstrato (usuario), nunca tipo concreto
        return usuario.descreverPapel();
    }
}