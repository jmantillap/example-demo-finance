package work.javiermantilla.finance.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;

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



import work.javiermantilla.finance.dto.login.UserContextSessionDTO;
import work.javiermantilla.finance.dto.product.CuentaDTO;
import work.javiermantilla.finance.dto.product.ProductDTO;
import work.javiermantilla.finance.entity.ClientEntity;
import work.javiermantilla.finance.entity.ProductEntity;
import work.javiermantilla.finance.repository.ClientRepository;
import work.javiermantilla.finance.repository.ProductRepository;
import work.javiermantilla.finance.security.ContextSession;

import work.javiermantilla.finance.service.ProductServices;
import work.javiermantilla.finance.utils.GeneratorNumberAccount;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(classes = { ProductServicesImpl.class })
class ProductServicesImplTest {

	@Autowired
	private ProductServices productServices;

	@MockBean
	private ContextSession session;
	@MockBean
	private ProductRepository productRepository;
	@MockBean
	private GeneratorNumberAccount generatorNumberAccount;
	@MockBean
	private ClientRepository clientRepository;

	@Test
	void testCreateProduct() {
		when(session.getContextSessionThread()).thenReturn(new UserContextSessionDTO("10", "nombre", new Date()));
		when(this.productRepository.save(any())).thenReturn(new ProductEntity());

		ProductDTO prod = new ProductDTO();
		prod.setIdCliente(10);
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			this.productServices.createProduct(prod);
		});
		assertFalse(exception.getMessage().isEmpty());

		when(this.clientRepository.findById(anyInt())).thenReturn(Optional.of(new ClientEntity()));
		assertNotNull(this.productServices.createProduct(prod));

	}

	@Test
	void testInactivateProduct() {
		when(session.getContextSessionThread()).thenReturn(new UserContextSessionDTO("10", "nombre", new Date()));

		CuentaDTO cuenta = new CuentaDTO();
		cuenta.setNumeroCuenta("numero");
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			this.productServices.inactivateProduct(cuenta);
		});
		assertFalse(exception.getMessage().isEmpty());

		ProductEntity prod = new ProductEntity();
		prod.setEstado("I");
		when(this.productRepository.findByNumeroCuenta(anyString())).thenReturn(prod);

		exception = assertThrows(ResponseStatusException.class, () -> {
			this.productServices.inactivateProduct(cuenta);
		});
		assertFalse(exception.getMessage().isEmpty());

		prod.setEstado("A");
		when(this.productRepository.findByNumeroCuenta(anyString())).thenReturn(prod);
		assertNotNull(this.productServices.inactivateProduct(cuenta));
		;
	}

	@Test
	void testActivateProduct() {
		when(session.getContextSessionThread()).thenReturn(new UserContextSessionDTO("10", "nombre", new Date()));
		ProductEntity prod = new ProductEntity();
		prod.setEstado("I");
		when(this.productRepository.findByNumeroCuenta(anyString())).thenReturn(prod);
		
		CuentaDTO cuenta = new CuentaDTO();
		cuenta.setNumeroCuenta("numero");
		assertNotNull(this.productServices.activateProduct(cuenta));
	}

	@Test
	void testCalcelProduct() {
		when(session.getContextSessionThread()).thenReturn(new UserContextSessionDTO("10", "nombre", new Date()));
		
		ProductEntity prod = new ProductEntity();
		prod.setEstado("I");
		prod.setSaldo(BigDecimal.valueOf(10));
		when(this.productRepository.findByNumeroCuenta(anyString())).thenReturn(prod);		
		
		CuentaDTO cuenta = new CuentaDTO();
		cuenta.setNumeroCuenta("numero");
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			this.productServices.calcelProduct(cuenta);
		});
		assertFalse(exception.getMessage().isEmpty());
		
		prod.setSaldo(BigDecimal.valueOf(0));		
		when(this.productRepository.findByNumeroCuenta(anyString())).thenReturn(prod);		
		assertNotNull(this.productServices.calcelProduct(cuenta));
	}

	@Test
	void testGetListProduct() {
		when(session.getContextSessionThread()).thenReturn(new UserContextSessionDTO("10", "nombre", new Date()));
		assertTrue(this.productServices.getListProduct().isEmpty());
	}

}
