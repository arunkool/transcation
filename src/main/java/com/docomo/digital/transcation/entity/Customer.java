package com.docomo.digital.transcation.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "customer")
@JsonIgnoreProperties(ignoreUnknown =true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Customer {
	
	
	public Customer(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Customer() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue
	private int id;
	private String name;
	@Column(nullable = false)
	private long phoneNo;
	private int balance;
	 @OneToMany(targetEntity = Transcation.class,cascade = CascadeType.ALL)
	    @JoinColumn(name ="cp_fk",referencedColumnName = "id")
	private List<Transcation> transcations;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public List<Transcation> getTranscations() {
		return transcations;
	}
	public void setTranscations(List<Transcation> transcations) {
		this.transcations = transcations;
	}
	
	public void addTranscation(Transcation transcation) {
		transcations.add(transcation);
	}
	 
}
