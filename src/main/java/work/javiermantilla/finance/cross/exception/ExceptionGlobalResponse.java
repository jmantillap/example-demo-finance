package work.javiermantilla.finance.cross.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ValidationException;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.finance.cross.dto.GenericResponseDTO;
import work.javiermantilla.finance.cross.util.FinanceConstants;

@ControllerAdvice
@Log4j2
public class ExceptionGlobalResponse {
	
	private GenericResponseDTO result;
	private static final String ERROR= "Error";

	@ExceptionHandler(HttpServerErrorException.class)
	protected ResponseEntity<Object> handleHttpServer(HttpServerErrorException ex) {
		log.error(ex.getMessage(), ex);
		result = new GenericResponseDTO(ERROR, false, "[HttpServerError Response] - Exception: " + ex.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ValidationException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(ValidationException ex) {
		log.error(ex.getMessage(), ex);
		result = new GenericResponseDTO(ERROR, false,
				"[ValidationException Response] - Exception: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@SuppressWarnings("null")
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrity(DataIntegrityViolationException ex) {
		log.error(ex.getMessage(), ex);
		result = new GenericResponseDTO(ERROR, false,
				"[DataIntegrityViolation Response] - Exception: " + ex.getRootCause().getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		log.error(ex.getMessage(), ex);
		String mensaje=ERROR;
		if(ex.getMessage().contains("JSON parse error: Cannot deserialize value of type `java.time.LocalDate`")) {
			mensaje="Formato de fecha erroneo. El formato debe ser 'YYYY-MM-DD'";
		}
		result = new GenericResponseDTO(mensaje, false,FinanceConstants.MENSAJE_ERROR_VALIDACION_CAMPOS,HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		//log.error(ex.getMessage(), ex);
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream().toList();		
		result = new GenericResponseDTO(this.getErrorsMap(fieldErrors), false,
				FinanceConstants.MENSAJE_ERROR_VALIDACION_CAMPOS,
				HttpStatus.BAD_REQUEST);		
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ArgumentNotValidException.class)
	protected ResponseEntity<Object> handleArgumentNotValidException(ArgumentNotValidException ex) {
		//log.error(ex.getMessage(), ex);
		List<FieldError> fieldErrors = ex.getListFieldErrors().stream().toList();
		result = new GenericResponseDTO(this.getErrorsMap(fieldErrors), false,
				FinanceConstants.MENSAJE_ERROR_VALIDACION_CAMPOS,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
	
	private Map<String, List<FieldErrorDTO>> getErrorsMap(List<FieldError> fields) {
		Map<String, List<FieldErrorDTO>> errorResponse = new HashMap<>();
		List<FieldErrorDTO> lista = fields.stream().map(f -> {
			return new FieldErrorDTO(f.getField(), f.getDefaultMessage());
		}).toList();
		errorResponse.put("errors", lista);
		return errorResponse;
	}

	@ExceptionHandler(ResponseStatusException.class)
	protected ResponseEntity<Object> handleHttpServer(ResponseStatusException ex) {		
		String[] datos = ex.getMessage().split("\"");
		String mensaje = ex.getMessage();
		for (String cadena : datos) {
			if (!cadena.contains("SERVER")) {
				mensaje = cadena;
			}
		}
		String data = ERROR;
		if (ex.getStatusCode().value() == 400 || ex.getStatusCode().value() == 204
				|| ex.getStatusCode().value() == 206) {
			data = null;
		} else {
			log.error(ex.getMessage());
		}
		result = new GenericResponseDTO(data, false, mensaje, HttpStatus.valueOf(ex.getStatusCode().value()));
		return new ResponseEntity<>(result, ex.getStatusCode());
	}
}
