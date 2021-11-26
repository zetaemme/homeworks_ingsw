package it.univr.bank;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BankAccountTests {
	private BankAccount bankAccount;

	@Before
	public void setup() {
		bankAccount = new BankAccount();
	}

	@Test
	public void testInitialBalance() {
		assertEquals("A new bank account shouldn't have any money in it", 0, bankAccount.balance());
	}

	@Test
	public void testDeposit() {
		bankAccount.deposit(10);

		assertEquals("After a 10$ deposit the balance should be 10$", 10, bankAccount.balance());
	}

	@Test
	public void testWithdraw() {
		bankAccount.deposit(10);
		bankAccount.withdraw(8);

		assertEquals("After a 10$ deposit and a 8$ withdraw the balance should be 2$", 2, bankAccount.balance());
	}

	@Test(timeout = 10)
	public void testIteration() {
		for (int i = 0; i < 2024; i++) {
			bankAccount.deposit(10);
			bankAccount.withdraw(10);
		}

		assertEquals("After 2024 iterations the balance is still 0$", 0, bankAccount.balance());
	}
}
