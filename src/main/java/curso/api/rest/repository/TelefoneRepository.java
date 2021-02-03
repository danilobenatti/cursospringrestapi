package curso.api.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import curso.api.rest.model.Telefone;
import curso.api.rest.model.Usuario;

@SuppressWarnings("unused")
@Repository
public interface TelefoneRepository extends CrudRepository<Telefone, Long> {

//    @Query(value = "SELECT f.tipo, f.numero\n"
//    	+ "	FROM usuario AS u\n"
//    	+ "	JOIN telefone AS f ON f.usuario_id = u.id\n"
//    	+ "	WHERE u.id = ?1")
//    List<Telefone> findByUsuario(Usuario usuario);
}
