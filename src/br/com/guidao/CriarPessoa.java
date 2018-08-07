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

public class CriarPessoa extends JFrame {

	private static final long serialVersionUID = -9157481906941039956L;
	private final DefaultTableModel modelo;
	private JPanel panel;
	private JButton btSalvar;
	private JButton btLimpar;
	private JLabel lbNome;
	private JLabel lbSobrenome;
	private JLabel lbDataAniversario;
	private JTextField txNome;
	private JTextField txSobrenome;
	private JFormattedTextField txDataAniversario;
	private final static MaskFormatter MASCARA_DATE = new MaskFormatter();

	CriarPessoa(DefaultTableModel modelo) {
		super("Cadastro Pessoas");
		criarJanela();
		this.modelo = modelo;

	}

	private void criarJanela() {

		try {
			MASCARA_DATE.setMask("##/##/####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		btSalvar = new JButton("Salvar");
		btLimpar = new JButton("Limpar");
		lbNome = new JLabel("         Nome.:   ");
		lbSobrenome = new JLabel("         Sobrenome.:   ");
		lbDataAniversario = new JLabel("         Data Aniversario.:   ");
		txNome = new JTextField(10);
		txSobrenome = new JTextField();
		txDataAniversario = new JFormattedTextField(MASCARA_DATE);

		panel = new JPanel(new GridLayout(4, 2, 2, 4));
		panel.add(lbNome);
		panel.add(txNome);
		panel.add(lbSobrenome);
		panel.add(txSobrenome);
		panel.add(lbDataAniversario);
		panel.add(txDataAniversario);
		panel.add(btLimpar);
		panel.add(btSalvar);

		getContentPane().add(panel);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 150);
		setVisible(true);
		btSalvar.addActionListener(new SalvarListener());
		btLimpar.addActionListener(new LimparListener());
	}

	private class SalvarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String nome = txNome.getText().trim();
			String sobrenome = txSobrenome.getText().trim();
			String dtAniversario = txDataAniversario.getText().trim();
			boolean verificar = !nome.isEmpty() && nome.length() > 0;
			verificar &= !sobrenome.isEmpty() && sobrenome.length() > 0;
			verificar &= !dtAniversario.isEmpty() && dtAniversario.length() > 0 && dtAniversario.matches(REGEX_DATA);
			if (verificar) {
				Pessoa p = new Pessoa.BuildPessoa().nome(nome).sobrenome(sobrenome).dtAniversario(dtAniversario)
						.build();

				PessoaDAO dao = new PessoaDAOImpl();
				dao.create(p);

				JanelaPessoa.pesquisar(modelo);
				setVisible(false);
			} else {
				txNome.setText("");
				txSobrenome.setText("");
				txDataAniversario.setText("");
				JOptionPane.showMessageDialog(CriarPessoa.this, "Informe os valores corretamente", getTitle(),
						JOptionPane.WARNING_MESSAGE);
			}

		}

	}

	private class LimparListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			txNome.setText("");
			txSobrenome.setText("");
			txDataAniversario.setText("");
		}

	}
}
