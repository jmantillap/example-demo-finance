package work.javiermantilla.finance.modules.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import work.javiermantilla.finance.modules.client.entity.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Integer> { 
	List<ClientEntity> findByTipoIdentificacionAndNumeroIdentificacion(String tipoDocumento, String numeroIdentificacion);  
}

