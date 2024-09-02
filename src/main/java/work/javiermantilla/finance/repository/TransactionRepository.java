package work.javiermantilla.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import work.javiermantilla.finance.entity.TransactionEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
	
}
