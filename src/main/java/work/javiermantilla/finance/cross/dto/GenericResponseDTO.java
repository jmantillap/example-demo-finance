package work.javiermantilla.finance.cross.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import work.javiermantilla.finance.cross.util.FinanceConstants;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponseDTO {

	private Object data;
	private boolean success;
	private String message;
	private HttpStatus status;
	private String title = "";

	public GenericResponseDTO(Object data, boolean success, String message, HttpStatus status) {
		this.data = data;
		this.success = success;
		this.message = message;
		this.status = status;
		this.title = success ? "" : "¡Oops! Ocurrió un error";
	}

	public GenericResponseDTO(Object data, HttpStatus status) {
		this.data = data;
		this.success = true;
		this.message = FinanceConstants.RESPONSE_FIND;
		this.status = status;
		this.title = FinanceConstants.TITTLE_FIND;
	}
}
