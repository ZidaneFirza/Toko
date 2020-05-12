package dao;

import static main_package.Attributes.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.jdbc.MysqlDataSource;

import main_package.MainTransaction;
import main_package.TransactionBranch;

public class TransactionDao {
	Connection connection;

	public TransactionDao() {
		try {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setServerName("localhost");
			dataSource.setUser("root");
			dataSource.setPassword("");
			dataSource.setDatabaseName("toko_sembako");
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertTransaction(MainTransaction transaction) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO transaction_table"
					+ "(transaction_id, account_id, transaction_address, product_ids, product_prices, product_quantities, transaction_amount, payment_code)"
					+ "VALUES(?,?,?,?,?,?,?,?)");

			ps.setString(1, transaction.getTransactionId());
			ps.setString(2, transaction.getAccountId());
			ps.setString(3, transaction.getTransactionAddress());
			ps.setString(4, transaction.getProductIds());
			ps.setString(5, transaction.getProductPrices());
			ps.setString(6, transaction.getProductQuantities());
			ps.setDouble(7, transaction.getTransactionAmount());
			ps.setString(8, transaction.getPaymentCode());

			ps.execute();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<MainTransaction> getVerifiedTransactionByTransactionId(String transactionId) {
		ArrayList<MainTransaction> transaction = new ArrayList<MainTransaction>();

		String accountId;
		String transactionAddress;
		String productIds;
		String productPrice;
		String productQuantity;
		double transactionAmount;
		String paymentCode;
		String transactionStatus;
		String transactionDate;

		try {
			PreparedStatement ps = connection.prepareStatement(
					"Select * from transaction_table " + "WHERE transaction_id  = ? AND transaction_status = ?");
			ps.setString(1, transactionId);
			ps.setString(2, CONFIRMED);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				accountId = rs.getString("account_id");
				transactionAddress = rs.getString("transaction_address");
				productIds = rs.getString("product_ids");
				productPrice = rs.getString("product_prices");
				productQuantity = rs.getString("product_quantities");
				transactionAmount = rs.getDouble("transaction_amount");
				paymentCode = rs.getString("payment_code");
				transactionDate = rs.getDate("transaction_date").toString();
				transactionStatus = CONFIRMED;

				transaction
						.add(new MainTransaction(transactionId, accountId, transactionAddress, productIds, productPrice,
								productQuantity, transactionAmount, paymentCode, transactionDate, transactionStatus));
			}

			connection.close();
			return transaction;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public void insertIntoTransactionBranch(TransactionBranch transactionBranch) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO transaction_branch_table"
					+ "(transaction_id, account_id, transaction_address, product_id, product_price, product_quantity, transaction_date)"
					+ "VALUES(?,?,?,?,?,?,?)");
			ps.setString(1, transactionBranch.getTransactionId());
			ps.setString(2, transactionBranch.getAccountId());
			ps.setString(3, transactionBranch.getTransactionAddress());
			ps.setString(4, transactionBranch.getProductId());
			ps.setDouble(5, transactionBranch.getProductPrice());
			ps.setInt(6, transactionBranch.getProductQuantity());
			ps.setString(7, transactionBranch.getTransactionDate());

			ps.execute();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void verifyTransaction(String transactionId) {
		try {
			PreparedStatement ps = connection.prepareStatement(
					"UPDATE transaction_table\r\n" + "SET transaction_status = ? WHERE transaction_id = ?");
			ps.setString(1, CONFIRMED);
			ps.setString(2, transactionId);

			ps.execute();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
