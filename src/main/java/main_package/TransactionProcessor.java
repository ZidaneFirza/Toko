package main_package;

import static main_package.Attributes.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import dao.ProductDao;
import dao.TransactionDao;

public class TransactionProcessor {

	// Temporary Code
	public static String[] makeTransaction(Map<String, Object> transactionMap, Customer customer) {
		String[] temp = new String[2];
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		String transactionId = CodeGenerator.generateTransactionId();
		String accountId = customer.getCustomerId();
		String transactionAddress = customer.getAddress();
		String productIds = (String) transactionMap.get(PRODUCT_IDS);
		String productPrices = (String) transactionMap.get(PRODUCT_PRICES);
		String productQuantities = (String) transactionMap.get(PRODUCT_QUANTITIES);
		double transactionAmount = (Double) transactionMap.get(TRANSACTION_AMOUNT);
		String paymentCode = CodeGenerator.generatePaymentCode();
		String transactionDate = dateFormat.format(date);
		String transactionStatus = WAITING;

		MainTransaction transaction = new MainTransaction(transactionId, accountId, transactionAddress, productIds,
				productPrices, productQuantities, transactionAmount, paymentCode, transactionDate, transactionStatus);

		TransactionDao transactionDao = new TransactionDao();
		transactionDao.insertTransaction(transaction);

		transactionDao = new TransactionDao();
		transactionDao.verifyTransaction(transactionId); // temporary

		temp[0] = paymentCode;
		temp[1] = transactionId;

		return temp;

	}

	public static void process(String transactionId) {
		TransactionDao transactionDao = new TransactionDao();
		ArrayList<MainTransaction> transaction = transactionDao.getVerifiedTransactionByTransactionId(transactionId);
		
		MainTransaction transactionO = transaction.get(0);

		String[] productIdArr = transactionO.productId();
		double[] productPriceArr = transactionO.productPrice();
		int[] productQuantityArr = transactionO.productQuantity();

		ArrayList<TransactionBranch> transactionBranchArr = new ArrayList<TransactionBranch>();

		String accountId;
		String transactionAddress;
		String productId;
		double productPrice;
		int productQuantity;
		String transactionDate;

		for (int i = 0; i < productIdArr.length; i++) {
			transactionId = transactionO.getTransactionId();
			accountId = transactionO.getAccountId();
			transactionAddress = transactionO.getTransactionAddress();
			productId = productIdArr[i];
			productPrice = productPriceArr[i];
			productQuantity = productQuantityArr[i];
			transactionDate = transactionO.getTransactionDate();

			transactionBranchArr.add(new TransactionBranch(transactionId, accountId, transactionAddress, productId,
					productPrice, productQuantity, transactionDate));
		}

		ProductDao productDao;

		for (TransactionBranch transactionBranch : transactionBranchArr) {
			productDao = new ProductDao();
			productDao.decrementProductStock(transactionBranch);

			transactionDao = new TransactionDao();
			transactionDao.insertIntoTransactionBranch(transactionBranch);
		}
		System.out.println("Transaction Success");
	}

}
