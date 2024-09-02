package work.javiermantilla.finance.entity;

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

@Data
@Entity
@Table(name = "transaccion")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "tipo", nullable = false, length = 2)
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductEntity producto;

    @Column(name = "signo", nullable = false, length = 1)
    private String signo;

    @ManyToOne
    @JoinColumn(name = "id_transaccion_base")
    private TransactionEntity transaccionBase;

    @Column(name = "monto", nullable = false)
    private BigDecimal monto;
}   