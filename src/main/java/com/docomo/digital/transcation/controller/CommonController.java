package com.docomo.digital.transcation.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docomo.digital.transcation.entity.Customer;
import com.docomo.digital.transcation.entity.Response;
import com.docomo.digital.transcation.entity.Transcation;
import com.docomo.digital.transcation.exception.ResourceNotFoundException;
import com.docomo.digital.transcation.service.CommonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RestController
@RequestMapping("/docomo")
public class CommonController {

	@Autowired
	private CommonService service;

	@PostMapping("/addCustomer")
	public Response addCustomer(@RequestBody Customer customer) {
		service.saveCustomer(customer);
		return new Response(customer.getId() + " inserted", HttpStatus.CREATED.toString());

	}

	@GetMapping("/customerById/{id}")
	public Customer getCustomerById(@PathVariable int id) {
		return service.getCustomerById(id);
	}

	@PatchMapping("/chargeToCustomer/{id}")
	public Response chargeToCustomer(@PathVariable int id, @RequestBody String transactionStr) throws Exception {
		Transcation reqTranscation = json2obj(Transcation.class, transactionStr);
		service.chargeToCustomer(reqTranscation, id);
		return new Response("Successfully charged to customer", HttpStatus.OK.toString());
	}
	
	@PatchMapping("/refundToCustomer/{id}")
	public Response refundToCustomer(@PathVariable int id, @RequestBody String transactionStr)
			throws IOException, ResourceNotFoundException {
		Transcation reqTranscation = json2obj(Transcation.class, transactionStr);
		service.refundToCustomer(reqTranscation, id);
		return new Response("Successfully refunded to customer", HttpStatus.OK.toString());
	}


	@GetMapping("/transcationsByCustomer/{id}")
	public List<Transcation> getTranscationsByCustomerId(@PathVariable int id) {
		return service.getTranscationsByCustomerId(id);
	}

	public static <T> T json2obj(Class<T> clazz, String jsonObj) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper.readValue(jsonObj, clazz);
	}

}
