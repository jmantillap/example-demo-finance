package work.javiermantilla.finance.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.servlet.http.HttpServletResponse;
import work.javiermantilla.finance.config.TestSecurityConfig;
import work.javiermantilla.finance.dto.login.AutenticationDTO;
import work.javiermantilla.finance.service.LoginServices;
import work.javiermantilla.finance.utils.JSONUtil;


@WebMvcTest
@ContextConfiguration(classes = { LoginController.class, TestSecurityConfig.class })
class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean
	private LoginServices loginServices;

	@BeforeEach
	protected void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	
	@Test
	void testAutenticacion() throws Exception { 
		AutenticationDTO autenticationDTO= new AutenticationDTO("DEMO");
		String inputJson = JSONUtil.mapToJson(autenticationDTO);		
		when(this.loginServices.getToken(anyString())).thenReturn("token");
		HttpServletResponse response= mock(HttpServletResponse.class);
		when(response.getHeader("Set-Cookie")).thenReturn("cookie");
		when(this.loginServices.createCookieSession(anyString(), any())).thenReturn(response);
		when(this.loginServices.getDataCookie(anyString())).thenReturn("finance_token=token");				
		
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/security/login")
				.content(inputJson).contentType(MediaType.APPLICATION_JSON))
				.andReturn();		
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

}
