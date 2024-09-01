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
import work.javiermantilla.finance.dto.product.ProductDTO;
import work.javiermantilla.finance.service.ProductServices;
import work.javiermantilla.finance.utils.FinanceConstants;



@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

	private final ProductServices productServices;
	private GenericResponseDTO genericResponse;
	

	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createProducto(@Valid @RequestBody ProductDTO requestCreateProduct) {
		
		log.info("Inicio de cliente a crear : {}",requestCreateProduct);
		genericResponse = new GenericResponseDTO(
				productServices.createProduct(requestCreateProduct),
				true, 
				FinanceConstants.RESPONSE_CREATED,
				HttpStatus.OK, 
				FinanceConstants.TITTLE_CREATED);
				
		return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
	}
}
