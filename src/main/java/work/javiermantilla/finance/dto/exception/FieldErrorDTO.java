package work.javiermantilla.finance.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldErrorDTO {
	private String field;
	private String message;
}
