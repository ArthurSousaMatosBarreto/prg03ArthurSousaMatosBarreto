package br.com.ifba.usuario.view;

import br.com.ifba.login.view.TelaLogin;
import br.com.ifba.usuario.dao.UsuarioDao;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import jakarta.persistence.PersistenceException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Tela de listagem de usuarios (CRUD), no formato do slide de exemplo:
 * busca + botao "+" + Homescreen em cima, e a tabela com as colunas
 * REMOVER e EDITAR em cada linha. Apenas componentes: a persistencia
 * entra na Parte 2.
 */
public class TelaListagemUsuario extends JFrame {

    private static final Color AZUL_ESCURO = new Color(23, 42, 69);

    // posicao das colunas de acao na tabela
    private static final int COL_REMOVER = 3;
    private static final int COL_EDITAR = 4;

    private final UsuarioDao dao = new UsuarioDao();
    // usuarios que estao na tabela agora, na mesma ordem das linhas
    private List<Usuario> usuariosExibidos = new ArrayList<>();
    private DefaultTableModel modelo;

    private final CampoComDica txtBusca = new CampoComDica("Procurar por nome...", 14);
    private final JButton btnNovo = new JButton(new IconeMais());
    private final JButton btnHomescreen = new JButton("Homescreen");
    private final JTable tblUsuarios;

