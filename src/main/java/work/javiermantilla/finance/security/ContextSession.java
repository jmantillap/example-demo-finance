package work.javiermantilla.finance.security;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import work.javiermantilla.finance.dto.login.UserContextSessionDTO;

@Component
public class ContextSession {
		
	public UserContextSessionDTO obtenerContextoDeSesionEnHilo() {
		try {
			String contextoJSON = Thread.currentThread().getName();
			UserContextSessionDTO usuarioContextoSesionDTO = new UserContextSessionDTO();						
			return usuarioContextoSesionDTO
					.toUserContextSessionDto(contextoJSON);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	public UserContextSessionDTO getContextoDeSesionEnHilo() {
		return obtenerContextoDeSesionEnHilo();
	}
}
