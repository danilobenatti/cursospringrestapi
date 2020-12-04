package curso.api.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import curso.api.rest.model.Telefone;
import curso.api.rest.model.Usuario;

public interface TelefoneRepository extends Repository<Telefone, Long> {
	
	@Query("SELECT t.tipo, t.numero FROM telefone t WHERE t.id = ?1")
	List<Telefone> findFoneByUsuario(Usuario usuario);
	
}
