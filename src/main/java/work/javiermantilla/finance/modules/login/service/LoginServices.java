package work.javiermantilla.finance.modules.login.service;

import jakarta.servlet.http.HttpServletResponse;
import work.javiermantilla.finance.modules.login.dto.UserContextSessionDTO;

public interface LoginServices {
	
	String getToken(String userName);	
	HttpServletResponse createCookieSession(String token, HttpServletResponse response);
	UserContextSessionDTO getUserDataCookie(String cookieSession);
	String getDataCookie(String cookieSession);
}
