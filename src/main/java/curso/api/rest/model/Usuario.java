package curso.api.rest.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "usuario_login_uk", columnNames = "login"),
		@UniqueConstraint(name = "usuario_cpf_uk", columnNames = "cpf")})
@SequenceGenerator(name = "usuarioID", sequenceName = "sequence_usuario", initialValue = 1, allocationSize = 1)
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "usuarioID")
//	@GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL auto-incremento
	private Long id;

	@Column(nullable = false)
	private String login;

	@Column(nullable = false)
	private String senha;

	@Column(nullable = false)
	private String nome;
	
	@CPF(message = "CPF inválido!")
	@Column(length = 15, nullable = false)
	private String cpf;
	
	@Pattern(regexp = "\\d{5}-\\d{3}", message = "Formato de CEP inválido!")
	@Column(length = 9, nullable = true)
	private String cep;
	
	@Column(length = 50)
	private String logradouro;
	
	@Column(length = 30)
	private String complemento;
	
	@Column(length = 30)
	private String bairro;
	
	@Column(length = 30)
	private String localidade;
	
	@Column(length = 2)
	private String uf;

	@OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Telefone> telefones;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_roles", uniqueConstraints = @UniqueConstraint(name = "usuario_role_uk", columnNames = { "usuario_id","role_id" }), 
	joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id", table = "usuario", nullable = false, unique = false,
		foreignKey = @ForeignKey(name = "fk_usuario_id", foreignKeyDefinition = "FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE")), 
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", table = "role", nullable = false, unique = false,
		foreignKey = @ForeignKey(name = "fk_role_id", foreignKeyDefinition = "FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE")))
	private List<Role> roles; /* Os papeis ou acessos */
	
	@JsonIgnore
	private String token = "";
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCep() {
	    return cep;
	}

	public void setCep(String cep) {
	    this.cep = cep;
	}

	public String getLogradouro() {
	    return logradouro;
	}

	public void setLogradouro(String logradouro) {
	    this.logradouro = logradouro;
	}

	public String getComplemento() {
	    return complemento;
	}

	public void setComplemento(String complemento) {
	    this.complemento = complemento;
	}

	public String getBairro() {
	    return bairro;
	}

	public void setBairro(String bairro) {
	    this.bairro = bairro;
	}

	public String getLocalidade() {
	    return localidade;
	}

	public void setLocalidade(String localidade) {
	    this.localidade = localidade;
	}

	public String getUf() {
	    return uf;
	}

	public void setUf(String uf) {
	    this.uf = uf;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public String getToken() {
	    return token;
	}

	public void setToken(String token) {
	    this.token = token;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/* São os acessos do usuário: ROLE_ADMIN, ROLE_USER, ROLE_GUEST,... */
//	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return this.senha;
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return this.login;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

}