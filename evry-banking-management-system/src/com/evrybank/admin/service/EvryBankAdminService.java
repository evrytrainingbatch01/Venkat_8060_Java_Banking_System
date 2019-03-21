package com.evrybank.admin.service;



public interface EvryBankAdminService {
	public boolean addCustomer();
	public boolean deleteCustomer();
	public boolean addMoneyToCustomer(final int cid);
	public boolean aproveTransaction(final int cid);
	public boolean provideLoans(final int cid);
	public void viewAllCustomers();

}
