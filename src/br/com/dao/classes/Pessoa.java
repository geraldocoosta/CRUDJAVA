package br.com.dao.classes;

public class Pessoa {
	private Integer id;
	private String nome;
	private String sobrenome;
	private String dtAniversario;
	public final static String REGEX_DATA = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])"
			+ "\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26"
			+ "])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-"
			+ "9]|[2-9]\\d)?\\d{2})$";

	public Pessoa() {
		
	}
	public Pessoa(String nome, String sobrenome, String dtAniversario) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		if (dtAniversario.matches(REGEX_DATA)) {
			this.dtAniversario = dtAniversario;
		} else {
			this.dtAniversario = "00/00/0000";
		}
	}

	public Pessoa(Integer id, String nome, String sobrenome, String dtAniversario) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		if (dtAniversario.matches(REGEX_DATA)) {
			this.dtAniversario = dtAniversario;
		} else {
			this.dtAniversario = "00/00/0000";
		}

	}

	public static class BuildPessoa {
		
		public BuildPessoa() {
		}

		private Integer id;
		private String nome;
		private String sobrenome;
		private String dtAniversario;

		public BuildPessoa id(int id) {
			this.id = id;
			return this;
		}

		public BuildPessoa nome(String nome) {
			this.nome = nome;
			return this;
		}

		public BuildPessoa sobrenome(String sobrenome) {
			this.sobrenome = sobrenome;
			return this;
		}

		public BuildPessoa dtAniversario(String dtAniversario) {
			this.dtAniversario = dtAniversario;
			return this;
		}

		public Pessoa build() {
			Pessoa pessoa = new Pessoa(id, nome, sobrenome, dtAniversario);
			return pessoa;
		}
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getDtAniversario() {
		return dtAniversario;
	}

	public void setDtAniversario(String dtAniversario) {
		if (dtAniversario.matches(REGEX_DATA)) {
			this.dtAniversario = dtAniversario;
		} else {
			this.dtAniversario = "00/00/0000";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Pessoa [id=%s, nome=%s, sobrenome=%s, dtAniversario=%s]", id, nome, sobrenome,
				dtAniversario);
	}

}
