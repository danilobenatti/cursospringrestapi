package curso.api.rest.model;

import java.io.Serializable;

public class TelefoneDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Character foneTipo;
	
	private String foneNumero;
	
	public TelefoneDTO(Telefone telefone) {
		
		this.foneTipo = telefone.getTipo();
		
		this.foneNumero = telefone.getNumero();
	}

	public Character getFoneTipo() {
		return foneTipo;
	}

	public void setFoneTipo(Character foneTipo) {
		this.foneTipo = foneTipo;
	}

	public String getFoneNumero() {
		return foneNumero;
	}

	public void setFoneNumero(String foneNumero) {
		this.foneNumero = foneNumero;
	}
	
}
