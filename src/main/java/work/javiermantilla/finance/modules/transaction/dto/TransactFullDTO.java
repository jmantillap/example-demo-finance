package work.javiermantilla.finance.modules.transaction.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class TransactFullDTO implements Serializable {
	
	private static final long serialVersionUID = 4110610670619078122L;
	private Integer id;
	private LocalDateTime fecha;
	private String tipo;
	private Integer idProducto;
	private String signo;
	private Integer idTransaccionBase;
	private BigDecimal monto;
}
