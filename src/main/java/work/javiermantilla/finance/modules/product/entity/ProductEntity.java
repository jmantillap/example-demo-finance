package work.javiermantilla.finance.modules.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import work.javiermantilla.finance.modules.client.entity.ClientEntity;

@Entity
@Table(name = "producto")
@Data
public class ProductEntity implements Serializable {

    private static final long serialVersionUID = 5148105422890887634L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClientEntity cliente;

    @Column(name = "tipo_producto", nullable = false, length = 2)
    private String tipoProducto;

    @Column(name = "numero_cuenta", nullable = false, length = 12)
    private String numeroCuenta;

    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;

    @Column(name = "excenta_gmf", nullable = false)
    private Boolean excentaGmf;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion;

    @Column(name = "estado", nullable = false, length = 1)
    private String estado;
}