package curso.api.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.api.rest.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	@Query("SELECT u FROM Usuario u WHERE u.login = ?1")
	Usuario findUserByLogin(String login);

	/**
	 * Realizando consulta com case-insensitive
	 * SELECT u FROM Usuario u WHERE lower(u.nome) LIKE lower(concat('%',?1,'%'))
	 * 
	 * findBy - busca padrão (especificação Spring Data JPA)
	 * Nome - atributo nome da entidade (especificação Spring Data JPA) 
	 * ContainingIgnoreCase - ignora o case (especificação Spring Data JPA)
	 */
	//@Query("SELECT u FROM Usuario u WHERE u.nome LIKE %?1%")
	List<Usuario> findByNomeContainingIgnoreCase(String nome);
	
	/**
	 * Rotina de update de usuário para atualizar token de acesso
	 * no banco de dados.
	 * @param token
	 * @param login
	 */
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE usuario AS u SET token = ?1 WHERE u.login = ?2")
	void atualizaTokenUser(String token, String login);

}
