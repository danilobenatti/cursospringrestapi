package curso.api.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(name = "uk_nomerole", columnNames = { "nomeRole" }))
@SequenceGenerator(name = "roleID", sequenceName = "sequence_role", initialValue = 1, allocationSize = 1)
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "roleID")
//	@GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL
	private Long id;

	@NotNull
	@NotEmpty
	@Column(nullable = false)
	private String nomeRole; /* Papel, exemplo: ROLE_ADMIN, ROLE_GUEST, ROLE_USER, ... */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeRole() {
		return nomeRole;
	}

	public void setNomeRole(String nomeRole) {
		this.nomeRole = nomeRole;
	}

	@Override
	public String getAuthority() { /* Retorna o nome do papel, acesso ou autorização exemplo ROLE_ADMIN */
		return this.nomeRole;
		
	}

}
