package br.com.dao.interfaceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.dao.interfaces.PessoaDAO;
import br.com.dao.classes.Pessoa;
import br.com.dao.conexao.FabricaConexao;

public class PessoaDAOImpl implements PessoaDAO {

	@Override
	public List<Pessoa> getAll() {
		String sql = "SELECT * FROM `pessoa`";
		ResultSet rs = null;
		List<Pessoa> allPessoas = new ArrayList<>();
		try (Connection conn = FabricaConexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
			rs = ps.executeQuery();
			while (rs.next()) {
				allPessoas.add(new Pessoa(rs.getInt("id"), rs.getString("nome"), rs.getString("sobrenome"),
						rs.getString("dataaniversario")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			FabricaConexao.fecharConexao(rs);
		}
		return allPessoas;
	}

	@Override
	public Pessoa getById(Integer id) {
		if (id == null || id < 0) {
			throw new IllegalArgumentException();
		}
		String sql = "SELECT * FROM `pessoa` WHERE id=?";
		ResultSet rs = null;
		Pessoa pessoa = null;
		try (Connection conn = FabricaConexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			rs = ps.executeQuery();
			rs.next();
			pessoa = new Pessoa(rs.getInt("id"), rs.getString("nome"), rs.getString("sobrenome"),
					rs.getString("dataaniversario"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			FabricaConexao.fecharConexao(rs);
		}
		return pessoa;
	}

	@Override
	public boolean create(Pessoa pessoa) {
		if (pessoa == null) {
			throw new IllegalArgumentException();
		}
		String sql = "INSERT INTO `pessoa` (`nome`, `sobrenome`, `dataaniversario`) VALUES (?, ?, ?)";
		try (Connection conn = FabricaConexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, pessoa.getNome());
			ps.setString(2, pessoa.getSobrenome());
			ps.setString(3, pessoa.getDtAniversario());
			int linhasAfetadas = ps.executeUpdate();
			if (linhasAfetadas > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Pessoa pessoa) {
		if (pessoa == null || pessoa.getId() == null || pessoa.getId() <= 0) {
			throw new IllegalArgumentException();
		}
		String sql = "UPDATE `pessoa` SET `nome` = ?, `sobrenome` = ?, `dataaniversario` = ? WHERE `pessoa`.`id` = ?";
		try (Connection conn = FabricaConexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, pessoa.getNome());
			ps.setString(2, pessoa.getSobrenome());
			ps.setString(3, pessoa.getDtAniversario());
			ps.setInt(4, pessoa.getId());
			int linhasAfetadas = ps.executeUpdate();
			if (linhasAfetadas > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Integer id) {
		if (id == null || id < 0) {
			throw new IllegalArgumentException();
		}
		String sql = "DELETE FROM `pessoa` WHERE `pessoa`.`id` = ?";
		try (Connection conn = FabricaConexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			int linhasAfetadas = ps.executeUpdate();
			if (linhasAfetadas > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public List<Pessoa> getByName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException();
		}
		String sql = "SELECT * FROM `pessoa` where nome like ?";
		ResultSet rs = null;
		List<Pessoa> allPessoas = new ArrayList<>();
		try (Connection conn = FabricaConexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, "%"+name+"%");
			rs = ps.executeQuery();
			while (rs.next()) {
				allPessoas.add(new Pessoa(rs.getInt("id"), rs.getString("nome"), rs.getString("sobrenome"),
						rs.getString("dataaniversario")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			FabricaConexao.fecharConexao(rs);
		}
		return allPessoas;
	}

}
