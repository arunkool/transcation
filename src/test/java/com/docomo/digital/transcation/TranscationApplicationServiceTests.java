package com.docomo.digital.transcation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.docomo.digital.transcation.entity.Customer;
import com.docomo.digital.transcation.entity.Transcation;
import com.docomo.digital.transcation.repository.CommonRepository;
import com.docomo.digital.transcation.service.CommonService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranscationApplicationServiceTests {
	@Autowired
	private CommonService service;

	@MockBean
	private CommonRepository repository;
	
	@Test
	public void getUserByIdTest() {
		Customer customer = new Customer(1, "Test");
		Optional<Customer> custOptional = Optional.of(customer);
		when(repository.findById(1)).thenReturn(custOptional);
		assertEquals(service.getCustomerById(1).getName(), "Test");
	}
	
	@Test
	public void getTranscationsByCustomerIdTest() {
		Customer customer = new Customer();
		customer.setId(1);
		customer.setPhoneNo(1234567890);
		customer.setBalance(5);
		Transcation trans = new Transcation();
		trans.setAmount(10);
		trans.setCustomerId(customer.getId());
		customer.setTranscations(Stream
				.of(trans).collect(Collectors.toList()));
		Optional<Customer> custOptional = Optional.of(customer);
		when(repository.findById(1)).thenReturn(custOptional);
		assertEquals(service.getCustomerById(1).getTranscations().get(0).getCustomerId(), 1);
	}
}
