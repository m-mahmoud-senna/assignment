package com.ingenico.assignment;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.ingenico.assignment.model.Account;
import com.ingenico.assignment.model.Transfer;
import com.ingenico.assignment.repository.DataRepository;
import com.ingenico.assignment.service.TransferService;

/**
 * Testing different Rest APIs and different business scenarios
 * 
 * @author <b>Mohamed Mahmoud</b> (m.mahmoud.senna@gmail.com)
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BankingControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private TransferService transferService;

	@Autowired
	private DataRepository repository;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters)
				.stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny()
				.orElse(null);

		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		repository.deleteAllInBatch();
		Account account1 = new Account("10304060", new BigDecimal(10.66));
		Account account2 = new Account("445551111", new BigDecimal(500));

		transferService.createAccount(account1);
		transferService.createAccount(account2);
	}

	/**
	 * test Account creating API
	 * 
	 * @throws Exception
	 */
	@Test
	public void createAccount() throws Exception {
		String accountJson = json(new Account("23444662233", new BigDecimal(100)));

		this.mockMvc.perform(post("/account").contentType(contentType)
				.content(accountJson))
				.andExpect(status().isOk());
	}

	/**
	 * Get Accounts API
	 * 
	 * @throws Exception
	 */
	@Test
	public void getAccounts() throws Exception {

		this.mockMvc.perform(get("/account").contentType(contentType))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].number", equalTo("10304060")))
				.andExpect(jsonPath("$[0].balance", equalTo(10.66)))
				.andExpect(jsonPath("$[1].number", equalTo("445551111")))
				.andExpect(jsonPath("$[1].balance", equalTo(500.0)));
	}

	/**
	 * Account not found business scenario
	 * 
	 * @throws Exception
	 */
	@Test
	public void accountNotFound() throws Exception {
		this.mockMvc.perform(get("/account/66446333333").contentType(contentType))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.message", equalTo("Couldn't find account [66446333333]")));
	}

	/**
	 * Test transfer API
	 * 
	 * @throws Exception
	 */
	@Test
	public void transfer() throws Exception {
		Transfer transferRequest = new Transfer();
		transferRequest.setSourceAccountNumber("445551111");
		transferRequest.setDestinationAccountNumber("10304060");
		transferRequest.setAmount(new BigDecimal(200));
		String transferJson = json(transferRequest);

		// Make transfer
		this.mockMvc.perform(put("/transfer").content(transferJson)
				.contentType(contentType))
				.andExpect(status().isOk());

		this.mockMvc.perform(get("/account/10304060").contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.balance", equalTo(210.66)));

		this.mockMvc.perform(get("/account/445551111").contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.balance", equalTo(300.0)));

	}

	/**
	 * Transfer insufficient funds business scenario
	 * 
	 * @throws Exception
	 */
	@Test
	public void insuffecientFunds() throws Exception {
		Transfer transferRequest = new Transfer();
		transferRequest.setSourceAccountNumber("445551111");
		transferRequest.setDestinationAccountNumber("10304060");
		transferRequest.setAmount(new BigDecimal(200000));
		String transferJson = json(transferRequest);

		// Make transfer with insufficient funds
		this.mockMvc.perform(put("/transfer").content(transferJson)
				.contentType(contentType))
				.andExpect(status().isBadRequest());

		// Check that account balances didn't affected
		this.mockMvc.perform(get("/account/10304060").contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.balance", equalTo(10.66)));

		this.mockMvc.perform(get("/account/445551111").contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.balance", equalTo(500.0)));

	}

	private String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
