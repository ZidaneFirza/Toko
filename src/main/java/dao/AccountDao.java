package dao;

import static main_package.Attributes.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.cj.jdbc.MysqlDataSource;

public class AccountDao {
	Connection connection;

	public AccountDao() {
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

	public boolean accountIsVerified(String username, String password) {
		try {
			PreparedStatement ps = connection
					.prepareStatement("Select * from account where " + "username = ? AND password = ?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				connection.close();
				return true;
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	public HashMap<String, String> getAccountDetailsByUsername(String username, String token) {
		HashMap<String, String> accountDetails = new HashMap<String, String>();
		try {
			PreparedStatement ps = connection.prepareStatement("Select * from account_details, activity"
					+ " where account_details.username = ? and token = ?");
			ps.setString(1, username);
			ps.setString(2, token);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				accountDetails.put(CUSTOMER_ID, rs.getString("account_id"));
				accountDetails.put(CUSTOMER_NAME, rs.getString("first_name"));
				accountDetails.put(CUSTOMER_DEFAULT_ADDRESS, rs.getString("default_address"));
				accountDetails.put(CUSTOMER_EMAIL_ADDRESS, rs.getString("email_address"));
				accountDetails.put(CUSTOMER_PHONE_NUMBER, rs.getString("phone_number"));
			}

			connection.close();
			return accountDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
