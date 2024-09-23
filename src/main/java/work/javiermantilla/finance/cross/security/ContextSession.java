package work.javiermantilla.finance.cross.security;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import work.javiermantilla.finance.modules.login.dto.UserContextSessionDTO;

@Component
public class ContextSession {
	
	public UserContextSessionDTO getContextSessionThread() {
		return contextSessionThread();
	}
	
	private UserContextSessionDTO contextSessionThread() {
		try {
			String contextoJSON = Thread.currentThread().getName();
			UserContextSessionDTO usuarioContextoSesionDTO = new UserContextSessionDTO();						
			return usuarioContextoSesionDTO.toUserContextSessionDto(contextoJSON);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
