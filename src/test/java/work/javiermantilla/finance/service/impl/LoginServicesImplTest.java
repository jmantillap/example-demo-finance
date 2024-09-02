package work.javiermantilla.finance.service.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletResponse;
import work.javiermantilla.finance.security.JwtUtil;
import work.javiermantilla.finance.service.LoginServices;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(classes = { LoginServicesImpl.class })
class LoginServicesImplTest {

	@Autowired
    private LoginServices loginServices;

	@MockBean    
    private JwtUtil jwtUtil;
	
	private String cadena="eyJhbGciOiJIUzI1NiJ9.eyJpZFVzZXIiOiIwMDEiLCJuYW1lIjoidXNlciBkZW1vIiwiaXNzIjoiMDAxIiwic3ViIjoidXNlciBkZW1vIiwiaWF0IjoxNzI1MjU1NjE2LCJleHAiOjE3MjUyOTg4MTZ9.X4aWkotHTeGuVI-Xy5vR7sgGEQXbUlzxOsGxkAmrHyY";
	private String cadenaDos="finance_token=eyJhbGciOiJIUzI1NiJ9.eyJpZFVzZXIiOiIwMDEiLCJuYW1lIjoidXNlciBkZW1vIiwiaXNzIjoiMDAxIiwic3ViIjoidXNlciBkZW1vIiwiaWF0IjoxNzI1MjU1NjE2LCJleHAiOjE3MjUyOTg4MTZ9.X4aWkotHTeGuVI-Xy5vR7sgGEQXbUlzxOsGxkAmrHyY; Max-Age=960; Expires=Mon, 02 Sep 2024 05:56:16 GMT; Domain=localhost; Path=/; Secure; HttpOnly\r\n"
			+ "";
	private String cadenaTres="_token=eyJhbGciOiJIUzI1NiJ9.eyJpZFVzZXIiOiIwMDEiLCJuYW1lIjoidXNlciBkZW1vIiwiaXNzIjoiMDAxIiwic3ViIjoidXNlciBkZW1vIiwiaWF0IjoxNzI1MjU1NjE2LCJleHAiOjE3MjUyOTg4MTZ9.X4aWkotHTeGuVI-Xy5vR7sgGEQXbUlzxOsGxkAmrHyY; Max-Age=960; Expires=Mon, 02 Sep 2024 05:56:16 GMT; Domain=localhost; Path=/; Secure; HttpOnly\r\n"
			+ "";
    
    @Test
	void testGetToken() {
    	when(jwtUtil.createJWT(anyString(), anyString())).thenReturn("token");
    	assertEquals("token",this.loginServices.getToken("nombre"));
    	
	}

    @Test
	void testCreateCookieSession() {
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	assertNotNull(this.loginServices.createCookieSession(cadena, response));
    	
    	ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
    		this.loginServices.createCookieSession(cadena, null);
		});
		assertFalse(exception.getMessage().isEmpty());
		
		exception = assertThrows(ResponseStatusException.class, () -> {
    		this.loginServices.createCookieSession("", response);
		});
		assertFalse(exception.getMessage().isEmpty());
		
	}

	@Test
	void testGetUserDataCookie() {				
		assertNull(this.loginServices.getUserDataCookie(cadenaDos));
	}

	@Test
	void testGetDataCookie() {
		assertNotNull(this.loginServices.getDataCookie(cadenaDos));		
		assertNull(this.loginServices.getDataCookie(cadenaTres));
	}

}
