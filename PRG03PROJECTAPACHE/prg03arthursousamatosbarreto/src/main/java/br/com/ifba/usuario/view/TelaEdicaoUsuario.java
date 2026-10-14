package br.com.ifba.usuario.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Tela de edicao de usuario (CRUD). Edita apenas nome, genero e login.
 * Apenas componentes, a gravacao no banco entra na Parte 2.
 */
public class TelaEdicaoUsuario extends JFrame {

    private final JTextField txtNome = new JTextField();
    private final JComboBox<String> cmbGenero = new JComboBox<>(
            new String[]{"Masculino", "Feminino", "Outro", "Prefiro nao dizer"});
    private final JTextField txtLogin = new JTextField();
    private final JButton btnSalvar = new JButton("Salvar alterações");
    private final JButton btnCancelar = new JButton("Cancelar");

    public TelaEdicaoUsuario() {
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

        // salvar: sem logica ainda (Parte 2)
        btnSalvar.addActionListener(e -> { });

        // cancelar: fecha a tela de edicao
        btnCancelar.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new TelaEdicaoUsuario().setVisible(true));
    }
}
