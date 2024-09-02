package work.javiermantilla.finance.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.finance.dto.product.CuentaDTO;
import work.javiermantilla.finance.dto.product.ProductDTO;
import work.javiermantilla.finance.entity.ProductEntity;
import work.javiermantilla.finance.repository.ClientRepository;
import work.javiermantilla.finance.repository.ProductRepository;
import work.javiermantilla.finance.security.ContextSession;
import work.javiermantilla.finance.service.ProductServices;
import work.javiermantilla.finance.utils.ECuentaEstado;
import work.javiermantilla.finance.utils.ETipoProducto;
import work.javiermantilla.finance.utils.GeneratorNumberAccount;
import work.javiermantilla.finance.utils.GenericMapper;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServicesImpl implements ProductServices {

	private final ContextSession session;
	private final ProductRepository productRepository;
	private final GeneratorNumberAccount generatorNumberAccount;
	private final ClientRepository clientRepository;

	@Override
	public ProductDTO createProduct(ProductDTO productDTO) {
		log.info("Producto a guardar: {}", productDTO);
		if(this.clientRepository.findById(productDTO.getIdCliente()).isEmpty()) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente no existe");
				
		productDTO.setId(null);
		productDTO.setEstado(ECuentaEstado.ACTIVA.getCode());
		String numCuenta = null;
		while (true) {
			numCuenta = this.generatorNumberAccount.getNumber(ETipoProducto.fromCode(productDTO.getTipoProducto()));
			if (this.productRepository.findByNumeroCuenta(numCuenta) == null) 
				break;			
		}
		ProductEntity productEntity = GenericMapper.map(productDTO, ProductEntity.class);
		productEntity.setFechaCreacion(LocalDateTime.now());
		productEntity.setFechaModificacion(LocalDateTime.now());
		productEntity.setSaldo(new BigDecimal(0));
		productEntity.setNumeroCuenta(numCuenta);
		productEntity = this.productRepository.save(productEntity);
		log.info("El usuario {}  creo el producto : {}", session.getContextSessionThread().getName(), productEntity);
		return GenericMapper.map(productEntity, ProductDTO.class);
	}

	@Override
	public ProductDTO inactivateProduct(CuentaDTO cuentaDTO) {		
		ProductEntity productEntity = this.validateProduct(cuentaDTO.getNumeroCuenta(), ECuentaEstado.INACTIVA);		
		this.productRepository.updateStatusProduct(ECuentaEstado.INACTIVA.getCode(), productEntity.getId());
		productEntity.setEstado(ECuentaEstado.INACTIVA.getCode());
		productEntity.setSaldo(null);
		log.info("El usuario {} inactivo el producto : {}", session.getContextSessionThread().getName(), productEntity);
		return GenericMapper.map(productEntity, ProductDTO.class);
	}

	@Override
	public ProductDTO activateProduct(CuentaDTO cuentaDTO) {		
		ProductEntity productEntity = this.validateProduct(cuentaDTO.getNumeroCuenta(), ECuentaEstado.ACTIVA);
		this.productRepository.updateStatusProduct(ECuentaEstado.ACTIVA.getCode(), productEntity.getId());
		productEntity.setEstado(ECuentaEstado.ACTIVA.getCode());
		productEntity.setSaldo(null);
		log.info("El usuario {} cancelo el producto : {}", session.getContextSessionThread().getName(), productEntity);
		return GenericMapper.map(productEntity, ProductDTO.class);
	}
	
	@Override
	public ProductDTO calcelProduct(CuentaDTO cuentaDTO) {
		ProductEntity productEntity = this.validateProduct(cuentaDTO.getNumeroCuenta(), ECuentaEstado.CANCELADA);
		if(productEntity.getSaldo().doubleValue()>0d) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cuenta tiene saldo y no se puede cancelar");
		
		this.productRepository.updateStatusProduct(ECuentaEstado.CANCELADA.getCode(), productEntity.getId());
		productEntity.setEstado(ECuentaEstado.CANCELADA.getCode());
		productEntity.setSaldo(null);
		log.info("El usuario {}  inactivo el producto : {}", session.getContextSessionThread().getName(), productEntity);
		return GenericMapper.map(productEntity, ProductDTO.class);
	}
	
	@Override
	public List<ProductDTO> getListProduct() {
		List<ProductEntity> list= this.productRepository.findAll();
		log.info("El usuario {}  consulto todos los productos",session.getContextSessionThread().getName());
		return GenericMapper.mapList(list, ProductDTO.class);
	}
	
	private ProductEntity validateProduct(String numeroCuenta, ECuentaEstado estado) {
		ProductEntity productEntity= this.productRepository.findByNumeroCuenta(numeroCuenta);
		if(productEntity==null) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cuenta no existe");
		if(productEntity.getEstado().equals(estado.getCode()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cuenta ya se encuentra "+ estado.getMessage());
		
		return productEntity;
	}

	

	

}
