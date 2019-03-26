package com.evrybank.admin.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.evrybank.admin.dao.EvryBankAdminDao;
import com.evrybank.user.model.Customer;

public class EvryBankAdminDaoImpl implements EvryBankAdminDao {
	public Connection connection = null;
	public int access = 2;

	@Override
	public Customer checkUser(final String userName, final String password) {
		Customer customer = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false&&allowPublicKeyRetrieval=true", "root",
					"root");
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM banking.customer where firstname = ? AND password = ?");
			statement.setString(1, userName);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			customer = new Customer();
			if (resultSet.next()) {
				customer.setAccess(resultSet.getInt("access"));
				customer.setCid(resultSet.getInt("id"));
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
		return customer;
	}

	@Override
	public boolean addCustomer(Customer customer) {
		int cid = customer.getCid();
		String firstName = customer.getFirstName();
		String lastName = customer.getLastName();
		String city = customer.getCity();
		String country = customer.getCountry();
		String mobileNumber = customer.getMobileNumber();
		String email = customer.getEmail();
		int balance = customer.getBalance();
		String password = customer.getPassword();
		int loanAmount = customer.getLoanAmount();
		int access = 0;
		int result = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false&&allowPublicKeyRetrieval=true", "root",
					"root");
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO banking.customer values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			statement.setInt(1, cid);
			statement.setString(2, firstName);
			statement.setString(3, lastName);
			statement.setString(4, city);
			statement.setString(5, country);
			statement.setString(6, mobileNumber);
			statement.setString(7, email);
			statement.setInt(8, balance);
			statement.setString(9, password);
			statement.setInt(10, access);
			statement.setInt(11, loanAmount);
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
	public boolean deleteCustomer(int cid) {
		int result = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false&&allowPublicKeyRetrieval=true", "root",
					"root");
			PreparedStatement statement = connection.prepareStatement("DELETE FROM banking.customer where id = ?");
			statement.setInt(1, cid);

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
	public List<Customer> viewAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false&&allowPublicKeyRetrieval=true", "root",
					"root");
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM banking.customer");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				customer = new Customer();
				customer.setCid(resultSet.getInt("id"));
				customer.setFirstName(resultSet.getString("firstname"));
				customer.setLastName(resultSet.getString("lastname"));
				customer.setCity(resultSet.getString("city"));
				customer.setCountry(resultSet.getString("country"));
				customer.setMobileNumber(resultSet.getString("mobile"));
				customer.setEmail(resultSet.getString("email"));
				customers.add(customer);
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
		// System.out.println(customers + "kkk");
		return customers;
	}

	@Override
	public boolean addMoneyToCustomer(int cid) {
		int result = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false&&allowPublicKeyRetrieval=true", "root",
					"root");
			int pending_amount = getCustomerAmount(cid, connection);
			int balance = getBalance(cid, connection);
			PreparedStatement statement = connection
					.prepareStatement("UPDATE banking.customer SET balance = ? WHERE id = ?");
			statement.setInt(1, balance + pending_amount);
			statement.setInt(2, cid);

			if(statement.executeUpdate() == 1) {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false", "root",
						"root");
				PreparedStatement statement2 = connection
						.prepareStatement("UPDATE banking.transaction SET process_amount = 0 WHERE id = ?");
				statement2.setInt(1, cid);

				result = statement2.executeUpdate();
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
		return (result == 1) ? true : false;
	}

	private int getCustomerAmount(int cid, Connection connection) {
		int sum = 0;
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT sum(process_amount) from banking.transaction where id = ?");
			statement.setInt(1, cid);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				sum = resultSet.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return sum;
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
	public boolean aproveTransaction(int cid) {
		int result = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false&&allowPublicKeyRetrieval=true", "root",
					"root");
			int transfer_amount = getTransferAmount(cid, connection);
			int balance = getBalance(cid, connection);
			PreparedStatement statement = connection
					.prepareStatement("UPDATE banking.customer SET balance = ? WHERE id = ?");
			statement.setInt(1, balance + transfer_amount);
			statement.setInt(2, cid);
			int update = statement.executeUpdate();
			if( update >= 1) {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false&&allowPublicKeyRetrieval=true", "root",
						"root");
				PreparedStatement statement2 = connection
						.prepareStatement("UPDATE banking.transaction SET transfer_amount = 0 WHERE id = ?");
				statement2.setInt(1, cid);

				result = statement2.executeUpdate();
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
		return (result >= 1) ? true : false;
	}
	private int getTransferAmount(int cid, Connection connection) {
		int sum = 0;
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT sum(transfer_amount) from banking.transaction where id = ?");
			statement.setInt(1, cid);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				sum = resultSet.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return sum;
	}

	@Override
	public boolean provideLoans(int cid) {
		int result = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false&&allowPublicKeyRetrieval=true", "root",
					"root");
			int loan_amount = getLoanAmount(cid, connection);
			PreparedStatement statement = connection
					.prepareStatement("UPDATE banking.customer SET loan_amount = ? WHERE id = ?");
			statement.setInt(1, loan_amount);
			statement.setInt(2, cid);
			int update = statement.executeUpdate();
			if( update >= 1) {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false&&allowPublicKeyRetrieval=true", "root",
						"root");
				PreparedStatement statement2 = connection
						.prepareStatement("UPDATE banking.transaction SET loan_request = 0 WHERE id = ?");
				statement2.setInt(1, cid);

				result = statement2.executeUpdate();
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
		return (result >= 1) ? true : false;
	}
	private int getLoanAmount(int cid, Connection connection) {
		int sum = 0;
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT sum(loan_request) from banking.transaction where id = ?");
			statement.setInt(1, cid);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				sum = resultSet.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return sum;
	}
}
