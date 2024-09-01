package work.javiermantilla.finance.service;

import jakarta.servlet.http.HttpServletResponse;
import work.javiermantilla.finance.dto.login.UserContextSessionDTO;

public interface LoginServices {
	
	String getToken(String userName);	
	HttpServletResponse createCookieSession(String token, HttpServletResponse response);
	UserContextSessionDTO getUserDataCookie(String cookieSession);
	String getDataCookie(String cookieSession);
}
