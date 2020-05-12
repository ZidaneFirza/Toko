package main_package;

public class ShoppingCart {
	private String productId;
	private String productName;
	private int quantity;
	private double productPrice;
	private double totalPrice;

	public ShoppingCart(String productId, String productName, int quantity, double productPrice) {
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.productPrice = productPrice;
		this.totalPrice = quantity * productPrice;
	}

	public String getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

}
