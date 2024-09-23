package work.javiermantilla.finance.modules.transaction.dto;

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
public class TransactDTO implements Serializable {
	
	private static final long serialVersionUID = -4267253955035784118L;

	@NotNull
    @Size(min = 12, max = 12)
    private String numeroCuenta;
	
	@NotNull
	@Positive
    private BigDecimal monto;

}
