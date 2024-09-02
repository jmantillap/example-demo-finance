package work.javiermantilla.finance.dto.product;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class CuentaDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 4753483269886554402L;
	@NotNull
    @Size(min = 12, max = 12)
    private String numeroCuenta;

}
