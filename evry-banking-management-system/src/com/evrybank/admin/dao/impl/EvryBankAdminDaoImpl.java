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
	public int checkUser(final String userName, final String password) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false", "root",
					"root");
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM banking.user where name = ? AND password = ?");
			statement.setString(1, userName);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				access = resultSet.getInt("access");
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
		return access;
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
		int result = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false", "root",
					"root");
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO banking.customer values (?, ?, ?, ?, ?, ?, ?)");
			statement.setInt(1, cid);
			statement.setString(2, firstName);
			statement.setString(3, lastName);
			statement.setString(4, city);
			statement.setString(5, country);
			statement.setString(6, mobileNumber);
			statement.setString(7, email);

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
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false", "root",
					"root");
			PreparedStatement statement = connection.prepareStatement("DELETE FROM banking.customer where cid = ?");
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
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false", "root",
					"root");
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM banking.customer");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				customer = new Customer();
				customer.setCid(resultSet.getInt("cid"));
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
		//System.out.println(customers + "kkk");
		return customers;
	}

	@Override
	public boolean addMoney(int cid, int amount) {
		int result = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking?useSSL=false", "root",
					"root");
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO banking.customer (amount) values (?) where cid = ?");
			statement.setInt(1, amount);
			statement.setInt(2, cid);

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

}
