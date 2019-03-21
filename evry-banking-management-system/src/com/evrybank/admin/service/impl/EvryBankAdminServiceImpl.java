package com.evrybank.admin.service.impl;

import java.util.List;
import java.util.Scanner;

import com.evrybank.admin.dao.EvryBankAdminDao;
import com.evrybank.admin.dao.impl.EvryBankAdminDaoImpl;
import com.evrybank.admin.service.EvryBankAdminService;
import com.evrybank.user.model.Customer;
import com.evrybank.user.model.Validator;

public class EvryBankAdminServiceImpl implements EvryBankAdminService {
	public Customer customer = null;
	public EvryBankAdminDao evryBankAdminDao = new EvryBankAdminDaoImpl();
	Scanner scanner = new Scanner(System.in);

	public EvryBankAdminServiceImpl() {

	}

	public void getData() {
		String userName;
		String password;
		EvryBankAdminService evryBankAdminService = new EvryBankAdminServiceImpl();
		Validator validator = new Validator();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to Evry Bank");
		System.out.println("Please Enter User Name:");

		userName = scanner.next();
		System.out.println("Please Enter Password:");
		password = scanner.next();
		Customer customer= validator.validateUser(userName, password);
		if (customer.getAccess() == 1) {
			System.out.println("Welcome Admin");
			getAdminMenu(evryBankAdminService, scanner);

		} else {
			System.out.println("Admin does not exist");
		}
	}

	private static void getAdminMenu(EvryBankAdminService evryBankAdminService, Scanner scanner) {
		System.out.println("Plese press the following options: ");
		System.out.println("1 -> Add a customer");
		System.out.println("2 -> Delete a customer");
		System.out.println("3 -> View all customer");
		System.out.println("4 -> Add a money to customer account");
		System.out.println("5 -> Approve a transaction");
		System.out.println("6 -> Provide loan");
		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			if (evryBankAdminService.addCustomer()) {
				System.out.println("One Customer Added");
			} else {
				System.out.println("Customer not added");
			}
			break;
		case 2:
			if (evryBankAdminService.deleteCustomer()) {
				System.out.println("Customer deleted");
			} else {
				System.out.println("Customer not deleted");
			}
			break;
		case 3:
			evryBankAdminService.viewAllCustomers();
			break;
		case 4:
			System.out.println("Please enter customer id");
			int id = scanner.nextInt();
			if (evryBankAdminService.addMoneyToCustomer(id)) {
				System.out.println("Money transfored to customer account");
			} else {
				System.out.println("Money not transfored to the customer account");
			}
			break;
		case 5:
			System.out.println("Please enter customer id");
			int customer_id = scanner.nextInt();
			if(evryBankAdminService.aproveTransaction(customer_id)) {
				System.out.println("Money transfored");
			} else {
				System.out.println("Transaction faild");
			}
			break;
		case 6:
			System.out.println("Please enter customer id");
			int cus_id = scanner.nextInt();
			if(evryBankAdminService.provideLoans(cus_id)) {
				System.out.println("Money transfored");
			} else {
				System.out.println("Transaction faild");
			}
		}
	}

	@Override
	public boolean addCustomer() {
		customer = new Customer();
		System.out.println("Plese enter Customer id: ");
		customer.setCid(scanner.nextInt());
		System.out.println("Plese enter Customer first name: ");
		customer.setFirstName(scanner.next());
		System.out.println("Plese enter Customer last name: ");
		customer.setLastName(scanner.next());
		System.out.println("Plese enter City: ");
		customer.setCity(scanner.next());
		System.out.println("Plese enter Country: ");
		customer.setCountry(scanner.next());
		System.out.println("Plese enter Mobile Number: ");
		customer.setMobileNumber(scanner.next());
		System.out.println("Plese enter Email:");
		customer.setEmail(scanner.next());
		System.out.println("Plese enter account balance:");
		customer.setBalance(scanner.nextInt());
		System.out.println("Plese enter password:");
		customer.setPassword(scanner.next());
		System.out.println("Plese enter loan amount:");
		customer.setLoanAmount(scanner.nextInt());
		return evryBankAdminDao.addCustomer(customer);
	}

	@Override
	public void viewAllCustomers() {
		List<Customer> customers = evryBankAdminDao.viewAllCustomers();
		for (Customer customer : customers) {
			System.out.println(customer);
		}
	}

	@Override
	public boolean deleteCustomer() {
		System.out.println("Plese enter deleted Customer id: ");
		return evryBankAdminDao.deleteCustomer(scanner.nextInt());
	}

	@Override
	public boolean addMoneyToCustomer(final int cid) {
		return evryBankAdminDao.addMoneyToCustomer(cid);
	}

	@Override
	public boolean aproveTransaction(final int cid) {
		return evryBankAdminDao.aproveTransaction(cid);
	}

	@Override
	public boolean provideLoans(final int cid) {
		return evryBankAdminDao.provideLoans(cid);
	}

}
