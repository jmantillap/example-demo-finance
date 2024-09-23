package work.javiermantilla.finance.cross.util;

public enum ECuentaEstado {
	ACTIVA("A","Activa"),
	INACTIVA("I","Inactiva"),
	CANCELADA("C","Cancelada"),
	;
	
	private String code;
	private String message;

	private ECuentaEstado(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
