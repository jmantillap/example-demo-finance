package work.javiermantilla.finance.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.math.BigDecimal;

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
import work.javiermantilla.finance.modules.transaction.controller.TransactionController;
import work.javiermantilla.finance.modules.transaction.dto.TransactDTO;
import work.javiermantilla.finance.modules.transaction.dto.TransactFullDTO;
import work.javiermantilla.finance.modules.transaction.dto.TransferDTO;
import work.javiermantilla.finance.modules.transaction.service.TransactServices;


@Log4j2
@WebMvcTest
@ContextConfiguration(classes = { TransactionController.class, TestSecurityConfig.class })
class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean
	private TransactServices transactServices;

	@BeforeEach
	protected void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	void testConsignmentMoneyTransaction() throws Exception {
		TransactDTO transactIn= new TransactDTO();
		transactIn.setMonto(BigDecimal.valueOf(10L));
		transactIn.setNumeroCuenta("535152770843");		
		String inputJson = JSONUtil.mapToJson(transactIn);
		
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/transaction/consignment-money")
				.content(inputJson).contentType(MediaType.APPLICATION_JSON))
				.andReturn();		
		assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void testWithdrawMoney() throws Exception {
		TransactDTO transactOut= new TransactDTO();
		transactOut.setMonto(BigDecimal.valueOf(10L));
		transactOut.setNumeroCuenta("535152770843");		
		String inputJson = JSONUtil.mapToJson(transactOut);
		
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/transaction/withdraw-money")
				.content(inputJson).contentType(MediaType.APPLICATION_JSON))
				.andReturn();		
		assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void testTransfer() throws Exception {
		TransferDTO transfer= new TransferDTO();
		transfer.setMonto(BigDecimal.valueOf(10L));
		transfer.setNumeroCuentaDestino("535152770843");
		transfer.setNumeroCuentaOrigen("535152770800");
		String inputJson = JSONUtil.mapToJson(transfer);
		
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/transaction/transfer")
				.content(inputJson).contentType(MediaType.APPLICATION_JSON))
				.andReturn();		
		assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void testListTransaction() throws Exception {
		TransactFullDTO tran =new TransactFullDTO();
		tran.setId(10);
		tran.setIdProducto(10);
		when(this.transactServices.getListTransaction()).thenReturn(List.of(tran));		
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/transaction/list")				
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();		
		log.info(mvcResult.getResponse().getContentAsString());
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

}
