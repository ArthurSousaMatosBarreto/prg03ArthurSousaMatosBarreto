package br.com.ifba.usuario.view;

import br.com.ifba.infrastructure.exception.NegocioException;
import br.com.ifba.infrastructure.exception.PersistenciaException;
import br.com.ifba.usuario.controller.UsuarioIController;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import br.com.ifba.usuario.validar.ValidadorCadastro;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Tela de edicao de usuario (CRUD). Edita apenas nome, genero e login.
 * Fala so com o Controller: as regras (login proibido, login repetido)
 * ficam no Service.
 */

public class TelaEdicaoUsuario extends JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(TelaEdicaoUsuario.class.getName());

    // recebido de fora (injecao de dependencia): a tela nao cria o controller
    private final UsuarioIController controller;
    private final Usuario usuario; // o usuario que veio do banco (com id)

    private final JTextField txtNome = new JTextField();
    private final JComboBox<String> cmbGenero = new JComboBox<>(
            new String[]{"Masculino", "Feminino", "Outro", "Prefiro nao dizer"});
    private final JTextField txtLogin = new JTextField();
    private final JButton btnSalvar = new JButton("Salvar alterações");
    private final JButton btnCancelar = new JButton("Cancelar");

    public TelaEdicaoUsuario(UsuarioIController controller, Usuario usuario) {
        this.controller = controller;
        this.usuario = usuario;

        setTitle("Tela de Edição");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(480, 230);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel campos = new JPanel(new GridLayout(0, 2, 8, 8));
        campos.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        campos.add(new JLabel("Nome Completo:")); campos.add(txtNome);
        campos.add(new JLabel("Genero:"));        campos.add(cmbGenero);
        campos.add(new JLabel("Login:"));         campos.add(txtLogin);
        add(campos, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(btnSalvar);
        botoes.add(btnCancelar);
        add(botoes, BorderLayout.SOUTH);

        // mostra os valores atuais do usuario escolhido
        txtNome.setText(usuario.getNome());
        cmbGenero.setSelectedItem(usuario.getGenero());
        txtLogin.setText(usuario.getLogin());

        btnSalvar.addActionListener(e -> salvar());

        // cancelar: fecha a tela de edicao
        btnCancelar.addActionListener(e -> dispose());
    }

    private void salvar() {
        String nome = txtNome.getText().trim();
        String login = txtLogin.getText().trim();

        // a tela so confere o que e conveniencia de formulario
        if (!ValidadorCadastro.camposPreenchidos(nome, login)) {
            JOptionPane.showMessageDialog(this, "Preencha nome e login.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // altera so nome, genero e login NO usuario que veio do banco,
            // assim senha, cpf e o resto continuam como estavam
            usuario.setNome(nome);
            usuario.setGenero((String) cmbGenero.getSelectedItem());
            usuario.setLogin(login);

            controller.update(usuario);

            // a mensagem de sucesso so aparece depois de gravar de verdade
            JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!",
                    "Edição", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (UsuarioNaoEncontradoException e) {
            // o usuario foi apagado enquanto a tela estava aberta
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            dispose();
        } catch (NegocioException e) {
            // regra do negocio (login proibido ou ja usado por outro usuario)
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (PersistenciaException e) {
            logger.log(java.util.logging.Level.SEVERE, "Falha ao atualizar usuário", e);
            JOptionPane.showMessageDialog(this,
                    "Não foi possível salvar as alterações. Verifique a conexão com o banco de dados.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}