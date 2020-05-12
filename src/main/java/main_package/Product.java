package main_package;

public class Product {
	private int productIndex;
	private String productId;
	private String productName;
	private double productPrice;
	private int productStock;
	
	public Product(int productIndex, String productId, String productName, double productPrice, int productStock) {
		this.productIndex = productIndex;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productStock = productStock;
	}

	public Product(String productId, String productName, double productPrice, int productStock) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productStock = productStock;
	}

	public void decrementStock(int dec) {
		productStock -= dec;
	}
	
	public int getproductIndex() {
		return productIndex;
	}

	public String getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public double getProductPrice() {
		return productPrice;
	}
	
	public int getProductStock() {
		return productStock;
	}

}
