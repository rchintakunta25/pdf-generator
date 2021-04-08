package com.poc.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.poc.model.Customer;
import com.poc.model.LoginUser;

@Service
public class CustomerService {

	private LoginUser loginUser;

	public List<Customer> getCustomers() {
		final List<Customer> customers = IntStream.range(1, 10)
				.mapToObj(v -> Customer.builder().id(v).name("Name " + v).lastName("Last Name " + v).build())
				.collect(Collectors.toList());
		return customers;
	}

	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}

	public LoginUser getLoginUser() {
		return loginUser;
	}
}