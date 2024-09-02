package work.javiermantilla.finance.utils;

import java.util.HashMap;
import java.util.Map;

public enum ETipoMovimiento {
	CONSIGNACION("CO","+","Consignación"),
	RETIRO("RE","-","Retiro"),
	TRANSFERENCIA_SALIDA("TS","-","Salida de transferencia"),
	TRANSFERENCIA_ENTRADA("TE","+","Entrada de transferencia"),
	;
	
	private String code;
	private String signo;
	private String descripcion;
	private static final Map<String, ETipoMovimiento> MAP = new HashMap<>();
	

	private ETipoMovimiento(String code, String signo,String descripcion) {
		this.code = code;
		this.descripcion = descripcion;
		this.signo=signo;
	}

	public static ETipoMovimiento fromCode(String code){
        return MAP.get(code);
    }
	
	
	public String getCode() {
		return code;
	}
	public String getSigno() {
		return signo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	
	static{
        for(ETipoMovimiento n : values()){
            MAP.put(n.getCode(), n);
        }
    }
}
