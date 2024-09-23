package work.javiermantilla.finance.modules.transaction.dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonInclude;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class TransferFullDTO implements Serializable {
	
	private static final long serialVersionUID = 3450337480216699483L;

	private TransactFullDTO cuentaOrigen;
	private TransactFullDTO cuentaDestino;
	public TransferFullDTO(TransactFullDTO cuentaOrigen, TransactFullDTO cuentaDestino) {
		super();
		this.cuentaOrigen = cuentaOrigen;
		this.cuentaDestino = cuentaDestino;
	}
	
	
	
	
}
