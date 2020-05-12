package main_package;

public class Customer {
	private String customerId;
	private String customerName;
	private String customerPhoneNumber;
	private String customerEmailAddress;
	private String address;

	public Customer(String customerId, String customerName, String customerPhoneNumber, String customerEmailAddress,
			String address) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerPhoneNumber = customerPhoneNumber;
		this.customerEmailAddress = customerEmailAddress;
		this.address = address;
	}

	public Customer(String customerName, String address) {
		this.customerName = customerName;
		this.address = address;
	}

	public Customer() {

	}

	public String getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public String getCustomerEmailAddress() {
		return customerEmailAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
