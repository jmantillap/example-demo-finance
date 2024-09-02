package work.javiermantilla.finance.service.impl;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.finance.dto.transaction.TransactDTO;
import work.javiermantilla.finance.dto.transaction.TransactFullDTO;
import work.javiermantilla.finance.entity.ProductEntity;
import work.javiermantilla.finance.entity.TransactionEntity;
import work.javiermantilla.finance.repository.ProductRepository;
import work.javiermantilla.finance.repository.TransactionRepository;
import work.javiermantilla.finance.security.ContextSession;
import work.javiermantilla.finance.service.TransactServices;
import work.javiermantilla.finance.utils.ECuentaEstado;
import work.javiermantilla.finance.utils.ETipoMovimiento;
import work.javiermantilla.finance.utils.ETipoProducto;
import work.javiermantilla.finance.utils.GenericMapper;

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
		log.info("La cuenta: {} Quedo con saldo: {} ",transactDTO.getNumeroCuenta(), productEntity.getSaldo());
		
		return GenericMapper.map(transaction, TransactFullDTO.class);
	}
	
	@Override
	@Transactional
	public TransactFullDTO withdrawMoney(TransactDTO transactDTO) {		
		ProductEntity productEntity = this.validatePrduct(transactDTO.getNumeroCuenta());
		if(productEntity.getTipoProducto().equals(ETipoProducto.AHORROS.getCode())
				&& productEntity.getSaldo().subtract(transactDTO.getMonto()).doubleValue()  <= 0D ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"La transacción no se puede realizar porque el saldo seria negativo y el tipo de cuenta es ahorros");
		}				
		TransactionEntity transaction= new TransactionEntity();
		transaction.setProducto(productEntity);
		transaction.setFecha(LocalDateTime.now());
		transaction.setMonto(transactDTO.getMonto());
		transaction.setTipo(ETipoMovimiento.RETIRO.getCode());
		transaction.setSigno(ETipoMovimiento.RETIRO.getSigno());
		transaction = this.transactionRepository.save(transaction);		
		log.info("El usuario {} retiro: {} ",session.getContextSessionThread().getName(), transaction );
		
		productEntity.setSaldo(productEntity.getSaldo().subtract(transactDTO.getMonto()));		
		this.productRepository.save(productEntity);		
		log.info("La cuenta: {} Quedo con saldo: {} ",transactDTO.getNumeroCuenta(), productEntity.getSaldo());
		return GenericMapper.map(transaction, TransactFullDTO.class);
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
