package work.javiermantilla.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import work.javiermantilla.finance.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
	
	@Query(value = "SELECT p FROM ProductEntity p JOIN p.cliente c  WHERE c.id= :idClient ")
	List<ProductEntity> findProductByIdClient(@Param("idClient") int idClient);	
	
	ProductEntity findByNumeroCuenta(String numCuenta);
}
