package main_package;

public abstract class Transaction {
	private String transactionId;
	private String accountId;
	private String transactionAddress;
	private String transactionDate;

	public Transaction(String transactionId, String accountId, String transactionAddress, String transactionDate) {
		this.transactionId = transactionId;
		this.accountId = accountId;
		this.transactionAddress = transactionAddress;
		this.transactionDate = transactionDate;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public String getAccountId() {
		return accountId;
	}
	public String getTransactionAddress() {
		return transactionAddress;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
}
