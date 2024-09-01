package work.javiermantilla.finance.dto.product;

import java.io.Serializable;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import work.javiermantilla.finance.validators.OneOf;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ProductDTO implements Serializable {
	
	private static final long serialVersionUID = 2491171719653065617L;
	
	@NotNull
    private Integer id;
	@NotNull
	@Min(1)
	private Integer idCliente;
	@Size(min = 2, max = 2)
    @OneOf(allowedValues = {"CC","CA"}, message = "El valor no es válido. Solo se permite: CC,CA")
    private String tipoProducto;
	@Null
    @Size(min = 12, max = 12)
    private String numeroCuenta;
	//@DecimalMin(value = "00.00")
	@Null
    private BigDecimal saldo;
	@NotNull
    private Boolean excentaGmf;
	
//	@Null
//	@Size(min = 1, max = 1)
//	@OneOf(allowedValues = {"A","I","C"}, message = "El valor no es válido. Solo se permite: <A>ctiva,<I>nactiva,<C>ancelada")
//  private String estado;
	@Null
	private String estado;
}
