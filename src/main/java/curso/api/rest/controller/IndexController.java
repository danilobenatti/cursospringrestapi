package curso.api.rest.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import curso.api.rest.model.Usuario;
import curso.api.rest.model.UsuarioDTO;
import curso.api.rest.repository.UsuarioRepository;

@CrossOrigin(origins = {"*"})
@RestController /* Aqui já se defini a Arquitetura REST, retornando em JSON */
@RequestMapping(value = "/usuario")
public class IndexController {

	/** Serviço RESTfull
	// Declaração inicial para testes e aprendizado
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity init() {
		return new ResponseEntity("Olá Usuário REST Spring Boot", HttpStatus.OK);
	}
	*/
	
	/**
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity init(@RequestParam(value = "nome", required = true, defaultValue = "Nome não informado!") String nome,
		@RequestParam(value = "salario", required = true, defaultValue = "Salário não informado!") String salario) {
		
		// No navegador: http://localhost:8080/usuario/?nome=João&salario=3000

		// System.out.println("Parâmetro sendo recebido: " + nome + ", Salário é: " + salario);

		return new ResponseEntity("Olá Usuário REST Spring Boot seu nome é: "+ nome +", e salário é: "+ salario, HttpStatus.OK);
	*/
	
	/**
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> init() {
	
		Usuario usuario1 = new Usuario();
		usuario1.setId(1L);
		usuario1.setLogin("teste1@tst.com");
		usuario1.setSenha("123");
		usuario1.setNome("testenome1");
		// return ResponseEntity.ok(usuario1);
		
		Usuario usuario2 = new Usuario();
		usuario2.setId(2L);
		usuario2.setLogin("teste2@tst.com");
		usuario2.setSenha("456");
		usuario2.setNome("testenome2");
		
		Usuario usuario3 = new Usuario();
		usuario3.setId(3L);
		usuario3.setLogin("teste3@tst.com");
		usuario3.setSenha("789");
		usuario3.setNome("testenome3");
				
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(usuario1);
		usuarios.add(usuario2);
		usuarios.add(usuario3);
		
		return new ResponseEntity(usuarios, HttpStatus.OK);
	*/
    
    	@Autowired /* se fosse CDI seria @Inject */
    	private UsuarioRepository usuarioRepository;
	
	@GetMapping(value = "/{id}", produces = "application/json")
//	@Cacheable(value = "cacheuser")
	@CacheEvict(value = "cacheuser", allEntries = true)
	@CachePut(value = "cacheuser")
	public ResponseEntity<UsuarioDTO> init(@PathVariable(value = "id") Long id) {
	    /**
	     * @PathVariable 
	     * No navegador: http://localhost:8080/usuario/1
	     */
	    Optional<Usuario> usuario = usuarioRepository.findById(id);

	    System.out.println("Executando versão 0");

	    return new ResponseEntity<UsuarioDTO>(new UsuarioDTO(usuario.get()), HttpStatus.OK);
	}
	
	/**
	 * Roteamento pela "URL".
	 * @param id
	 * @return Lista de usuários API V1 ou V2.
	 */
	@GetMapping(value = "v1/{id}", produces = "application/json")
	public ResponseEntity<Usuario> initV1(@PathVariable(value = "id") Long id) {

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		System.out.println("Executando versão 1 (URL).");

		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
	}

	@GetMapping(value = "v2/{id}", produces = "application/json")
	public ResponseEntity<Usuario> initV2(@PathVariable(value = "id") Long id) {

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		System.out.println("Executando versão 2 (URL).");

		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
	}
	
	/**
	 * Roteamento pela "header".
	 * @param id
	 * @return Lista de usuários API V3 ou V4.
	 */
	@GetMapping(value = "/{id}", produces = "application/json", headers = "X-API-Version=v3")
	public ResponseEntity<Usuario> initV3(@PathVariable(value = "id") Long id) {

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		System.out.println("Executando versão 3 (headers).");

		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = "application/json", headers = "X-API-Version=v4")
	public ResponseEntity<Usuario> initV4(@PathVariable(value = "id") Long id) {

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		System.out.println("Executando versão 4 (headers).");

		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
	}

