package work.javiermantilla.finance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.finance.dto.GenericResponseDTO;
import work.javiermantilla.finance.dto.client.ClientFullDTO;
import work.javiermantilla.finance.service.ClientServices;
import work.javiermantilla.finance.utils.FinanceConstants;


@RestController
@RequestMapping("/api/client")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Log4j2
public class ClientController {
	
	private final ClientServices clientServices;
	private GenericResponseDTO genericResponse;

	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createClient(@Valid @RequestBody ClientFullDTO requestCreateClient) {
		
		log.info("Inicio de cliente a crear : {}",requestCreateClient);
		genericResponse = new GenericResponseDTO(
				clientServices.createClient(requestCreateClient),
				true, 
				FinanceConstants.RESPONSE_CREATED,
				HttpStatus.OK, 
				FinanceConstants.TITTLE_CREATED);
				
		return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateClient(@Valid @RequestBody ClientFullDTO requestUpdateClient) {
		
		log.info("Inicio de cliente a Actualizar : {}",requestUpdateClient);
		genericResponse = new GenericResponseDTO(
				clientServices.updateClient(requestUpdateClient),
				true, 
				FinanceConstants.RESPONSE_UPDATE,
				HttpStatus.OK, 
				FinanceConstants.TITTLE_UPDATE);
				
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);
	}
	 
	@DeleteMapping("/delete/{id}") 
	public ResponseEntity<Object> deleteClient(@PathVariable Integer id) {
		
		log.info("Inicio de Cliente a eliminar con id : {}",id);		
		genericResponse = new GenericResponseDTO(
				this.clientServices.deleteClient(id),
				true, 
				FinanceConstants.RESPONSE_DELETE,
				HttpStatus.OK, 
				FinanceConstants.TITTLE_DELETE);
				
		return new ResponseEntity<>(genericResponse, HttpStatus.NO_CONTENT);		
	}
	
	
	@GetMapping("/list") 
	public ResponseEntity<Object> listClient() {
		
		log.info("Listado de todos los clientes");		
		genericResponse = new GenericResponseDTO(
				this.clientServices.getListClients(),
				true, 
				FinanceConstants.RESPONSE_FIND,
				HttpStatus.OK, 
				FinanceConstants.TITTLE_FIND);
				
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);		
	}

	
	
}
