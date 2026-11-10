package br.com.ifba.prg03arthursousamatosbarreto;

import br.com.ifba.infrastructure.util.JPAUtil;
import br.com.ifba.login.view.TelaLogin;

public class Prg03arthursousamatosbarreto {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(JPAUtil::fechar)); //fecha o JPA qnd o sistema encerra
        java.awt.EventQueue.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}