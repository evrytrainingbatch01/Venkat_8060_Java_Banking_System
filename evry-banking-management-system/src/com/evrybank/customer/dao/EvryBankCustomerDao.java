package com.evrybank.customer.dao;

import java.util.List;

import com.evrybank.user.model.Account;
import com.evrybank.user.model.Customer;

public interface EvryBankCustomerDao {
	public boolean addMoney(int cid, int amount);

	public List<Customer> viewAccountDetails(int cid);

	public void sendMoney(int cid, int rid, int amount);

	public boolean loanRequest(int cid, int loanAmount);
}
