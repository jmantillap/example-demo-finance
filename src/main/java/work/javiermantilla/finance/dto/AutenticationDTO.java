package work.javiermantilla.finance.dto;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutenticationDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @NotNull
    @NotBlank
    private String name;
    
}
