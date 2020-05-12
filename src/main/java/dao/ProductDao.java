package dao;

import static main_package.Attributes.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mysql.cj.jdbc.MysqlDataSource;

import main_package.Product;
import main_package.TransactionBranch;

public class ProductDao {
	Connection connection;

	public ProductDao() {
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

	public ArrayList<Product> getProducts() {
		ArrayList<Product> product = new ArrayList<Product>();
		int count = 1;
		String productName = "";
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM product, product_inventory" + 
					"    WHERE product.product_id = product_inventory.product_id");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				productName = rs.getString("product_name") + " " + rs.getDouble("product_volume")
						+ rs.getString("product_unit");
				product.add(new Product(count, rs.getString("product_id"), productName, rs.getDouble("product_price"),
						rs.getInt("product_stock")));
				count++;
			}

			ps.close();
			rs.close();
			connection.close();
			return product;
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return null;
	}

	public void decrementProductStock(TransactionBranch transactionBranch) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE `toko_sembako`.product_inventory SET product_stock = product_stock - ?  WHERE product_id = ?");
			ps.setInt(1, transactionBranch.getProductQuantity());
			ps.setString(2, transactionBranch.getProductId());
			
			ps.execute();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
