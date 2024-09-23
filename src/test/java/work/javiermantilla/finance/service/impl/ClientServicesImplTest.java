package work.javiermantilla.finance.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.log4j.Log4j2;
import work.javiermantilla.finance.cross.security.ContextSession;
import work.javiermantilla.finance.modules.client.dto.ClientDTO;
import work.javiermantilla.finance.modules.client.entity.ClientEntity;
import work.javiermantilla.finance.modules.client.repository.ClientRepository;
import work.javiermantilla.finance.modules.client.service.ClientServices;
import work.javiermantilla.finance.modules.client.service.ClientServicesImpl;
import work.javiermantilla.finance.modules.login.dto.UserContextSessionDTO;
import work.javiermantilla.finance.modules.product.entity.ProductEntity;
import work.javiermantilla.finance.modules.product.repository.ProductRepository;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(classes = { ClientServicesImpl.class })
@Log4j2
class ClientServicesImplTest {

	@Autowired
    private ClientServices clientServices;

	@MockBean    
	private ClientRepository clientRepository;
	@MockBean
	private ProductRepository productRepository;
	@MockBean
	private ContextSession session;
	
	@Test
	void testCreateClient() {
		when(session.getContextSessionThread()).thenReturn(new UserContextSessionDTO("10", "nombre", new Date()));
		when(this.clientRepository.save(any())).thenReturn(new ClientEntity());
		
		ClientDTO client= new ClientDTO();
		client.setTipoIdentificacion("CC");
		client.setNumeroIdentificacion("numero");
		assertNotNull(this.clientServices.createClient(client));
		
		when(this.clientRepository.findByTipoIdentificacionAndNumeroIdentificacion(
				anyString(), anyString())).thenReturn(List.of(new ClientEntity()));
		
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			this.clientServices.createClient(client);
		});		
		assertFalse(exception.getMessage().isEmpty());
	}

	@Test
	void testUpdateClient() {
		when(session.getContextSessionThread()).thenReturn(new UserContextSessionDTO("10", "nombre", new Date()));		
		when(this.clientRepository.findByTipoIdentificacionAndNumeroIdentificacion(
				anyString(), anyString())).thenReturn(new ArrayList<>());
		when(this.clientRepository.save(any())).thenReturn(new ClientEntity());
		
		ClientDTO clientUpdate= new ClientDTO();
		clientUpdate.setId(-1);
		clientUpdate.setTipoIdentificacion("CC");
		clientUpdate.setNumeroIdentificacion("numero");
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			this.clientServices.updateClient(clientUpdate);
		});		
		assertFalse(exception.getMessage().isEmpty());
		
		clientUpdate.setId(20);
		exception = assertThrows(ResponseStatusException.class, () -> {
			this.clientServices.updateClient(clientUpdate);
		});
		log.info(exception.getMessage());
		assertFalse(exception.getMessage().isEmpty());
		
		ClientEntity clientEntity= new ClientEntity();
		clientEntity.setId(20);		
		when(this.clientRepository.findById(anyInt())).thenReturn(Optional.of(clientEntity));
		assertNotNull(this.clientServices.updateClient(clientUpdate));
		
		ClientEntity clientList= new ClientEntity();
		clientList.setId(30);
		when(this.clientRepository.findByTipoIdentificacionAndNumeroIdentificacion(
				anyString(), anyString())).thenReturn(List.of(clientList));
		
		exception = assertThrows(ResponseStatusException.class, () -> {
			this.clientServices.updateClient(clientUpdate);
		});
		log.info(exception.getMessage());
		assertFalse(exception.getMessage().isEmpty());		
	}

	@Test
	void testDeleteClient() {
		when(session.getContextSessionThread()).thenReturn(new UserContextSessionDTO("10", "nombre", new Date()));	
		ClientEntity clientEntity= new ClientEntity();
		clientEntity.setId(20);		
		when(this.clientRepository.findById(anyInt())).thenReturn(Optional.of(clientEntity));
		assertTrue(this.clientServices.deleteClient(10));
		
		when(this.productRepository.findProductByIdClient(anyInt())).thenReturn(List.of(new ProductEntity()));
		
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			this.clientServices.deleteClient(10);
		});
		log.info(exception.getMessage());
		assertFalse(exception.getMessage().isEmpty());
		
	}

	@Test
	void testGetListClients() {
		when(session.getContextSessionThread()).thenReturn(new UserContextSessionDTO("10", "nombre", new Date()));
		assertTrue(this.clientServices.getListClients().isEmpty());
	}	

}
