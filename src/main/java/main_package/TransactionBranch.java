package main_package;

public class TransactionBranch extends Transaction {
	private String productId;
	private double productPrice;
	private int productQuantity;

	public TransactionBranch(String transactionId, String accountId, String transactionAddress, String productId,
			double productPrice, int productQuantity, String transactionDate) {
		super(transactionId, accountId, transactionAddress, transactionDate);
		this.productId = productId;
		this.productPrice = productPrice;
		this.productQuantity = productQuantity;
	}

	public String getProductId() {
		return productId;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

}
