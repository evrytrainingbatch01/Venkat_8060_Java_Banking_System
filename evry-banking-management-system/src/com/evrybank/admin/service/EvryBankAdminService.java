package com.evrybank.admin.service;



public interface EvryBankAdminService {
	public int validateUser(final String userName, final String password);
	public boolean addCustomer();
	public boolean deleteCustomer();
	public boolean addMoney(final int cid, final int amount );
	public boolean aproveTransaction();
	public boolean provideLoans();
	public void viewAllCustomers();

}
