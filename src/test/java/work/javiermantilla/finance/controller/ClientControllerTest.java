package work.javiermantilla.finance.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDate;
import java.util.List;

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

import lombok.extern.log4j.Log4j2;
import work.javiermantilla.finance.config.TestSecurityConfig;
import work.javiermantilla.finance.cross.util.JSONUtil;
import work.javiermantilla.finance.modules.client.controller.ClientController;
import work.javiermantilla.finance.modules.client.dto.ClientDTO;
import work.javiermantilla.finance.modules.client.service.ClientServices;


@Log4j2
@WebMvcTest
@ContextConfiguration(classes = { ClientController.class, TestSecurityConfig.class })
class ClientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean
	private ClientServices clientServices;

	@BeforeEach
	protected void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	void testCreateClient() throws Exception {
		ClientDTO clienteCreate = new ClientDTO();
		clienteCreate.setId(10);
		clienteCreate.setEmail("javier@hotmail.com");
		clienteCreate.setApellido("Apellido");
		clienteCreate.setNombre("Nombre");
		clienteCreate.setNumeroIdentificacion("13544171");
		clienteCreate.setTipoIdentificacion("CC");
		clienteCreate.setFechaNacimiento(LocalDate.of(1978,11,7));
		
		String inputJson = JSONUtil.mapToJson(clienteCreate);
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/client/create")
						.content(inputJson).contentType(MediaType.APPLICATION_JSON))
				.andReturn();		
		assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void testUpdateClient() throws Exception {
		ClientDTO clienteUpdate= new ClientDTO();
		clienteUpdate.setId(10);
		clienteUpdate.setEmail("javier@hotmail.com");
		clienteUpdate.setApellido("Apellido");
		clienteUpdate.setNombre("Nombre");
		clienteUpdate.setNumeroIdentificacion("13544171");
		clienteUpdate.setTipoIdentificacion("CC");
		clienteUpdate.setFechaNacimiento(LocalDate.of(1978,11,7));
		
		String inputJson = JSONUtil.mapToJson(clienteUpdate);
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.put("/api/client/update")
						.content(inputJson).contentType(MediaType.APPLICATION_JSON))
				.andReturn();		
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void testDeleteClient() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.delete("/api/client/delete/{id}",10)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();		
		assertEquals(HttpStatus.NO_CONTENT.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void testListClient() throws Exception {
		ClientDTO client =new ClientDTO();
		client.setId(10);
		client.setFechaNacimiento(LocalDate.now());
		when(this.clientServices.getListClients()).thenReturn(List.of(client));		
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/client/list")				
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();		
		log.info(mvcResult.getResponse().getContentAsString());
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

}
