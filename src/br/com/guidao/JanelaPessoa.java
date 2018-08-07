package br.com.guidao;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import br.com.dao.classes.Pessoa;
import br.com.dao.interfaceImpl.PessoaDAOImpl;
import br.com.dao.interfaces.PessoaDAO;

public class JanelaPessoa extends JFrame {

	private static final long serialVersionUID = 8037463915860039350L;
	private JButton buttonCriar;
	private JButton buttonListar;
	private JButton buttonAtualizar;
	private JButton buttonExcluir;
	private GridLayout layoutButtons;
	private JPanel panelButtons;
	private final DefaultTableModel modelo = new DefaultTableModel() {

		private static final long serialVersionUID = 2037585742133551882L;

		@Override
		public boolean isCellEditable(int rowIndex, int mColIndex) {
			return false;
		}
	};
	private JTable table;

	public JanelaPessoa() {
		super("Tabela Pessoas");
		setarBotoes();
		criarTabela();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 200);
		setVisible(true);
	}

	private void setarBotoes() {
		ListenerCriar listenerCriar = new ListenerCriar();
		ListenerListar listenerListar = new ListenerListar();
		ListenerExcluir listenerExcluir = new ListenerExcluir();
		ListenerAtualizar listenerAtualizar = new ListenerAtualizar();

		layoutButtons = new GridLayout(4, 1, 0, 2);

		buttonCriar = new JButton("Criar");
		buttonListar = new JButton("Listar");
		buttonExcluir = new JButton("Excluir");
		buttonAtualizar = new JButton("Atualizar");
		panelButtons = new JPanel(layoutButtons);

		buttonCriar.addActionListener(listenerCriar);
		buttonListar.addActionListener(listenerListar);
		buttonExcluir.addActionListener(listenerExcluir);
		buttonAtualizar.addActionListener(listenerAtualizar);

		panelButtons.add(buttonListar);
		panelButtons.add(buttonCriar);
		panelButtons.add(buttonAtualizar);
		panelButtons.add(buttonExcluir);

		this.add(panelButtons, BorderLayout.WEST);
	}

	private void criarTabela() {
		table = new JTable(modelo);
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setFont(new Font("Serif", Font.BOLD, 20));
		table.setFont(new Font("Serif", Font.PLAIN, 18));

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		modelo.addColumn("ID");
		modelo.addColumn("Nome");
		modelo.addColumn("Sobrenome");
		modelo.addColumn("Data Aniversario");
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(40);

		table.setVisible(false);
		this.add(new JScrollPane(table));

	}

	public static void pesquisar(DefaultTableModel model) {
		model.setRowCount(0);
		PessoaDAO dao = new PessoaDAOImpl();
		for (Pessoa p : dao.getAll()) {
			model.addRow(new Object[] { p.getId(), p.getNome(), p.getSobrenome(), p.getDtAniversario() });
		}
	}

	private class ListenerListar implements ActionListener {
		boolean togle = true;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (togle) {
				pesquisar(modelo);
				table.setVisible(true);
				getContentPane().validate();
				togle = !togle;
			} else {
				table.setVisible(false);
				getContentPane().validate();
				togle = !togle;
			}
		}

	}

	private class ListenerCriar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			CriarPessoa criar = new CriarPessoa(modelo);
			criar.setVisible(true);
		}

	}

	private class ListenerExcluir implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			int linhaSelecionada = -1;
			linhaSelecionada = table.getSelectedRow();
			if (linhaSelecionada >= 0) {
				int i = JOptionPane.showConfirmDialog(JanelaPessoa.this, "Tem certeza de excluir esse registro?",
						"Confirmação", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (i == JOptionPane.YES_OPTION) {
					int id = (int) table.getValueAt(linhaSelecionada, 0);
					PessoaDAO dao = new PessoaDAOImpl();
					dao.delete(id);
					modelo.removeRow(linhaSelecionada);
				}
			} else {
				JOptionPane.showMessageDialog(null, "É necesário selecionar uma linha.");
			}

		}

	}

	private class ListenerAtualizar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			int linhaSelecionada = -1;
			linhaSelecionada = table.getSelectedRow();
			if (linhaSelecionada >= 0) {
				int i = JOptionPane.showConfirmDialog(JanelaPessoa.this,
						"Tem certeza que quer atualizar esse registro?", "Confirmação",
						JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (i == JOptionPane.YES_OPTION) {
					int id = (int) table.getValueAt(linhaSelecionada, 0);
					AtualizarPessoa ap = new AtualizarPessoa(modelo, id, linhaSelecionada);
					ap.setVisible(true);
				}
			} else {
				JOptionPane.showMessageDialog(null, "É necesário selecionar uma linha.");
			}

		}

	}

}