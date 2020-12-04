package curso.api.rest.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import curso.api.rest.ApplicationContextLoad;
import curso.api.rest.model.Usuario;
import curso.api.rest.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {

	/** Tempo(milliseconds) de validade do Token, no caso 1 dia, 
	 * pode ser qualquer valor: 15 dias, 1 ano, 100 anos.
	 * Bastando converter para milisegundos.
	 */
	private static final long EXPIRATION_TIME = 86400000;

	/**
	 * Uma senha única para compor a autenticação (assinatura digital)
	 * e ajudar na segurança
	 * senha secreta extremamente difícil, assinatura de certificado digital
	 */
	private static final String SECRET = "senha-extremamente-secreta-para-compor-senha-do-usuario";
	
	/* Padrão na resposta do Token */
	private static final String HEADER_STRING = "Authorization";

	/* Prefixo padrão de Token */
	private static final String TOKEN_PREFIX = "Bearer";

	/* Gerando Token de autenticação e adicionando ao cabeçalho a resposta HTTP */
	public void addAuthentication(HttpServletResponse response, String username) throws IOException {

	    /* Montagem/Construção do Token */
	    String JWT = Jwts.builder() /* Chama o gerador de Token */
		    .setSubject(username) /* Adiciona o usuário */
		    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /* seta data de expiração */
		    .signWith(SignatureAlgorithm.HS512, SECRET)
		    .compact(); /* Algoritmos de geração de senha + compacta */

	    /* Junta o token com o prefixo */
	    String token = TOKEN_PREFIX + " " + JWT; /* Ex.: Bearer 816816df6s8d... */

	    /* Adiciona o token no cabeçalho HTTP */
	    response.addHeader(HEADER_STRING, token); /* Authorization: Bearer 816816df6s8d... */
	    
	    /* Atualiza token do usuário no banco de dados */
	    ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class)
		.atualizaTokenUser(JWT, username);

	    /* Liberando resposta para portas diferentes que usam a API ou caso clientes Web */
	    liberacaoCors(response);

	    /* Escreve token como resposta no corpo HTTP */
	    response.getWriter().write("{\"Authorization\": \"" + token + "\"}");

	}

	/* Retorna o usuário validado com token ou caso não seja válido retorna null */
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {

	    /* Pega o token enviado no cabeçalho HTTP */
	    String token = request.getHeader(HEADER_STRING);

	    try {
		if (token != null) { /*Início da condição token*/
		    String tokenClear = token.replace(TOKEN_PREFIX, "").trim();
		    /* Faz a validação do token do usuário na requisição */
		    String user = Jwts.parser().setSigningKey(SECRET) /* Neste momento token é: Bearer 816816df6s8d... */
			    .parseClaimsJws(tokenClear) /* remove o "Bearer" Depois: 816816df6s8d... */
			    .getBody().getSubject(); /* Depois retorna somente o usuário */
		    
		    if (user != null) {
			
			Usuario usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class)
				.findUserByLogin(user);
			
			if (usuario != null) {
			    
			    /*if (tokenClear.equalsIgnoreCase(usuario.getToken())) {*/
			    if (tokenClear.equals(usuario.getToken())) {
				return new UsernamePasswordAuthenticationToken(
					usuario.getLogin(), 
					usuario.getSenha(),
					usuario.getAuthorities());			    
			    }
			}
		    }
		} /*Fim da condição token*/
	    } catch (io.jsonwebtoken.ExpiredJwtException e) {
		try {
		    response.getOutputStream().println("Token expirado, faça login ou informe novo token.");
		} catch (IOException e1) {}
	    }
	    /* Permite servidores em portas diferentes Spring: 8080 Angular: 4300 */
	    liberacaoCors(response);
	    return null; /* Não Autorizado */
	}

	private void liberacaoCors(HttpServletResponse response) {
	    if (response.getHeader("Access-Control-Allow-Origin") == null) {
		response.addHeader("Access-Control-Allow-Origin", "*");
	    }

	    if (response.getHeader("Access-Control-Allow-Headers") == null) {
		response.addHeader("Access-Control-Allow-Headers", "*");
	    }

	    if (response.getHeader("Access-Control-Request-Headers") == null) {
		response.addHeader("Access-Control-Request-Headers", "*");
	    }

	    if (response.getHeader("Access-Control-Allow-Methods") == null) {
		response.addHeader("Access-Control-Allow-Methods", "*");
	    }
	}

}
