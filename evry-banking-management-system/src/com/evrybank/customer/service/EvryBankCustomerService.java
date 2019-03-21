package com.evrybank.customer.service;

import java.util.List;

import com.evrybank.user.model.Account;

public interface EvryBankCustomerService {
	public boolean addMoney(int amount, int cid);
	public void viewAccountDetails(int cid);
	public void sendMoney(int cid, int rid, int amount);
	public void withdrawMoney(int cid, int withdrawAmount);
	public boolean loanRequest(int cid, int loanAmount);
	
}
