package br.com.guidao;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import br.com.dao.classes.Pessoa;
import br.com.dao.interfaceImpl.PessoaDAOImpl;
import br.com.dao.interfaces.PessoaDAO;

import static br.com.dao.classes.Pessoa.REGEX_DATA;

public class AtualizarPessoa extends JFrame {

	private static final long serialVersionUID = -3403439666276147613L;
	private DefaultTableModel modelo = new DefaultTableModel();
	private JPanel painel;
	private JButton btSalvar;
	private JButton btLimpar;
	private JLabel lbNome;
	private JLabel lbSobrenome;
	private JLabel lbDataAniversario;
	private JLabel lbId;
	private JTextField txNome;
	private JTextField txSobrenome;
	private JFormattedTextField txDataAniversario;
	private JTextField txId;
	private Pessoa pessoa;
	private final static MaskFormatter MASCARA_DATE = new MaskFormatter();
	private int linhaSelecionada;

	public AtualizarPessoa(DefaultTableModel md, int id, int linha) {
		super("Contatos");
		criaJanela();
		modelo = md;
		PessoaDAO dao = new PessoaDAOImpl();
		pessoa = dao.getById(id);
		txId.setText(Integer.toString(pessoa.getId()));
		txNome.setText(pessoa.getNome());
		txSobrenome.setText(pessoa.getSobrenome());
		txDataAniversario.setText(pessoa.getDtAniversario());
		linhaSelecionada = linha;
	}

	public void criaJanela() {
		btSalvar = new JButton("Salvar");
		btLimpar = new JButton("Limpar");
		lbNome = new JLabel("         Nome.:   ");
		lbSobrenome = new JLabel("         Telefone.:   ");
		lbDataAniversario = new JLabel("         Email.:   ");
		lbId = new JLabel("         Id.:   ");
		txNome = new JTextField();
		txSobrenome = new JTextField();
		try {
			MASCARA_DATE.setMask("##/##/####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		txDataAniversario = new JFormattedTextField(MASCARA_DATE);
		txId = new JTextField();
		txId.setEditable(false);

		painel = new JPanel();
		painel.setLayout(new GridLayout(5, 2, 2, 4));
		painel.add(lbId);
		painel.add(txId);
		painel.add(lbNome);
		painel.add(txNome);
		painel.add(lbSobrenome);
		painel.add(txSobrenome);
		painel.add(lbDataAniversario);
		painel.add(txDataAniversario);
		painel.add(btLimpar);
		painel.add(btSalvar);

		getContentPane().add(painel);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 150);
		setVisible(true);

		btSalvar.addActionListener(new BtSalvarListener());
		btLimpar.addActionListener(new BtLimparListener());
	}

	private class BtSalvarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String nome = txNome.getText().trim();
			String sobrenome = txSobrenome.getText().trim();
			String dtAniversario = txDataAniversario.getText().trim();
			int id = Integer.parseInt(txId.getText().trim());
			boolean verificar = !nome.isEmpty() && nome.length() > 0;
			verificar &= !sobrenome.isEmpty() && sobrenome.length() > 0;
			verificar &= !dtAniversario.isEmpty() && dtAniversario.length() > 0 && dtAniversario.matches(REGEX_DATA);
			verificar &= (id > 0);
			if (verificar) {
				Pessoa p = new Pessoa.BuildPessoa().id(id).nome(nome).sobrenome(sobrenome).dtAniversario(dtAniversario)
						.build();

				PessoaDAO dao = new PessoaDAOImpl();
				dao.update(p);

				JanelaPessoa.pesquisar(modelo);
				modelo.removeRow(linhaSelecionada);
				modelo.addRow(new Object[] { p.getId(), p.getNome(), p.getSobrenome(), p.getDtAniversario() });
				setVisible(false);
			} else {
				txNome.setText("");
				txSobrenome.setText("");
				txDataAniversario.setText("");
				JOptionPane.showMessageDialog(AtualizarPessoa.this, "Informe os valores corretamente", getTitle(),
						JOptionPane.WARNING_MESSAGE);
			}
		}

	}

	private class BtLimparListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			txNome.setText("");
			txSobrenome.setText("");
			txDataAniversario.setText("");
		}

	}

}
