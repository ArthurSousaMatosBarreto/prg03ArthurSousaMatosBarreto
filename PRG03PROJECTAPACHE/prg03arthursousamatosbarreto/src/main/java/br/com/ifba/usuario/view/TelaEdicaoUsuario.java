package br.com.ifba.usuario.view;

import br.com.ifba.usuario.dao.UsuarioDao;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import br.com.ifba.usuario.validar.ValidadorCadastro;
import jakarta.persistence.PersistenceException;
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
 * Apenas componentes, a gravacao no banco entra na Parte 2.
 */


public class TelaEdicaoUsuario extends JFrame {

    private final UsuarioDao dao = new UsuarioDao();
    private final Usuario usuario; // o usuario que veio do banco (com id)

    private final JTextField txtNome = new JTextField();
    private final JComboBox<String> cmbGenero = new JComboBox<>(
            new String[]{"Masculino", "Feminino", "Outro", "Prefiro nao dizer"});
    private final JTextField txtLogin = new JTextField();
    private final JButton btnSalvar = new JButton("Salvar alterações");
    private final JButton btnCancelar = new JButton("Cancelar");

    public TelaEdicaoUsuario(Usuario usuario) {
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

        if (!ValidadorCadastro.camposPreenchidos(nome, login)) {
            JOptionPane.showMessageDialog(this, "Preencha nome e login.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (ValidadorCadastro.contemPalavraProibida(login)) {
            JOptionPane.showMessageDialog(this, "Login contém palavra não permitida.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // altera so nome, genero e login NO usuario que veio do banco,
            // assim senha, cpf e o resto continuam como estavam
            usuario.setNome(nome);
            usuario.setGenero((String) cmbGenero.getSelectedItem());
            usuario.setLogin(login);

            dao.atualizar(usuario);

            // a mensagem de sucesso so aparece depois de gravar de verdade
            JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!",
                    "Edição", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (UsuarioNaoEncontradoException e) {
            // o usuario foi apagado enquanto a tela estava aberta
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            dispose();
        } catch (IllegalArgumentException e) {
            // login ja usado por outro usuario
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (PersistenceException e) {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível salvar as alterações. Verifique a conexão com o banco de dados.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}