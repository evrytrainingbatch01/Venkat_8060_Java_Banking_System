package com.evrybank.user.model;

public class Account {
	private int id;
	private int processAmount;
	private int transferTo;
	private int transferAmount;
	private int transactionStatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProcessAmount() {
		return processAmount;
	}

	public void setProcessAmount(int processAmount) {
		this.processAmount = processAmount;
	}

	public int getTransferTo() {
		return transferTo;
	}

	public void setTransferTo(int transferTo) {
		this.transferTo = transferTo;
	}

	public int getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(int transferAmount) {
		this.transferAmount = transferAmount;
	}

	public int getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(int transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

}
