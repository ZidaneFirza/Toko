package main_package;

import java.util.Random;

public class CodeGenerator {

	public static String generateAccountToken() {
		String token = "";
		Random random = new Random();
		int digit;

		for (int i = 0; i < 5; i++) {
			digit = random.nextInt(10);
			token += digit;
		}
		return token;
	}

	public static String generateTransactionId() {
		String transactionId = "";
		Random random = new Random();
		int digit;

		for (int i = 0; i < 5; i++) {
			digit = random.nextInt(10);
			transactionId += digit;
		}
		return transactionId;
	}
	
	public static String generatePaymentCode() {
		String paymentCode = "";
		Random random = new Random();
		int digit;

		for (int i = 0; i < 10; i++) {
			digit = random.nextInt(10);
			paymentCode += digit;
		}
		return paymentCode;
	}

}
