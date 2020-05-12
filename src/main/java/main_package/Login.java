package main_package;

import static main_package.Attributes.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import dao.AccountDao;
import dao.ActivityDao;

public class Login {

	public static Customer login() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Username: ");
		String username = sc.next();
		System.out.println("Pasword: ");
		String password = sc.next();

		AccountDao accountDao = new AccountDao();
		if (accountDao.accountIsVerified(username, password)) {
			String token = CodeGenerator.generateAccountToken();
			ActivityDao activityDao = new ActivityDao();
			activityDao.insertToken(username, token);
			AccountDao accountDaoVerified = new AccountDao();
			HashMap<String, String> accountDetails = accountDaoVerified.getAccountDetailsByUsername(username, token);

			String customerId = accountDetails.get(CUSTOMER_ID);
			String customerName = accountDetails.get(CUSTOMER_NAME);
			String customerPhoneNumber = accountDetails.get(CUSTOMER_PHONE_NUMBER);
			String customerEmailAddress = accountDetails.get(CUSTOMER_EMAIL_ADDRESS);
			String customerDefaultAddress = accountDetails.get(CUSTOMER_DEFAULT_ADDRESS);

			Customer customer = new Customer(customerId, customerName, customerPhoneNumber, customerEmailAddress,
					customerDefaultAddress);

			System.out.println("Akun berhasil diverifikasi");

			return customer;
		} else {
			System.out.println("Akun gagal diverifikasi");
		}
		return null;
	}

}
