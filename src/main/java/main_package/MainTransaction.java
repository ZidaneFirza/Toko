package main_package;

public class MainTransaction extends Transaction {
	private String productIds;
	private String productPrices;
	private String productQuantities;
	private double transactionAmount;
	private String paymentCode;
	private String transactionStatus;

	public MainTransaction(String transactionId, String accountId, String transactionAddress, String productIds,
			String productPrices, String productQuantities, double transactionAmount, String paymentCode,
			String transactionDate, String transactionStatus) {
		super(transactionId, accountId, transactionAddress, transactionDate);
		this.productIds = productIds;
		this.productPrices = productPrices;
		this.productQuantities = productQuantities;
		this.transactionAmount = transactionAmount;
		this.paymentCode = paymentCode;
		this.transactionStatus = transactionStatus;
	}

	public String[] productId() {
		String[] productIdArr = productIds.split(",");
		String[] productIdArrAfter = new String[productIdArr.length];
		int count = 0;
		for (String a : productIdArr) {
			if (!(a.trim().isEmpty())) {
				productIdArrAfter[count] = a.trim();
			}
			count++;
		}
		return productIdArrAfter;
	}

	public double[] productPrice() {
		String[] productPriceArr = productPrices.split(",");
		double[] productPriceArrAfter = new double[productPriceArr.length];
		int count = 0;
		for (String a : productPriceArr) {
			if (!(a.trim().isEmpty())) {
				productPriceArrAfter[count] = Double.parseDouble(a.trim());
			}
			count++;
		}
		return productPriceArrAfter;
	}

	public int[] productQuantity() {
		String[] productQuantityArr = productQuantities.split(",");
		int[] productQuantityArrAfter = new int[productQuantityArr.length];
		int count = 0;
		for (String a : productQuantityArr) {
			if (!(a.trim().isEmpty())) {
				productQuantityArrAfter[count] = Integer.parseInt(a.trim());
			}
			count++;
		}
		return productQuantityArrAfter;
	}

	public String getProductIds() {
		return productIds;
	}

	public String getProductPrices() {
		return productPrices;
	}

	public String getProductQuantities() {
		return productQuantities;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

}
