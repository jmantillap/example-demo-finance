package work.javiermantilla.finance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.finance.dto.AutenticationDTO;
import work.javiermantilla.finance.dto.GenericResponseDTO;
import work.javiermantilla.finance.service.LoginServices;
import work.javiermantilla.finance.utils.FinanceConstants;

@RestController
@RequestMapping("/api/security")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Log4j2
public class LoginController {
	private GenericResponseDTO genericResponse;
	private final LoginServices loginServices;

	@PostMapping("/login")
	public ResponseEntity<GenericResponseDTO> autenticacion(@Valid @RequestBody AutenticationDTO login,
			HttpServletResponse response) {

		String token = this.loginServices.getToken(login.getName());
		response = this.loginServices.createCookieSession(token, response);
		
		String dataCookie =this.loginServices.getDataCookie(response.getHeader("Set-Cookie"));
		genericResponse = new GenericResponseDTO(
				dataCookie ,
				true, 
				FinanceConstants.RESPONSE_LOGIN,
				HttpStatus.OK, 
				FinanceConstants.TITTLE_FIND);
		log.info("Generación de cookie: {}",dataCookie);
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);

	}
}
