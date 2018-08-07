package br.com.dao.interfaces;

import java.util.List;

import br.com.dao.classes.Pessoa;

public interface PessoaDAO {
	public List<Pessoa> getAll();

	public Pessoa getById(Integer id);
	
	public boolean create(Pessoa pessoa);
	
	public boolean update(Pessoa pessoa);
	
	public boolean delete(Integer id);
	
	public List<Pessoa> getByName(String name);

}