    public TelaListagemUsuario() {
        setTitle("Listagem de Usuários");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(760, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(criarBarraSuperior(), BorderLayout.NORTH);

        tblUsuarios = criarTabela();
        add(new JScrollPane(tblUsuarios), BorderLayout.CENTER);

        carregar(); // mostra os usuarios do banco ao abrir a tela
    }

    // barra de cima: busca (esquerda), "+" (centro) e Homescreen (direita)
    private JPanel criarBarraSuperior() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(AZUL_ESCURO);
        barra.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        // campo de busca com a lupa
        JPanel campoBusca = new JPanel(new BorderLayout(6, 0));
        campoBusca.setBackground(Color.WHITE);
        campoBusca.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        JLabel lblLupa = new JLabel(new IconeLupa());
        lblLupa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLupa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                carregar();
            }
        });
        campoBusca.add(lblLupa, BorderLayout.WEST);
        txtBusca.setBorder(null);
        campoBusca.add(txtBusca, BorderLayout.CENTER);
        barra.add(campoBusca, BorderLayout.WEST);

        // botao "+" abre a tela de cadastro
        btnNovo.setToolTipText("Cadastrar novo usuário");
        btnNovo.setPreferredSize(new Dimension(120, 30));
        btnNovo.addActionListener(e -> abrirCadastro());
        JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centro.setOpaque(false);
        centro.add(btnNovo);
        barra.add(centro, BorderLayout.CENTER);

        // volta para a tela inicial do sistema (o login)
        btnHomescreen.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });
        barra.add(btnHomescreen, BorderLayout.EAST);

        // busca por nome (Enter no campo ou clique na lupa); campo vazio mostra todos
        txtBusca.addActionListener(e -> carregar());

        return barra;
    }

    // tabela: NOME | LOGIN | TIPO | REMOVER | EDITAR (sem senha, ver RETROSPECTIVA.md)
    private JTable criarTabela() {
        modelo = new DefaultTableModel(
                new Object[]{"NOME", "LOGIN", "TIPO", "REMOVER", "EDITAR"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int coluna) {
                // colunas de acao guardam icones
                return (coluna == COL_REMOVER || coluna == COL_EDITAR) ? Icon.class : String.class;
            }
        };

        JTable tabela = new JTable(modelo);
        tabela.setRowHeight(34);
        tabela.setShowVerticalLines(false);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getTableHeader().setBackground(AZUL_ESCURO);
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setFont(tabela.getTableHeader().getFont().deriveFont(Font.BOLD));

        // texto centralizado, como no slide
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        tabela.setDefaultRenderer(String.class, centralizado);

        // clique no icone da lixeira abre a confirmacao; no lapis abre a edicao
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.rowAtPoint(e.getPoint());
                int coluna = tabela.columnAtPoint(e.getPoint());
                if (linha < 0 || linha >= usuariosExibidos.size()) {
                    return;
                }
                Usuario usuario = usuariosExibidos.get(tabela.convertRowIndexToModel(linha));
                if (coluna == COL_REMOVER) {
                    confirmarExclusao(usuario);
                } else if (coluna == COL_EDITAR) {
                    abrirEdicao(usuario);
                }
            }
        });
        // cursor de "mao" em cima dos icones
        tabela.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int coluna = tabela.columnAtPoint(e.getPoint());
                boolean emIcone = tabela.rowAtPoint(e.getPoint()) >= 0
                        && (coluna == COL_REMOVER || coluna == COL_EDITAR);
                tabela.setCursor(emIcone ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
            }
        });

        return tabela;
    }
    // busca no banco (todos, ou so os que contem o texto digitado) e atualiza a tabela
    private void carregar() {
        String termo = txtBusca.getText().trim();
        try {
            List<Usuario> lista = termo.isEmpty() ? dao.listar() : dao.buscarPorNome(termo);
            preencherTabela(lista);
        } catch (PersistenceException e) {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível carregar os usuários. Verifique a conexão com o banco de dados.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    // senha nunca vai para a tabela (ver RETROSPECTIVA.md)
    private void preencherTabela(List<Usuario> lista) {
        usuariosExibidos = lista;
        modelo.setRowCount(0);
        for (Usuario u : lista) {
            String tipo = u.getTipoUsuario() == null ? "-" : u.getTipoUsuario().name();
            modelo.addRow(new Object[]{u.getNome(), u.getLogin(), tipo, new IconeLixeira(), new IconeEditar()});
        }
    }
    // quando a outra tela fecha, a listagem busca os dados de novo
    private void recarregarAoFechar(JFrame tela) {
        tela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                carregar();
            }
        });
    }
    private void abrirCadastro() {
        TelaCadastroUsuario tela = new TelaCadastroUsuario();
        // fechar o cadastro pelo X nao pode encerrar o sistema inteiro quando ele foi aberto daqui
        tela.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        recarregarAoFechar(tela);
        tela.setVisible(true);
    }
    private void abrirEdicao(Usuario usuario) {
        TelaEdicaoUsuario tela = new TelaEdicaoUsuario(usuario);
        recarregarAoFechar(tela);
        tela.setVisible(true);
    }
    // JOptionPane pergunta se o usuario deseja excluir; so remove se confirmar
    private void confirmarExclusao(Usuario usuario) {
        int opcao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir este usuário?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (opcao != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            dao.remover(usuario.getId());
            carregar();
            // so chega aqui se removeu de verdade
            JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso!",
                    "Exclusão", JOptionPane.INFORMATION_MESSAGE);
        } catch (UsuarioNaoEncontradoException e) {
            // ja tinha sido apagado por outro lado: avisa e atualiza a lista
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            carregar();
        } catch (PersistenceException e) {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível excluir o usuário. Verifique a conexão com o banco de dados.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
    }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new TelaListagemUsuario().setVisible(true));
    }
    /** Campo de texto que mostra uma dica enquanto esta vazio. */
    private static class CampoComDica extends JTextField {
        private final String dica;

        CampoComDica(String dica, int colunas) {
            super(colunas);
            this.dica = dica;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(Color.GRAY);
                Insets in = getInsets();
                int y = (getHeight() + g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent()) / 2;
                g2.drawString(dica, in.left, y);
                g2.dispose();
    }
    }
    }
    /** Base dos icones 20x20 desenhados com Graphics2D. */
    private abstract static class IconeDesenhado implements Icon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.translate(x, y);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(40, 40, 40));
            g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            desenhar(g2);
            g2.dispose();
        }
        abstract void desenhar(Graphics2D g2);
        @Override
        public int getIconWidth() {
            return 20;
        }
        @Override
        public int getIconHeight() {
            return 20;
        }
    }
    private static class IconeLixeira extends IconeDesenhado {
        @Override
        void desenhar(Graphics2D g2) {
            g2.drawLine(3, 5, 17, 5);          // tampa
            g2.drawRect(8, 2, 4, 3);           // alca
            g2.drawPolygon(new int[]{5, 15, 14, 6}, new int[]{7, 7, 18, 18}, 4); // corpo
            g2.drawLine(8, 9, 8, 15);
            g2.drawLine(10, 9, 10, 15);
            g2.drawLine(12, 9, 12, 15);
        }
    }
    private static class IconeEditar extends IconeDesenhado {
        @Override
        void desenhar(Graphics2D g2) {
            g2.drawRoundRect(3, 4, 13, 13, 3, 3); // quadrado
            g2.setStroke(new BasicStroke(2.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine(8, 12, 16, 4);            // lapis
        }
    }
    private static class IconeLupa extends IconeDesenhado {
        @Override
        void desenhar(Graphics2D g2) {
            g2.drawOval(3, 3, 10, 10);
            g2.drawLine(11, 11, 17, 17);
        }
    }
    private static class IconeMais extends IconeDesenhado {
        @Override
        void desenhar(Graphics2D g2) {
            g2.drawOval(2, 2, 16, 16);
            g2.drawLine(10, 6, 10, 14);
            g2.drawLine(6, 10, 14, 10);
}
}
}