package br.com.dao.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FabricaConexao {
	public static Connection getConexao() {
		String url = "jdbc:mysql://localhost:3306/teste?useTimezone=true&serverTimezone=UTC&useSSL=false";
		String user = "root";
		String pw = null;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, pw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void fecharConexao(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void fecharConexao(Connection conn, Statement s) {
		fecharConexao(conn);
		try {
			if (s != null)
				s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void fecharConexao(Connection conn, Statement s, ResultSet rs) {
		fecharConexao(conn, s);
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void fecharConexao(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
