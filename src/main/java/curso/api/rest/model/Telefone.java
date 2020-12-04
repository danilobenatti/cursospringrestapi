package curso.api.rest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@SequenceGenerator(name = "telefoneID", sequenceName = "sequence_telefone", initialValue = 1, allocationSize = 1)
public class Telefone implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "telefoneID")
//	@GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL
	private Long id;

	@Column(nullable = false)
	private Character Tipo;

	@Column(nullable = false, length = 17)
	private String numero;

	@JsonIgnore /* Resolve recursividade ao carregar JSON de telefones para o usuário */
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, 
		foreignKey = @ForeignKey(name = "usuario_fk", 
		foreignKeyDefinition = "FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE"))
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Character getTipo() {
		return Tipo;
	}

	public void setTipo(Character tipo) {
		Tipo = tipo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		Telefone other = (Telefone) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
