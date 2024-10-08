package work.javiermantilla.finance.modules.product.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import work.javiermantilla.finance.modules.product.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
	
	@Query(value = "SELECT p FROM ProductEntity p JOIN p.cliente c  WHERE c.id= :idClient ")
	List<ProductEntity> findProductByIdClient(@Param("idClient") int idClient);	
	
	ProductEntity findByNumeroCuenta(String numCuenta);
	
	@Modifying
	@Transactional
	@Query("UPDATE ProductEntity p SET p.estado = :estado, p.fechaModificacion= :fecha  WHERE id  = :idProduct")
	int updateStatusProduct(@Param("estado") String estado, @Param("fecha") LocalDateTime fecha , @Param("idProduct") Integer idProduct);
}
