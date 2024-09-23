package work.javiermantilla.finance.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import work.javiermantilla.finance.cross.security.ContextSession;
import work.javiermantilla.finance.modules.login.dto.UserContextSessionDTO;
import work.javiermantilla.finance.modules.product.entity.ProductEntity;
import work.javiermantilla.finance.modules.product.repository.ProductRepository;
import work.javiermantilla.finance.modules.transaction.dto.TransactDTO;
import work.javiermantilla.finance.modules.transaction.dto.TransferDTO;
import work.javiermantilla.finance.modules.transaction.entity.TransactionEntity;
import work.javiermantilla.finance.modules.transaction.repository.TransactionRepository;
import work.javiermantilla.finance.modules.transaction.service.TransactServices;
import work.javiermantilla.finance.modules.transaction.service.TransactServicesImpl;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(classes = { TransactServicesImpl.class })
class TransactServicesImplTest {

	@Autowired
	private TransactServices transactServices;

	@MockBean
	private ContextSession session;
	@MockBean
	private TransactionRepository transactionRepository;
	@MockBean
	private ProductRepository productRepository;
	
	
	@Test
	void testConsignmentMoney() {
		when(session.getContextSessionThread()).thenReturn(new UserContextSessionDTO("10", "nombre", new Date()));
		TransactDTO transactDTO= new TransactDTO();
		transactDTO.setNumeroCuenta("10220211001");
		transactDTO.setMonto(BigDecimal.valueOf(10d));
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			this.transactServices.consignmentMoney(transactDTO);
		});
		assertFalse(exception.getMessage().isEmpty());
		
		ProductEntity prod = new ProductEntity();
		prod.setEstado("I");
		when(this.productRepository.findByNumeroCuenta(anyString())).thenReturn(prod);
		exception = assertThrows(ResponseStatusException.class, () -> {
			this.transactServices.consignmentMoney(transactDTO);
		});
		assertFalse(exception.getMessage().isEmpty());
		
		prod.setEstado("A");
		prod.setSaldo(BigDecimal.valueOf(100));
		when(this.productRepository.findByNumeroCuenta(anyString())).thenReturn(prod);		
		when(this.transactionRepository.save(any())).thenReturn(new TransactionEntity(10));
		assertNotNull(this.transactServices.consignmentMoney(transactDTO));
	}

	@Test
	void testWithdrawMoney() {
		when(session.getContextSessionThread()).thenReturn(new UserContextSessionDTO("10", "nombre", new Date()));
		
		ProductEntity prod = new ProductEntity();
		prod.setEstado("A");
		prod.setTipoProducto("CA");
		prod.setSaldo(BigDecimal.valueOf(50));
		when(this.productRepository.findByNumeroCuenta(anyString())).thenReturn(prod);
				
		TransactDTO transactDTO= new TransactDTO();
		transactDTO.setNumeroCuenta("10220211001");
		transactDTO.setMonto(BigDecimal.valueOf(100d));
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			this.transactServices.withdrawMoney(transactDTO);
		});
		assertFalse(exception.getMessage().isEmpty());
		
		prod.setTipoProducto("CC");
		prod.setSaldo(BigDecimal.valueOf(50));
		when(this.productRepository.findByNumeroCuenta(anyString())).thenReturn(prod);		
		when(this.transactionRepository.save(any())).thenReturn(new TransactionEntity(10));		
		assertNotNull(this.transactServices.withdrawMoney(transactDTO));
		
	}

	@Test
	void testTransfer() {
		when(session.getContextSessionThread()).thenReturn(new UserContextSessionDTO("10", "nombre", new Date()));
		ProductEntity prod = new ProductEntity();
		prod.setEstado("A");
		prod.setTipoProducto("CC");
		prod.setSaldo(BigDecimal.valueOf(1000));
		when(this.productRepository.findByNumeroCuenta(anyString())).thenReturn(prod);		
		when(this.transactionRepository.save(any())).thenReturn(new TransactionEntity(10));  
		
		TransferDTO transferDTO= new TransferDTO("cuentaOrigen","CuentaDestino", BigDecimal.valueOf(1000));
		assertNotNull(this.transactServices.transfer(transferDTO));
		
	}

	@Test
	void testGetListTransaction() {
		when(session.getContextSessionThread()).thenReturn(new UserContextSessionDTO("10", "nombre", new Date()));
		assertTrue(this.transactServices.getListTransaction().isEmpty());
	}

}
