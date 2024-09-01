package work.javiermantilla.finance.dto.client;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.AssertFalse;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
public class ClientDTO implements Serializable {	
	
	private static final long serialVersionUID = -8751017376544579435L;
	    
	@NotNull
    private Integer id;
    @Size(min = 2, max = 2)
    @OneOf(allowedValues = {"CC","PA","DE"}, message = "El valor no es válido. Solo se permite: CC,PA,DE")
    private String tipoIdentificacion;
    @NotNull
    private String numeroIdentificacion;
    @NotNull
    @Size(min = 2, max = 45)
    private String nombre;
    @NotNull
    @Size(min = 2, max = 45)
    private String apellido;
    //RFC 5322 for Email Validation
    @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @NotNull    
    private LocalDate fechaNacimiento;
    
    @AssertFalse(message = "El Cliente debe ser Mayor de Edad .")
    public boolean isMenorEdad() {
    	Period edad = Period.between(fechaNacimiento, LocalDate.now());
    	return edad.getYears()<18;
    }
    
}