package work.javiermantilla.finance.controller;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
import work.javiermantilla.finance.dto.product.CuentaDTO;
import work.javiermantilla.finance.dto.product.ProductDTO;
import work.javiermantilla.finance.service.ProductServices;
import work.javiermantilla.finance.utils.JSONUtil;

@Log4j2
@WebMvcTest
@ContextConfiguration(classes = { ProductController.class, TestSecurityConfig.class })
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean
	private ProductServices productServices;

	@BeforeEach
	protected void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void testCreateProducto() throws Exception {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(-1);
		productDTO.setIdCliente(10);
		productDTO.setTipoProducto("CA");
		productDTO.setExcentaGmf(true);		
		String inputJson = JSONUtil.mapToJson(productDTO);
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/product/create")
						.content(inputJson).contentType(MediaType.APPLICATION_JSON))
				.andReturn();		
		assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
	}	
	

	@Test
	void testInactivateProducto() throws Exception {
		CuentaDTO cuentaInactivar = new CuentaDTO();
		cuentaInactivar.setNumeroCuenta("330258148292");
		String inputJson = JSONUtil.mapToJson(cuentaInactivar);		
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/product/inactivate")
				.content(inputJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();		
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());		
		
	}

	@Test
	void testActivateProducto() throws Exception {
		CuentaDTO cuentaActivar = new CuentaDTO();
		cuentaActivar.setNumeroCuenta("330258148290");
		String inputJson = JSONUtil.mapToJson(cuentaActivar);		
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/product/activate")
				.content(inputJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();		
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
		
	}

	@Test
	void testCancelProducto() throws Exception {
		CuentaDTO cuentaCancelar= new CuentaDTO();
		cuentaCancelar.setNumeroCuenta("330258148000");
		String inputJson = JSONUtil.mapToJson(cuentaCancelar);		
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/product/cancel")
				.content(inputJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void testListClient() throws Exception {
		ProductDTO prod =new ProductDTO();
		prod.setId(10);
		when(this.productServices.getListProduct()).thenReturn(List.of(prod));		
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/product/list")				
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();		
		log.info(mvcResult.getResponse().getContentAsString());
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

}
