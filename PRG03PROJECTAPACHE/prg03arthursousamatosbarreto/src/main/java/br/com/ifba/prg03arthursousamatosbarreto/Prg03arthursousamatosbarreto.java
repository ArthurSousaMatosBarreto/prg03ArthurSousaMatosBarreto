package br.com.ifba.prg03arthursousamatosbarreto;

import br.com.ifba.infrastructure.util.JPAUtil;
import br.com.ifba.login.view.TelaLogin;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.flywaydb.core.Flyway;

public class Prg03arthursousamatosbarreto {

    public static void main(String[] args) {
        // 1º: aplica as migrations pendentes (antes de qualquer tela)
        try {
            Properties config = new Properties();
            try (InputStream in = Prg03arthursousamatosbarreto.class
                    .getResourceAsStream("/db.properties")) {
                config.load(in);
            }
            Flyway flyway = Flyway.configure()
                    .dataSource(config.getProperty("db.url"),
                                config.getProperty("db.user"),
                                config.getProperty("db.password"))
                    .load();
            flyway.migrate();
        } catch (IOException | RuntimeException e) {
            JOptionPane.showMessageDialog(null,
                    "Não foi possível atualizar o banco de dados:\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return; // sem banco atualizado, não abre o sistema
        }

        // 2º: só depois a aplicação sobe
        Runtime.getRuntime().addShutdownHook(new Thread(JPAUtil::fechar));
        java.awt.EventQueue.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}