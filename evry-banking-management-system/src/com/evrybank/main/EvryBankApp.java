package com.evrybank.main;

import java.util.Scanner;

import com.evrybank.admin.service.impl.EvryBankAdminServiceImpl;
import com.evrybank.customer.service.impl.EvryBankCustomerServiceImpl;

public class EvryBankApp {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		MainMenu mainMenu = () -> System.out.println("Welcome to Evry Bank");
		mainMenu.show();
		boolean flag = true;
		while (flag) {
			System.out.println("Please enter the following option ");
			System.out.println("1 -> Admin user");
			System.out.println("2 -> Customer");
			System.out.println("3 -> Exit");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				EvryBankAdminServiceImpl evryBankAdminService = new EvryBankAdminServiceImpl();
				evryBankAdminService.getData();
				break;
			case 2:
				EvryBankCustomerServiceImpl evryBankCustomerService = new EvryBankCustomerServiceImpl();
				evryBankCustomerService.getData();
				break;
			case 3:
				flag = false;
				break;
			}
		}
	}
}
