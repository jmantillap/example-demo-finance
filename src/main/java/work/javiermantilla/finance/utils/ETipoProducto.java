package work.javiermantilla.finance.utils;

import java.util.HashMap;
import java.util.Map;

public enum ETipoProducto {
	AHORROS("CA","53","Cuenta Ahorros"),
	CORRIENTE("CC","33","Cuenta Corriente"),
	;
	
	private String code;
	private String numero;
	private String descripcion;
	private static final Map<String, ETipoProducto> MAP = new HashMap<>();
	

	private ETipoProducto(String code, String numero,String descripcion) {
		this.code = code;
		this.descripcion = descripcion;
		this.numero=numero;
	}

	public static ETipoProducto fromCode(String code){
        return MAP.get(code);
    }
	
	
	public String getCode() {
		return code;
	}
	public String getNumero() {
		return numero;
	}
	public String getDescripcion() {
		return descripcion;
	}
	
	static{
        for(ETipoProducto n : values()){
            MAP.put(n.getCode(), n);
        }
    }
	
}
