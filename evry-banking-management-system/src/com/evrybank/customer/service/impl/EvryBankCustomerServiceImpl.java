package com.evrybank.customer.service.impl;

import java.util.List;
import java.util.Scanner;

import com.evrybank.customer.dao.EvryBankCustomerDao;
import com.evrybank.customer.service.EvryBankCustomerService;
import com.evrybank.user.model.Account;
import com.evrybank.user.model.Customer;
import com.evrybank.user.model.Validator;
import com.evrybank.customer.dao.impl.EvryBankCustomerDaoImpl;

public class EvryBankCustomerServiceImpl implements EvryBankCustomerService {
	public Customer customer = null;
	public EvryBankCustomerDao evryBankCustomerDao = new EvryBankCustomerDaoImpl();
	Scanner scanner = new Scanner(System.in);

	public EvryBankCustomerServiceImpl(){
		
	}
	public void getData() {
		String userName;
		String password;
		EvryBankCustomerService evryCustomerService = new EvryBankCustomerServiceImpl();
		Validator validator = new Validator();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to Evry Bank");
		System.out.println("Please Enter User Name:");

		userName = scanner.next();
		System.out.println("Please Enter Password:");
		password = scanner.next();
		Customer customer = validator.validateUser(userName, password);
		if (customer.getAccess() == 0) {

			System.out.println("Welcome User");
			getUserMenu(evryCustomerService, customer.getCid(), scanner);
		} else {
			System.out.println("User does not exist.");
		}
	}

	private static void getUserMenu(EvryBankCustomerService evryCustomerService, int cid, Scanner scanner) {
		System.out.println("Plese press the following options: ");
		System.out.println("1 -> Add Money");
		System.out.println("2 -> view account");
		System.out.println("3 -> send money");
		System.out.println("4 -> withdraw money");
		System.out.println("5 -> Loan request");
		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			System.out.println("Please enter the amount.");
			int amount = scanner.nextInt();
			if (evryCustomerService.addMoney(cid, amount)) {
				System.out.println(" amount will add to your account with in few minutes.");
			} else {
				System.out.println("Sorry you are unable to add money to your account, please contact admin.");
			}
			break;
		case 2:
			System.out.println("Your account details are:");
			evryCustomerService.viewAccountDetails(cid);
			break;
		case 3:
			System.out.println("Please enter receiver id:");
			int rid = scanner.nextInt();
			System.out.println("Please enter transfer amount:");
			int transfer_amount = scanner.nextInt();
			evryCustomerService.sendMoney(cid, rid, transfer_amount);
			break;
		case 4:
			System.out.println("Please Enter withdraw amount:");
			int withdrawAmount = scanner.nextInt();
			evryCustomerService.withdrawMoney(cid, withdrawAmount);
		case 5:
			System.out.println("Please enter Loan amount");
			int loanAmount = scanner.nextInt();
			evryCustomerService.loanRequest(cid, loanAmount);
		}
	}

	@Override
	public boolean addMoney(int cid, int amount) {

		return evryBankCustomerDao.addMoney(cid, amount);
	}

	@Override
	public void viewAccountDetails(int cid) {
		for (Customer customer : evryBankCustomerDao.viewAccountDetails(cid)) {
			System.out.println("Cid \t Name \t City \t Balance ");
			System.out.print(customer.getCid() + "\t");
			System.out.print(customer.getFirstName() + "\t");
			System.out.print(customer.getCity() + "\t");
			System.out.print(customer.getBalance() + "\t");
			System.out.println();
		}
	}

	@Override
	public void sendMoney(int cid, int rid, int amount) {
		evryBankCustomerDao.sendMoney(cid, rid, amount);
	}

	@Override
	public void withdrawMoney(int cid, int withdrawAmount) {
		evryBankCustomerDao.sendMoney(cid, 0, withdrawAmount);
	}

	@Override
	public boolean loanRequest(int cid, int loanAmount) {
		return evryBankCustomerDao.loanRequest(cid, loanAmount);
	}

}
