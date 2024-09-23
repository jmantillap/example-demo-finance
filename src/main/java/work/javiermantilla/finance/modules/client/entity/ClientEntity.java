package work.javiermantilla.finance.modules.client.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity
@Table(name = "cliente", uniqueConstraints = {
    @UniqueConstraint(name = "UQ_NUM_DOCU", columnNames = {"tipo_identificacion", "numero_identificacion"})
})
public class ClientEntity implements Serializable {
	
	private static final long serialVersionUID = 4612364009145610547L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo_identificacion", nullable = false, length = 2)
    private String tipoIdentificacion;

    @Column(name = "numero_identificacion", nullable = false, length = 45)
    private String numeroIdentificacion;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 45)
    private String apellido;

    @Email
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_ultima_actualizacion", nullable = false)
    private LocalDateTime fechaUltimaActualizacion;
    
}
