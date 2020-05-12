package main_package;

import static main_package.Attributes.CUSTOMER_DEFAULT_ADDRESS;
import static main_package.Attributes.CUSTOMER_EMAIL_ADDRESS;
import static main_package.Attributes.CUSTOMER_ID;
import static main_package.Attributes.CUSTOMER_NAME;
import static main_package.Attributes.CUSTOMER_PHONE_NUMBER;

import java.util.HashMap;
import java.util.Scanner;

import dao.AccountDao;
import dao.ActivityDao;

public class CustomerProcessor {

	public static Customer ask() {
		System.out.println("1. Login ke akun untuk melanjutkan pembelian");
		System.out.println("2. Melanjutkan pembelian tanpa akun");

		Scanner sc = new Scanner(System.in);

		int ask = sc.nextInt();

		Customer customer = null;

		boolean run = true;

		while (run) {
			switch (ask) {
			case 1:
				run = false;
				
				System.out.println("Username: ");
				String username = sc.next();
				System.out.println("Pasword: ");
				String password = sc.next();
				
				customer = login(username, password);
				
				System.out.println("Ketik 1 untuk menggunakan alamat yang sama \n " + "Ketik 2 untuk mengganti alamat");
				int alamat = sc.nextInt();
				if (alamat == 2) {
					System.out.println("Alamat pengiriman baru(Tidak Permanen): ");
					String alamatBaru = sc.next();
					customer.setAddress(alamatBaru);
				}
				break;
			case 2:
				run = false;
				
				System.out.println("Nama penerima: ");
				String namaPenerima = sc.next();
				System.out.println("Alamat pengiriman: ");
				String alamatPengiriman = sc.next();
				
				customer = getWithoutLogin(namaPenerima, alamatPengiriman);
				break;
			default:
				break;
			}
		}
		
		return customer;
	}

	private static Customer login(String username, String password) {
		boolean run = true;
		Customer customer = null;

		while (run) {
			AccountDao accountDao = new AccountDao();

			if (accountDao.accountIsVerified(username, password)) {
				String token = CodeGenerator.generateAccountToken();

				ActivityDao activityDao = new ActivityDao();
				activityDao.insertToken(username, token);

				AccountDao accountDaoVerified = new AccountDao();
				HashMap<String, String> accountDetails = accountDaoVerified.getAccountDetailsByUsername(username,
						token);

				String customerId = accountDetails.get(CUSTOMER_ID);
				String customerName = accountDetails.get(CUSTOMER_NAME);
				String customerPhoneNumber = accountDetails.get(CUSTOMER_PHONE_NUMBER);
				String customerEmailAddress = accountDetails.get(CUSTOMER_EMAIL_ADDRESS);
				String customerDefaultAddress = accountDetails.get(CUSTOMER_DEFAULT_ADDRESS);

				customer = new Customer(customerId, customerName, customerPhoneNumber, customerEmailAddress,
						customerDefaultAddress);

				System.out.println("Akun berhasil diverifikasi");
				run = false;
			} else {
				System.out.println("Akun gagal diverifikasi");
			}
		}
		
		return customer;
	}

	private static Customer getWithoutLogin(String namaPenerima, String alamatPengiriman) {
		Customer customer = new Customer(namaPenerima, alamatPengiriman);
		return customer;
	}

}
