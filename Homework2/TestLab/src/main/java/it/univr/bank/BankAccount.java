package it.univr.bank;

import java.util.LinkedList;
import java.util.List;

public class BankAccount {

	private final List<Integer> transactions;
	private long balance;

	public BankAccount() {
		balance = 0;
		transactions = new LinkedList<>();
	}

	public void deposit(int value) {
		balance += value;
		transactions.add(value);
	}

	public void withdraw(int value) {
		balance -= value;
		transactions.add(-value);
	}

	public long balance() {
		return balance;
	}

}