	/**
	 * Supondo que o carregamento de usuários seja uma operação muito lenta Vamos
	 * controlar com cache para agilizar o processo
	 * 
	 * @throws InterruptedException
	 */
	/* Método para consultar todos usuários */
	// @Secured({"ROLE_SELECT"})
	@CrossOrigin(origins = {"*","localhost:8080","http://www.ecosensor.com.br/"})
	@GetMapping(value = "/", produces = "application/json")
//	@Cacheable(value = "cacheusuarios")
	@CacheEvict(value = "cacheusuarios", allEntries = true)
	@CachePut(value = "cacheusuarios")
	public ResponseEntity<List<Usuario>> usuario() throws InterruptedException {

		List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();

		//Thread.sleep(6000); Segura o código por 6 segundos simulando um processo muito lento

		// ResponseEntity<List<Usuario>> responseEntity = new
		// ResponseEntity<List<Usuario>>(list, HttpStatus.OK);

		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
		// return responseEntity;
	}

	/* End-Point consulta de usuário por nome */
	// @Secured({"ROLE_SELECT"})
	@GetMapping(value = "/usuarioPorNome/{nome}", produces = "application/json")
//	@Cacheable(value = "cacheusername")
	@CacheEvict(value = "cacheusername", allEntries = true)
	@CachePut(value = "cacheusername")
	public ResponseEntity<List<Usuario>> usuarioPorNome(@PathVariable(value = "nome") String nome) {

		List<Usuario> list = (List<Usuario>) usuarioRepository.findByNomeContainingIgnoreCase(nome);

		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/codigovenda/{venda}", produces = "application/json")
	public ResponseEntity<Usuario> relatorio(@PathVariable(value = "id") Long id,
			@PathVariable(value = "venda") Long venda) {

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		/* supondo que o retorno seria a rotina de um relatorio ou processo de venda */
		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
	}

	// @Secured({"ROLE_INSERT"})
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) throws Exception{

		/* Código para associar o telefone ao usuário */
		for (int i = 0; i < usuario.getTelefones().size(); i++) {
			usuario.getTelefones().get(i).setUsuario(usuario);
		}
		
		/**
		 * Consumindo uma API externa - início
		 */
		URL url = new URL("http://viacep.com.br/ws/"+ usuario.getCep() +"/json/");
		URLConnection connection = url.openConnection();
		InputStream inputStream = connection.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
		String cep = "";
		StringBuilder jsonCep = new StringBuilder();
		while ((cep = bufferedReader.readLine()) != null) {
		    jsonCep.append(cep);
		}
//		System.out.println(jsonCep.toString());
		Usuario usuario2 = new Gson().fromJson(jsonCep.toString(), Usuario.class);
		usuario.setCep(usuario2.getCep());
		usuario.setLogradouro(usuario2.getLogradouro());
		usuario.setComplemento(usuario2.getComplemento());
		usuario.setBairro(usuario2.getBairro());
		usuario.setLocalidade(usuario2.getLocalidade());
		usuario.setUf(usuario2.getUf());
		/**
		 * Consumindo uma API externa - fim
		 */

		/* Gerando senha criptografada para ser GRAVADA no BD */
		/*String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());*/
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));

		Usuario usuarioSalvo = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}

//	@PostMapping(value = "/{iduser}/idvenda/{idvenda}", produces = "application/json")
//	public ResponseEntity<Usuario> cadastrarvenda(@PathVariable Long iduser, @PathVariable Long idvenda) {
//
//		/* Aqui seria o processo de venda de produto(s) */
//		// Usuario usuarioSalvo = usuarioRepository.save(usuario);
//
//		return new ResponseEntity("id user: "+ iduser +" | id venda: "+ idvenda, HttpStatus.OK);
//	}

	// @Secured(value = { "ROLE_UPDATE" })
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {

		/* Outras requisição de atualizações, enviar e-mail */

		/* Código para associar o telefone ao usuário */
		for (int i = 0; i < usuario.getTelefones().size(); i++) {
			usuario.getTelefones().get(i).setUsuario(usuario);
		}

		Usuario usuarioTemp = usuarioRepository.findById(usuario.getId()).get();
		/* Se a senha atual for diferente da senha já cadastrada no BD */
		if (!usuarioTemp.getSenha().equals(usuario.getSenha())) {
			/* Gera nova senha criptografada para ser ATUALIZADA no BD */
			/*String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());*/
			usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		}

		Usuario usuarioSalvo = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}

	// @Secured(value = {"ROLE_DELETE"})
	@DeleteMapping(value = "/{id}", produces = "application/text")
	public String deletar(@PathVariable(value = "id") Long id) {

		usuarioRepository.deleteById(id);

		return "Usuário deletado!";
	}

	@DeleteMapping(value = "/{id}/vendas", produces = "application/text")
	public String deletarvendas(@PathVariable(value = "id") Long id) {

		/* Rotina para deletar todas as vendas do usuário */
		usuarioRepository.deleteById(id);

		return "Venda deletada!";
	}

}
