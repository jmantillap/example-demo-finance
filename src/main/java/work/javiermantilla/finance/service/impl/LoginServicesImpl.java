package work.javiermantilla.finance.service.impl;

import java.util.Calendar;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import work.javiermantilla.finance.dto.UserContextSessionDTO;
import work.javiermantilla.finance.security.JwtUtil;
import work.javiermantilla.finance.service.LoginServices;
import work.javiermantilla.finance.utils.FinanceConstants;
import work.javiermantilla.finance.utils.UtilFinance;

@Service
@RequiredArgsConstructor
public class LoginServicesImpl implements LoginServices {

	private final JwtUtil jwtUtil;

	@Override
	public String getToken(String userName) {
		return jwtUtil.createJWT("001", userName);
	}

	@Override
	public HttpServletResponse createCookieSession(String token, HttpServletResponse response) {
		try {
			String dominio = FinanceConstants.DOMAIN_LOCAL_COOKIE;
			Calendar date = Calendar.getInstance();
			String expiracion = obtenerExpiracionToken(token);
			date.setTimeInMillis(Long.parseLong(expiracion));
			Cookie cookie = new Cookie("finance_token", token);
			Cookie cookieSegura = createCookieHttps(cookie, date, dominio);
			response.addHeader("Set-Cookie", transformStringCookie(cookieSegura, date));
			response.addCookie(cookieSegura);
			return response;

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error");
		}
	}

	private String transformStringCookie(Cookie cookie, Calendar expires) {

		return cookie.getName() + "=" + cookie.getValue() + ";Domain="
				+ ((cookie.getDomain() == null) ? "/" : cookie.getDomain()) + ";Path="
				+ ((cookie.getPath() == null) ? "/" : cookie.getPath()) + ";Expires=" + expires.getTime() + ";Secure"
				+ ";HttpOnly" + ";SameSite=None";
	}

	private Cookie createCookieHttps(Cookie c, Calendar expDate, String domain) {
		Cookie cookie1 = null;
		int maxAge = expDate.get(Calendar.SECOND) * 60;
		c.setHttpOnly(true);
		c.setSecure(true);
		c.setMaxAge(maxAge);
		c.setDomain(domain);
		c.setPath("/");
		cookie1 = c;
		return cookie1;
	}

	private String obtenerExpiracionToken(String jwt) {
		String[] tokenObject = jwt.split("\\.");
		String payload = "";

		if (tokenObject.length > 1) {
			payload = UtilFinance.decodificarBase64Cadena(tokenObject[1]);
		}
		String jsonExp = "";
		try {
			jsonExp = UtilFinance.obtenerAtributoStringJSON("exp", payload);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error");
		}
		return jsonExp + "000";
	}

	@Override
	public UserContextSessionDTO getUserDataCookie(String cookieSesion) {
		String token = this.getTokenCookie(cookieSesion);
		return token != null ? jwtUtil.extraerContextoJWT(token) : null;

	}

	@Override
	public String getDataCookie(String cookieSession) {		
		String token = this.getTokenCookie(cookieSession);		
		return token != null ? "finance_token=" + token : null;
	}
	
	private String getTokenCookie(String cookieSession) {
		String[] cookieArray = cookieSession.split(";");
		String token = null;
		for (String cookiePart : cookieArray) {
			if (cookiePart.contains("finance_token")) {
				String[] tokenArray = cookiePart.split("=");
				token = tokenArray[1].isEmpty() ? null : tokenArray[1];
				break;
			}
		}
		return token;
	}

}
