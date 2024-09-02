package work.javiermantilla.finance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.finance.dto.GenericResponseDTO;

import work.javiermantilla.finance.dto.transaction.TransactDTO;
import work.javiermantilla.finance.service.TransactServices;
import work.javiermantilla.finance.utils.FinanceConstants;


@RestController
@RequestMapping("/api/transaction")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Log4j2
public class TransactionController {
	
	private final TransactServices transactServices;
	private GenericResponseDTO genericResponse;
	
	
	@PostMapping(value = "/consignment-money", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consignmentMoneyTransaction(@Valid @RequestBody TransactDTO consignment) {		
		log.info("Inicio de consignacion a crear : {}",consignment);
		genericResponse = new GenericResponseDTO(
				transactServices.consignmentMoney(consignment),
				true, 
				FinanceConstants.RESPONSE_CREATED,
				HttpStatus.OK, 
				FinanceConstants.TITTLE_CREATED);
				
		return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/withdraw-money", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> withdrawMoney(@Valid @RequestBody TransactDTO withdrawMoney) {		
		log.info("Inicio de retiro de dinero a crear : {}",withdrawMoney);
		genericResponse = new GenericResponseDTO(
				transactServices.withdrawMoney(withdrawMoney),
				true, 
				FinanceConstants.RESPONSE_CREATED,
				HttpStatus.OK, 
				FinanceConstants.TITTLE_CREATED);
				
		return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
	}
		

}
