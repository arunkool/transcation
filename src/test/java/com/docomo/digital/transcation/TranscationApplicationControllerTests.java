package com.docomo.digital.transcation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.docomo.digital.transcation.entity.Customer;
import com.docomo.digital.transcation.entity.Response;
import com.docomo.digital.transcation.entity.Transcation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TranscationApplicationControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	ObjectMapper om = new ObjectMapper();

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	private String mockTranscation = "{\"name\": \"test pack\", \"amount\": 1}";

	@Test
	public void addCustomerTest() throws Exception {
		Customer cust = new Customer();
		cust.setName("TestCustomer");
		cust.setPhoneNo(1234567890);
		cust.setBalance(10);
		String jsonRequest = om.writeValueAsString(cust);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/docomo/addCustomer").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		Response response = om.readValue(resultContent, Response.class);
		Assert.assertTrue(response.getStatus().equals(HttpStatus.CREATED.toString()));

	}

	@Test
	public void refundToCustomerTest() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/docomo/refundToCustomer/1")
				.content(mockTranscation).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andReturn();
		String resultContent = result.getResponse().getContentAsString();
		Response response = om.readValue(resultContent, Response.class);
		Assert.assertTrue(response.getStatus().equals(HttpStatus.OK.toString()));
		Assert.assertTrue(response.getMessage().equals("Successfully refunded to customer"));

	}

	@Test
	public void chargeToCustomerTest() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/docomo/chargeToCustomer/1")
				.content(mockTranscation).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andReturn();
		String resultContent = result.getResponse().getContentAsString();
		Response response = om.readValue(resultContent, Response.class);
		Assert.assertTrue(response.getStatus().equals(HttpStatus.OK.toString()));
		Assert.assertTrue(response.getMessage().equals("Successfully charged to customer"));

	}

	@Test
	public void getTranscationsByCustomerIdTest() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/docomo/transcationsByCustomer/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
		List<Transcation> actual = om.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<Transcation>>() {
				});

		Assert.assertTrue(actual.get(0).getCustomerId() == 1);

	}

}
