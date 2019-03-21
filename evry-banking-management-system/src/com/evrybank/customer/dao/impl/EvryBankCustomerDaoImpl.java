package com.evrybank.customer.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.evrybank.customer.dao.EvryBankCustomerDao;
import com.evrybank.user.model.Account;
import com.evrybank.user.model.Customer;

public class EvryBankCustomerDaoImpl implements EvryBankCustomerDao {
	public Connection connection = null;

	@Override
	public boolean addMoney(int cid, int amount) {
		int result = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false", "root",
					"root");
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO banking.transaction (id, process_amount) values (?, ?)");
			statement.setInt(1, cid);
			statement.setInt(2, amount);
			result = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return (result == 1) ? true : false;
	}


	@Override
	public List<Customer> viewAccountDetails(int cid) {
		List<Customer> customerDetails = new ArrayList<Customer>();
		Customer customer = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false", "root",
					"root");
			PreparedStatement statement = connection.prepareStatement("SELECT * from customer where id = ?");
			statement.setInt(1, cid);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				customer = new Customer();
				customer.setCid(resultSet.getInt("id"));
				customer.setFirstName(resultSet.getString("firstname"));
				customer.setCity(resultSet.getString("city"));
				customer.setBalance(resultSet.getInt("balance"));
				customerDetails.add(customer);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return customerDetails;
	}

	@Override
	public void sendMoney(int cid, int rid, int amount) {
		List<Account> accountDetails = new ArrayList<Account>();
		Account account = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false", "root",
					"root");
			int balance = getBalance(cid, connection);
			if (balance >= amount) {
				balance = balance - amount;
				setBalance(cid, rid, balance, amount, connection);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void setBalance(int cid, int rid, int result, int amount, Connection connection) {
		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE customer SET balance = ? where id = ?");
			statement.setInt(1, result);
			statement.setInt(2, cid);
			int status = statement.executeUpdate();
			if (status == 1 && rid != 0) {
				transferToPayee(rid, amount, connection);
			} else {
				System.out.println("Amout successfully withdraw from the user ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void transferToPayee(int rid, int amount, Connection connection) {

		try {
			PreparedStatement statement = connection
					.prepareStatement("insert into transaction (id, transfer_amount) values(?, ?)");
			statement.setInt(1, rid);
			statement.setInt(2, amount);
			int status = statement.executeUpdate();
			if (status == 1) {
				System.out.println("Money Transfer to Payee. ");
			} else {
				System.out.println("Unable to transfer money to payee.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private int getBalance(int cid, Connection connection) {
		int balance = 0;
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT balance from customer where id = ?");
			statement.setInt(1, cid);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				balance = resultSet.getInt("balance");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;

	}

	@Override
	public boolean loanRequest(int cid, int loanAmount) {
		boolean flag = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false", "root",
					"root");
			PreparedStatement statement = connection
					.prepareStatement("insert into transaction (id, loan_request) values(?, ?)");
			statement.setInt(1, cid);
			statement.setInt(2, loanAmount);
			int status = statement.executeUpdate();
			if (status == 1) {
				flag = true;
				System.out.println("Loan amount requested");
			} else {
				System.out.println("Unable to request the loan amount");
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

}
