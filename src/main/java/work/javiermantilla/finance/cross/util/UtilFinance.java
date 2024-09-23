package work.javiermantilla.finance.cross.util;

import java.io.IOException;
import java.util.Base64;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilFinance {

	private UtilFinance() {}
	
	public static String decodificarBase64Cadena(String base64String) {
		Base64.Decoder decoder = Base64.getDecoder();
		return new String(decoder.decode(base64String));
	}
	
	public static String obtenerAtributoStringJSON(String atributo, String json) throws  IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        return jsonNode.get(atributo).asText();
    }
}
