package com.evrybank.customer.service;

import java.util.List;

import com.evrybank.user.model.Account;

public interface EvryBankCustomerService {
	public boolean addMoney();
	public List<Account> viewAccountDetails();
	public boolean sendMoney();
	public int withdrawMoney();
	public boolean loanRequest();
	
}
