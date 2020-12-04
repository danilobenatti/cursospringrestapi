package curso.api.rest.model;

import java.io.Serializable;
import java.util.List;

public class UsuarioDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private String userLogin;
    
    private String userNome;
    
    private String userCpf;

    private List<TelefoneDTO> userTelefones;
    
    public UsuarioDTO(Usuario usuario) {
	
	this.userLogin = usuario.getLogin();
	
	this.userNome = usuario.getNome();
	
	this.userCpf = usuario.getCpf();
	
//	this.userTelefones = usuario.getTelefones();
	
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserNome() {
        return userNome;
    }

    public void setUserNome(String userNome) {
        this.userNome = userNome;
    }

    public String getUserCpf() {
        return userCpf;
    }

    public void setUserCpf(String userCpf) {
        this.userCpf = userCpf;
    }

	public List<TelefoneDTO> getUserTelefones() {
		return userTelefones;
	}

	public void setUserTelefones(List<TelefoneDTO> userTelefones) {
		this.userTelefones = userTelefones;
	}

}
