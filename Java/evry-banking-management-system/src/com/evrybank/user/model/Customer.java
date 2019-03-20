package com.evrybank.user.model;

public class Customer {
	private int cid;
	private String firstName;
	private String lastName;
	private String city;
	private String country;
	private String mobileNumber;
	private String email;

	public Customer() {
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Customer [cid=" + cid + ", firstName=" + firstName + ", lastName=" + lastName + ", city=" + city
				+ ", country=" + country + ", mobileNumber=" + mobileNumber + ", email=" + email + "]";
	}

}
