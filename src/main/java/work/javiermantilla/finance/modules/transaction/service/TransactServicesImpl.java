package work.javiermantilla.finance.modules.transaction.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.finance.cross.security.ContextSession;
import work.javiermantilla.finance.cross.util.ECuentaEstado;
import work.javiermantilla.finance.cross.util.ETipoMovimiento;
import work.javiermantilla.finance.cross.util.ETipoProducto;
import work.javiermantilla.finance.cross.util.GenericMapper;
import work.javiermantilla.finance.modules.product.entity.ProductEntity;
import work.javiermantilla.finance.modules.product.repository.ProductRepository;
import work.javiermantilla.finance.modules.transaction.dto.TransactDTO;
import work.javiermantilla.finance.modules.transaction.dto.TransactFullDTO;
import work.javiermantilla.finance.modules.transaction.dto.TransferDTO;
import work.javiermantilla.finance.modules.transaction.dto.TransferFullDTO;
import work.javiermantilla.finance.modules.transaction.entity.TransactionEntity;
import work.javiermantilla.finance.modules.transaction.repository.TransactionRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class TransactServicesImpl implements TransactServices {
	private final ContextSession session;
	private final TransactionRepository transactionRepository;
	private final ProductRepository productRepository;
	
	@Override
	@Transactional
	public TransactFullDTO consignmentMoney(TransactDTO transactDTO) {		
		ProductEntity productEntity = this.validatePrduct(transactDTO.getNumeroCuenta());
		TransactionEntity transaction= new TransactionEntity();
		transaction.setProducto(productEntity);
		transaction.setFecha(LocalDateTime.now());
		transaction.setMonto(transactDTO.getMonto());
		transaction.setTipo(ETipoMovimiento.CONSIGNACION.getCode());
		transaction.setSigno(ETipoMovimiento.CONSIGNACION.getSigno());
		transaction = this.transactionRepository.save(transaction);		
		log.info("El usuario {} Consigno: {} ",session.getContextSessionThread().getName(), transaction );
		
		productEntity.setSaldo(productEntity.getSaldo().add(transactDTO.getMonto()));		
		this.productRepository.save(productEntity);		
		log.info("Con la consignació la cuenta: {} Quedo con saldo: {} ",transactDTO.getNumeroCuenta(), productEntity.getSaldo());
		
		return GenericMapper.map(transaction, TransactFullDTO.class);
	}
	
	@Override
	@Transactional
	public TransactFullDTO withdrawMoney(TransactDTO transactDTO) {		
		ProductEntity productEntity = this.validatePrduct(transactDTO.getNumeroCuenta());		
		this.validateNegativeBalance(productEntity, transactDTO.getMonto());
								
		TransactionEntity transactionOut= new TransactionEntity();
		transactionOut.setProducto(productEntity);
		transactionOut.setFecha(LocalDateTime.now());
		transactionOut.setMonto(transactDTO.getMonto());
		transactionOut.setTipo(ETipoMovimiento.RETIRO.getCode());
		transactionOut.setSigno(ETipoMovimiento.RETIRO.getSigno());
		transactionOut = this.transactionRepository.save(transactionOut);		
		log.info("El usuario {} retiro: {} ",session.getContextSessionThread().getName(), transactionOut );
		
		productEntity.setSaldo(productEntity.getSaldo().subtract(transactDTO.getMonto()));		
		this.productRepository.save(productEntity);		
		log.info("Con el retiro la cuenta: {} Quedo con saldo: {} ",transactDTO.getNumeroCuenta(), productEntity.getSaldo());
		return GenericMapper.map(transactionOut, TransactFullDTO.class);
	}
	
	@Override
	@Transactional
	public TransferFullDTO transfer(TransferDTO transferDTO) {
		ProductEntity productOriginEntity = this.validatePrduct(transferDTO.getNumeroCuentaOrigen());
		ProductEntity productDestinationEntity = this.validatePrduct(transferDTO.getNumeroCuentaDestino());
				
		this.validateNegativeBalance(productOriginEntity, transferDTO.getMonto());
		TransactionEntity transactionOut= new TransactionEntity();
		transactionOut.setProducto(productOriginEntity);
		transactionOut.setFecha(LocalDateTime.now());
		transactionOut.setMonto(transferDTO.getMonto());
		transactionOut.setTipo(ETipoMovimiento.TRANSFERENCIA_SALIDA.getCode());
		transactionOut.setSigno(ETipoMovimiento.TRANSFERENCIA_SALIDA.getSigno());
		transactionOut = this.transactionRepository.save(transactionOut);		
		log.info("El usuario {} transferencia de salida: {} ",session.getContextSessionThread().getName(), transactionOut );
		
		productOriginEntity.setSaldo(productOriginEntity.getSaldo().subtract(transferDTO.getMonto()));		
		this.productRepository.save(productOriginEntity);		
		log.info("La cuenta: {} Quedo con saldo: {} ",transferDTO.getNumeroCuentaOrigen(), productOriginEntity.getSaldo());
		
		TransactionEntity transactionIn= new TransactionEntity();		
		transactionIn.setProducto(productDestinationEntity);
		transactionIn.setFecha(LocalDateTime.now());
		transactionIn.setMonto(transferDTO.getMonto());
		transactionIn.setTipo(ETipoMovimiento.TRANSFERENCIA_ENTRADA.getCode());
		transactionIn.setSigno(ETipoMovimiento.TRANSFERENCIA_ENTRADA.getSigno());
		transactionIn.setTransaccionBase(new TransactionEntity(transactionOut.getId()));
		transactionIn = this.transactionRepository.save(transactionIn);		
		log.info("El usuario {} transferencia de salida: {} ",session.getContextSessionThread().getName(), transactionIn );
		
		productDestinationEntity.setSaldo(productDestinationEntity.getSaldo().add(transferDTO.getMonto()));
		this.productRepository.save(productDestinationEntity);
		log.info("La cuenta: {} Quedo con saldo: {} ",transferDTO.getNumeroCuentaDestino(), productDestinationEntity.getSaldo());
				
		transactionOut.setTransaccionBase(new TransactionEntity(transactionIn.getId()));
		transactionOut = this.transactionRepository.save(transactionOut);
		
		return new TransferFullDTO(
				GenericMapper.map(transactionOut, TransactFullDTO.class),
				GenericMapper.map(transactionIn, TransactFullDTO.class));
	}
	
	@Override
	public List<TransactFullDTO> getListTransaction() {
		List<TransactionEntity> list= this.transactionRepository.findAll();
		log.info("El usuario {}  consulto todos los movimiento",session.getContextSessionThread().getName());
		return GenericMapper.mapList(list, TransactFullDTO.class);
	}
	
	private void validateNegativeBalance(ProductEntity productEntity, BigDecimal monto) {
		if(productEntity.getTipoProducto().equals(ETipoProducto.AHORROS.getCode())
				&& productEntity.getSaldo().subtract(monto).doubleValue()  < 0D ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"La transacción no se puede realizar porque el saldo seria negativo y el tipo de cuenta origen es ahorros");
		}	
	}
	
	private ProductEntity validatePrduct(String numeroCuenta) {
		ProductEntity productEntity= this.productRepository.findByNumeroCuenta(numeroCuenta);
		if(productEntity==null) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cuenta no existe");
		if(!productEntity.getEstado().equals(ECuentaEstado.ACTIVA.getCode()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cuenta no se encuetra activa");
		return productEntity;
	}

	

	

	

}
