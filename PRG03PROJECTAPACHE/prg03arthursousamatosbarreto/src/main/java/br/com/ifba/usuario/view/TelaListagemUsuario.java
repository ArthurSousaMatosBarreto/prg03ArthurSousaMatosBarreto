package br.com.ifba.usuario.view;

import br.com.ifba.login.view.TelaLogin;
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

    private final CampoComDica txtBusca = new CampoComDica("Procurar...", 14);
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
        campoBusca.add(new JLabel(new IconeLupa()), BorderLayout.WEST);
        txtBusca.setBorder(null);
        campoBusca.add(txtBusca, BorderLayout.CENTER);
        barra.add(campoBusca, BorderLayout.WEST);

        // botao "+" abre a tela de cadastro
        btnNovo.setToolTipText("Cadastrar novo usuário");
        btnNovo.setPreferredSize(new Dimension(120, 30));
        btnNovo.addActionListener(e -> new TelaCadastroUsuario().setVisible(true));
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

        // busca na lista abaixo: sem logica ainda (Parte 2)
        txtBusca.addActionListener(e -> { });

        return barra;
    }

    // tabela: NOME | LOGIN | TIPO | REMOVER | EDITAR (sem senha, ver RETROSPECTIVA.md)
    private JTable criarTabela() {
        DefaultTableModel modelo = new DefaultTableModel(
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

        // linhas de exemplo, so para a tela nao ficar vazia: a Parte 2 troca pelos dados do banco
        modelo.addRow(new Object[]{"Arthur Sousa", "arthur_sousa", "ALUNO", new IconeLixeira(), new IconeEditar()});
        modelo.addRow(new Object[]{"Jonatas Bastos", "jonatas", "PROFESSOR", new IconeLixeira(), new IconeEditar()});

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
                if (linha < 0) {
                    return;
                }
                if (coluna == COL_REMOVER) {
                    confirmarExclusao();
                } else if (coluna == COL_EDITAR) {
                    new TelaEdicaoUsuario().setVisible(true);
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

    // JOptionPane que pergunta se o usuario deseja excluir
    private void confirmarExclusao() {
        int opcao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir este usuário?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
            // exclusao de verdade fica para a Parte 2
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new TelaListagemUsuario().setVisible(true));
    }

    // ---------------------------------------------------------------
    // Componentes auxiliares (icones desenhados em codigo, sem arquivos)
    // ---------------------------------------------------------------

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
