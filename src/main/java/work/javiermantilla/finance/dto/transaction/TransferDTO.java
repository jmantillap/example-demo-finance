package work.javiermantilla.finance.dto.transaction;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class TransferDTO implements Serializable {

	private static final long serialVersionUID = -2807168260500440892L;
	
	@NotNull
    @Size(min = 12, max = 12)
    private String numeroCuentaOrigen;
	@NotNull
    @Size(min = 12, max = 12)
    private String numeroCuentaDestino;
	
	@NotNull
	@Positive
    private BigDecimal monto;
}
