package br.com.ifba.prg03arthursousamatosbarreto;

import br.com.ifba.infrastructure.util.JPAUtil;
import br.com.ifba.login.view.TelaLogin;
import br.com.ifba.usuario.controller.UsuarioController;
import br.com.ifba.usuario.controller.UsuarioIController;
import br.com.ifba.usuario.dao.UsuarioDao;
import br.com.ifba.usuario.dao.UsuarioIDao;
import br.com.ifba.usuario.service.UsuarioIService;
import br.com.ifba.usuario.service.UsuarioService;
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
        // monta as camadas, de baixo pra cima; só o main sabe quais classes existem
        UsuarioIDao dao = new UsuarioDao();
        UsuarioIService service = new UsuarioService(dao);
        UsuarioIController controller = new UsuarioController(service);

        Runtime.getRuntime().addShutdownHook(new Thread(JPAUtil::fechar));
        java.awt.EventQueue.invokeLater(() -> new TelaLogin(controller).setVisible(true));
    }
}