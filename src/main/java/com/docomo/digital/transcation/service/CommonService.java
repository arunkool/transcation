package com.docomo.digital.transcation.service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docomo.digital.transcation.entity.Customer;
import com.docomo.digital.transcation.entity.Transcation;
import com.docomo.digital.transcation.enums.TranscationType;
import com.docomo.digital.transcation.exception.NotEnoughBalanceException;
import com.docomo.digital.transcation.exception.ResourceNotFoundException;
import com.docomo.digital.transcation.repository.CommonRepository;

@Service
public class CommonService {
	@Autowired
	private CommonRepository repository;

	public Customer saveCustomer(Customer customer) {
		return repository.save(customer);
	}

	public Customer getCustomerById(int id) {
		return repository.findById(id).orElse(null);
	}

	public String deleteCustomer(int id) {
		repository.deleteById(id);
		return "Customer removed !! " + id;
	}

	public Customer refundToCustomer(Transcation transcation, int customerId) {
		Customer dbCustomer = repository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + customerId));
		int currentBalance = dbCustomer.getBalance();
		dbCustomer.setBalance(currentBalance + transcation.getAmount());
		transcation.setDate(new Date(System.currentTimeMillis()));
		transcation.setType(TranscationType.REFUND);
		transcation.setCustomerId(dbCustomer.getId());
		dbCustomer.addTranscation(transcation);

		return repository.save(dbCustomer);
	}

	public Customer chargeToCustomer(Transcation transcation, int customerId) {
		Customer dbCustomer = repository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID >>" + customerId));
		int currentBalance = dbCustomer.getBalance();
		if (currentBalance > transcation.getAmount()) {
			dbCustomer.setBalance(currentBalance - transcation.getAmount());
			transcation.setDate(new Date(System.currentTimeMillis()));
			transcation.setType(TranscationType.CHARGE);
			transcation.setCustomerId(dbCustomer.getId());
			dbCustomer.addTranscation(transcation);
		} else {
			throw new NotEnoughBalanceException("Customer has insufficient balance");
		}

		return repository.save(dbCustomer);
	}

	public List<Transcation> getTranscationsByCustomerId(int id) {
		Customer dbCustomer = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID >>" + id));
		List<Transcation> transcations = dbCustomer.getTranscations();
		transcations.sort(Comparator.comparing(Transcation::getType));
		return transcations;
	}
}
