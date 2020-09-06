package com.docomo.digital.transcation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.docomo.digital.transcation.entity.Customer;


public interface CommonRepository extends JpaRepository<Customer,Integer> {

}
