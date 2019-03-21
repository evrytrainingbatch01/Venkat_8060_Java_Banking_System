package com.evrybank.admin.dao;

import java.util.List;

import com.evrybank.user.model.Customer;

public interface EvryBankAdminDao {
	public Customer checkUser(final String userName, final String password);
	public boolean addCustomer(final Customer customer);
	public boolean deleteCustomer(final int cid);
	public List<Customer> viewAllCustomers();
	public boolean addMoneyToCustomer(final int cid);
	public boolean aproveTransaction(int cid);
	public boolean provideLoans(int cid);
}
